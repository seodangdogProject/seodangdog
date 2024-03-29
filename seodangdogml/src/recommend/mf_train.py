from repository.recommend_repository import select_all_ratings
from fastapi import APIRouter, BackgroundTasks
import pandas as pd
import numpy as np
from sklearn.utils import shuffle
import pickle
import os
import time
import multiprocessing

router = APIRouter()

@router.get('/fast/mf_recom/train')
def user_register_train():
    multiprocessing_train()


def multiprocessing_train():
    process = multiprocessing.Process(target=train_mf_model)
    process.start()

    return {"msg": "Training started."}


def save_mf(model):
    base_src = './recommend'
    model_name = 'mf_online.pkl'
    save_path = os.path.join(base_src, model_name)
    with open(save_path, 'wb') as f:
        pickle.dump(model, f)


def load_mf():
    base_src = './recommend'
    model_name = 'mf_online.pkl'
    save_path = os.path.join(base_src, model_name)
    if not os.path.exists(save_path):
        train_mf_model()
    with open(save_path, 'rb') as f:
        model = pickle.load(f)
    return model


def train_mf_model():

    ratings = select_all_ratings()
    ratings = pd.DataFrame(ratings)

    # 중복제거(디비의 무결성이 보장되면 필요없음)
    ratings = ratings.drop_duplicates(subset=['user_id', 'news_id'])

    TRAIN_SIZE = 0.75
    # (사용자 - 영화 - 평점)
    # 데이터를 섞는다. 데이터의 순서를 무작위로 변경하여 모델이 특정 순서에 의존하지 않도록 한다.
    # random_state 매개변수를 사용하여 재현 가능한 결과를 얻을 수 있도록 설정
    ratings = shuffle(ratings, random_state=2021)

    # 전체 데이터에서 훈련 세트의 크기를 결정하는 기준을 설정
    cutoff = int(TRAIN_SIZE * len(ratings))
    ratings_train = ratings.iloc[:cutoff]
    ratings_test = ratings.iloc[cutoff:]

    R_temp = ratings.pivot(index='user_id', columns='news_id', values='rating').fillna(0)

    hyper_params = {
        'K': 5,
        'alpha': 0.001,
        'beta': 0.02,
        'iterations': 3900,
        'verbose': True
    }

    mf = NEW_MF(R_temp, hyper_params)
    test_set = mf.set_test(ratings_test)
    result = mf.test()
    print("학습완료")
    # 학습을 통해 최적의 K값 찾기 start
    # results = []
    # index = []

    # R_temp = ratings.pivot(index = 'user_id', columns = 'news_id', values = 'rating').fillna(0)

    # for K in range (1,261,10):
    #   print(f'K : {K}')
    #   hyper_params = {
    #     'K' : K,
    #     'alpha' : 0.001,
    #     'beta' : 0.02,
    #     'iterations' : 300,
    #     'verbose' : True
    #   }
    #   mf = NEW_MF(R_temp, hyper_params)

    # test_set = mf.set_test(ratings_test)

    # result = mf.test()
    # 학습을 통해 최적의 K값 찾기 end

    save_mf(mf)
    print("mf.user_id_index : ", mf.user_id_index)
    print('모델저장완료')



# 변수로 넘어온 user_seq, news_seq는 인덱스(0부터)로 변환해서 학습을 시켜야한다.
def online_learning(mf, user_id, item_id, rating, weight=1):

    if user_id not in mf.user_id_index:
        # 새로운 사용자인 경우, 사용자를 모델에 추가하고 초기화
        mf.user_id_index[user_id] = mf.num_users
        mf.index_user_id[mf.num_users] = user_id
        mf.num_users += 1

        # 새로운 사용자의 특성을 추가하기 위해 mf.P에 새로운 행을 추가
        # np.random.normal() 함수를 사용하여 임의로 생성되며, 각 요소는 평균이 0이고 표준 편차가 1/mf.K 인 정규 분포를 따르는 난수
        # 새로운 사용자의 초기 특성을 무작위로 설정

        # np.random.normal() 함수는 정규 분포를 따르는 난수를 생성하는 함수(평균과 표준 편차가 인자)
        ## scale=1./mf.K로 표준 편차를 설정 -> 1/mf.K를 표준 편차로 가지는 정규 분포를 따르는 난수
        ### size=(1, mf.K) -> 1행, mf.K(잠재요인)열 형태의 난수를 생성
        existing_user_features = mf.P.mean(axis=0)  # 예시로 평균 사용
        mf.P = np.vstack([mf.P, existing_user_features])
        mf.b_u = np.append(mf.b_u, 0)

    if item_id not in mf.item_id_index:
        # 새로운 아이템인 경우, 아이템을 모델에 추가하고 초기화
        mf.item_id_index[item_id] = mf.num_items

        print(mf.item_id_index[item_id])
        mf.index_item_id[mf.num_items] = item_id
        mf.num_items += 1
        existing_item_features = mf.Q.mean(axis=0)  # 예시로 평균 사용
        mf.Q = np.vstack([mf.Q, existing_item_features])
        mf.b_d = np.append(mf.b_d, 0)

    # 사용자와 아이템을 모델에 추가한 후에는 모델을 업데이트
    i = mf.user_id_index[user_id]
    j = mf.item_id_index[item_id]
    mf.online_sgd((i, j, rating), weight)


class NEW_MF():
    def __init__(self, ratings, hyper_params):
        self.R = np.array(ratings)  # 데이터프레임으로 전달된 데이터를 변환
        self.num_users, self.num_items = np.shape(self.R)  # 사용자 수와, 아이템 수를 받아온다

        # 아래는 MF weight 조절을 위한 하이터파라미터이다
        self.K = hyper_params['K']  # 잠재요인의 개수, 하이퍼 파라미터는 딕셔너리라서 키값으로 받아온다
        self.alpha = hyper_params['alpha']  # 학습률
        self.beta = hyper_params['beta']  # 정규화개수
        self.iterations = hyper_params['iterations']  # SGD의 계산을 할 때의 반복 횟수
        self.verbose = hyper_params['verbose']  # 학습과정을 중/간에 출력할건지? 플래그 변수

        # movielens같은 경우는 데이터가 아주 잘 정리되어있고 연속된값이다.
        # 하지만 실제는 그렇지 않을 경우가 더 많을 것이다
        # 이러한 경우 self.R을 numpay array로 변환해 버리면 중가에 비어있는 실제 아이디와 self.R의 인덱스가 일치하지 않기때문에 문제 발생
        # 그래서 이러한 문제를 해결하고 나중에 범용적인 모델을 만들기 위해서 자동적으로 아이디간 매핑작업이 필요하가
        # 그래서 유저아이디와 아이템아이디를 인덱스와 매핑하기 위한 딕셔너리를 생성해보자

        item_id_index = []
        index_item_id = []
        for i, one_id in enumerate(ratings):
            # i는 반복인덱스, one_id는movie_id값이 들어간다
            item_id_index.append([one_id, i])
            index_item_id.append([i, one_id])
        self.item_id_index = dict(item_id_index)
        self.index_item_id = dict(index_item_id)

        user_id_index = []
        index_user_id = []
        for i, one_id in enumerate(ratings.T):
            user_id_index.append([one_id, i])
            index_user_id.append([i, one_id])
        self.user_id_index = dict(user_id_index)
        self.index_user_id = dict(index_user_id)

    # 목적함수 :  예측 오차를 계산, 평점 예측 오차를 최소화, SGD를 사용하여 오차가 최소되는 파라미터를 찾는다
    def rmse(self):
        xs, ys = self.R.nonzero()  # rating에서 0이 아닌 요소를 가져온다
        self.predictions = []
        self.errors = []

        # 평점이 있는 요소(사용자 x, 아이템 y)각각에 대해서 아래의 코드를 실행한다
        for x, y in zip(xs, ys):
            # 사용자 x, 아이템 y에 대해서 평점예측치를 계산
            prediction = self.get_prediction(x, y)
            self.predictions.append(prediction)

            # 실제값 R과 예측값의 차이(errors)를 오차값리스트에 추가
            self.errors.append(self.R[x, y] - prediction)

        # 예측값 리스트와 오차값리스트를 numpy array로 변환
        self.predictions = np.array(self.predictions)
        self.errors = np.array(self.errors)

        # error를 활용해서 RSME도출
        return np.sqrt(np.mean(self.errors ** 2))

    # 목적함수를 최소화하자 : 실제 최적의 p,q,bu,bd를 구하기 위한 과정
    def sgd(self):
        # 좌표 i j와 그 위치에 있는 rating
        for i, j, r in self.samples:
            # 사용자 i, 아이템 j에 대한 평점 예측치 계산
            prediction = self.get_prediction(i, j)
            # 실제 평점과 비교한 오차 계산
            e = (r - prediction)

            # 사용자 평가 경향 계산 및 업데이트
            self.b_u[i] += self.alpha * (e - (self.beta * self.b_u[i]))
            # 아이템 평가 경향 계산 및 업데이트
            self.b_d[j] += self.alpha * (e - (self.beta * self.b_d[j]))

            self.P[i, :] += self.alpha * ((e * self.Q[j, :]) - (self.beta * self.P[i, :]))
            self.Q[j, :] += self.alpha * ((e * self.P[i, :]) - (self.beta * self.Q[j, :]))

    # 평점예측값
    # i사용자에 대한 j아이템 대한 평점예측
    def get_prediction(self, i, j):
        # self.b : 전체평점
        # self.b_u[i] : 사용자 유저에 대한 평가 경향
        # self.b_d[j] : 아이템에 대한 평가 경향
        # self.P[i,:] : i번쨰 사용자의 요인값
        # self.Q[j,].T : 아이템 요인에 대해서 트랜포지안 값을 연산을 하
        # dot(self.Q[j,:].T): 잠재 요인 벡터를 내적(dot product)하여 사용자 i와 아이템 j 간의 상호작용을 모델링
        # ->  i와 아이템 j 간의 평점 예측치를 계산하는 식
        prediction = self.b + self.b_u[i] + self.b_d[j] + self.P[i, :].dot(self.Q[j, :].T)
        return prediction

    # Test set 선정
    # 분리된 테스트셋을 넘겨받아서 클래스 내부의 테스트셋을 만드는 함수
    def set_test(self, ratings_test):
        test_set = []
        for i in range(len(ratings_test)):
            x = self.user_id_index[ratings_test.iloc[i, 0]]
            y = self.item_id_index[ratings_test.iloc[i, 1]]
            z = ratings_test.iloc[i, 2]
            test_set.append([x, y, z])
            # 테스트셋으로 만든건 0으로
            self.R[x, y] = 0
        self.test_set = test_set
        return test_set

    # Test set RMSE 계산
    def test_rsme(self):
        error = 0
        for one_set in self.test_set:
            predicted = self.get_prediction(one_set[0], one_set[1])
            error += pow(one_set[2] - predicted, 2)
        return np.sqrt(error / len(self.test_set))

    def test(self):
        # 사용자 요인, scale = 표준편차(잠재변수의 개수 / 1), size = 우저의개수, 잠재요인의 개수로 사용자요인의 크기값을 지정
        self.P = np.random.normal(scale=1. / self.K,
                                  size=(self.num_users, self.K))
        # 아이템 요인
        self.Q = np.random.normal(scale=1. / self.K,
                                  size=(self.num_items, self.K))
        # 사용자 평가 경향도 초기화
        self.b_u = np.zeros(self.num_users)
        self.b_d = np.zeros(self.num_items)
        # 평점의 전체 평균, 0이 아닌 R만 남는다 그러면 전체 사용자 평점 평균을 낸다?..
        self.b = np.mean(self.R[self.R.nonzero()])

        # 평점행렬 R중에서 평점이 있는 요소만 들고온다
        rows, columns = self.R.nonzero()
        # print(rows, columns)
        # SDG를 적용할 상황(평가가 된 요소들을 x,y좌표로 들고온다)
        self.samples = [(i, j, self.R[i, j]) for i, j in zip(rows, columns)]

        # sgd가 한번 실행될때마다 RMSE가 얼마나 계선되는지
        training_process = []
        for i in range(self.iterations):
            # 다양한 시작점에서 계속해서 셔플을 하면서 SGD를 수행하겠다
            np.random.shuffle(self.samples)

            self.sgd()

            # 트레이닝과 테스트에 대한 RMSE를 따로
            rmse1 = self.rmse()
            # rmse2 = self.rmse()
            rmse2 = self.test_rsme()
            training_process.append((i + 1, rmse1, rmse2))

            if self.verbose:
                if (i + 1) % 10 == 0:
                    print('iter : %d; TRAIN RMSE = %.4f TEST RMSE = %.4f' % (i + 1, rmse1, rmse2))
        return training_process

    ### 가중치 없는 온라인 학습 start###
    # def online_sgd(self, new_sample):
    #   # 새로운 샘플의 사용자, 아이템, 평점을 가져옴
    #   i, j, r = new_sample
    #   print(i,j,r)
    #   # 사용자 i, 아이템 j에 대한 평점 예측치 계산
    #   prediction = self.get_prediction(i, j)
    #   # 실제 평점과 비교한 오차 계산
    #   e = (r - prediction)

    #   # 사용자 평가 경향 계산 및 업데이트
    #   self.b_u[i] += self.alpha * (e - (self.beta * self.b_u[i]))
    #   # 아이템 평가 경향 계산 및 업데이트
    #   self.b_d[j] += self.alpha * (e - (self.beta * self.b_d[j]))

    #   self.P[i, :] += self.alpha * ((e * self.Q[j, :]) - (self.beta * self.P[i, :]))
    #   self.Q[j, :] += self.alpha * ((e * self.P[i, :]) - (self.beta * self.Q[j, :]))

    # def online_learning(self, new_samples):
    #   for sample in new_samples:
    #     self.online_sgd(sample)
    ### 가중치 없는 온라인 학습 end###

    ### 가중치 있는 온라인 학습 start###
    def online_learning(self, new_sample, new_weight):
        # new_sample에는 유저, 뉴스, 점수 new_weight는 중요도
        self.online_sgd(new_sample, new_weight)

    def online_sgd(self, new_sample, new_weight):
        i, j, r = new_sample
        # 새로운 데이터에 가중치를 적용하여 업데이트 수행
        prediction = self.get_prediction(i, j)
        e = (r - prediction) * new_weight

        self.b_u[i] += self.alpha * (e - (self.beta * self.b_u[i]))
        self.b_d[j] += self.alpha * (e - (self.beta * self.b_d[j]))

        self.P[i, :] += self.alpha * ((e * self.Q[j, :]) - (self.beta * self.P[i, :]))
        self.Q[j, :] += self.alpha * ((e * self.P[i, :]) - (self.beta * self.Q[j, :]))

    ### 가중치 있는 온라인 학습 end###

    # 유저아이디와 아이템 아이디로 예측치를 계산해서 돌려준다
    # -> 어떤 인덱스 값이 들어와도 자동으로 매핑되서 예측치계산
    def get_one_prediction(self, user_id, item_id):
        return self.get_prediction(self.user_id_index[user_id],
                                   self.item_id_index[item_id])

    def full_prediction(self):
        return self.b + self.b_u[:, np.newaxis] + self.b_d[np.newaxis, :] + self.P.dot(self.Q.T)