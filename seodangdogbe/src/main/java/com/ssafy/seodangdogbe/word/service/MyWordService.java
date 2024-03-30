package com.ssafy.seodangdogbe.word.service;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.word.domain.MetaWord;
import com.ssafy.seodangdogbe.word.domain.UserWord;
import com.ssafy.seodangdogbe.word.dto.MyWordResponseDto;
import com.ssafy.seodangdogbe.word.dto.WordDto;
import com.ssafy.seodangdogbe.word.repository.MetaWordRepository;
import com.ssafy.seodangdogbe.word.repository.MyWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyWordService {
    private final MyWordRepository myWordRepository;
    private final MetaWordRepository metaWordRepository;
    private final WordMeanService wordMeanService;
    private UserService userService;

    @Autowired
    public MyWordService(MyWordRepository myWordRepository,  WordMeanService wordMeanService, UserService userService, MetaWordRepository metaWordRepository) {
        this.myWordRepository = myWordRepository;
        this.metaWordRepository = metaWordRepository;
        this.wordMeanService = wordMeanService;
        this.userService = userService;

    }

    // 사용자의 모든 단어를 조회하는 메서드
    public MyWordResponseDto findAllUserWords() {
        int userSeq = userService.getUserSeq();
        List<UserWord> userWords = myWordRepository.findAllUserWords(userSeq);
        List<MyWordResponseDto.WordInfo> wordInfos = new ArrayList<>();

        for (UserWord userWord : userWords) {
            MetaWord metaWord = metaWordRepository.findByWord(userWord.getWord())
                    .orElse(null);
            if (metaWord != null && "kor".equals(metaWord.getWordLang())) {
                WordDto.MetaWordDto metaWordDto = new WordDto.MetaWordDto(metaWord);
                String mean1 = null, mean2 = null;
                if (!metaWordDto.getItems().isEmpty()) {
                    mean1 = metaWordDto.getItems().get(0).getDefinition();
                    if (metaWordDto.getItems().size() > 1) {
                        mean2 = metaWordDto.getItems().get(1).getDefinition();
                    }
                }
                wordInfos.add(new MyWordResponseDto.WordInfo(
                        userWord.getWordSeq(),
                        userWord.getWord(),
                        mean1,
                        mean2
                ));
            }
        }

        return new MyWordResponseDto(wordInfos);
    }
    public MyWordResponseDto findAllEngWords() {
        int userSeq = userService.getUserSeq();
        List<UserWord> userWords = myWordRepository.findAllUserWords(userSeq);
        List<MyWordResponseDto.WordInfo> wordInfos = new ArrayList<>();

        for (UserWord userWord : userWords) {
            MetaWord metaWord = metaWordRepository.findByWord(userWord.getWord())
                    .orElse(null);
            if (metaWord != null && "eng".equals(metaWord.getWordLang())) {
                WordDto.MetaWordDto metaWordDto = new WordDto.MetaWordDto(metaWord);
                String mean1 = null, mean2 = null;
                if (!metaWordDto.getItems().isEmpty()) {
                    mean1 = metaWordDto.getItems().get(0).getDefinition();
                    if (metaWordDto.getItems().size() > 1) {
                        mean2 = metaWordDto.getItems().get(1).getDefinition();
                    }
                }
                wordInfos.add(new MyWordResponseDto.WordInfo(
                        userWord.getWordSeq(),
                        userWord.getWord(),
                        mean1,
                        mean2
                ));
            }
        }

        return new MyWordResponseDto(wordInfos);
    }


    // 단어 삭제 메서드
    public boolean deleteWord(Long wordSeq) {
        int userSeq = userService.getUserSeq();
        return myWordRepository.findById(wordSeq).map(userWord -> {
            if (userWord.getUser().getUserSeq() == userSeq) {
                userWord.setIsDelete(true);
                myWordRepository.save(userWord);
                return true;
            } else {
                return false;
            }
        }).orElse(false);
    }

//    public MyWordResponseDto findWordInfo(String word) {
//        int userSeq = userService.getUserSeq();
//        // 특정 단어에 대한 UserWord 객체를 조회합니다.
//        UserWord userWord = myWordRepository.findUserWordByUserSeqAndWord(userSeq, word);
//        List<MyWordResponseDto.WordInfo> wordInfos = new ArrayList<>();
//
//        if (userWord != null) {
//            // MetaWord 객체를 조회합니다.
//            MetaWord metaWord = metaWordRepository.findByWord(word).orElse(null);
//            if (metaWord != null) {
//                String mean1 = null, mean2 = null;
//                if (!metaWord.getWordItemList().isEmpty()) {
//                    mean1 = metaWord.getWordItemList().get(0).getDefinition(); // 첫 번째 뜻
//                    if (metaWord.getWordItemList().size() > 1) {
//                        mean2 = metaWord.getWordItemList().get(1).getDefinition(); // 두 번째 뜻
//                    }
//                }
//                wordInfos.add(new MyWordResponseDto.WordInfo(
//                        userWord.getWordSeq(), // userWord에서 ID를 직접 사용합니다.
//                        metaWord.getWord(),
//                        mean1,
//                        mean2
//                ));
//            }
//        }
//
//        return new MyWordResponseDto(wordInfos);
//    }



    public MyWordResponseDto findWordSearch(String prefix) {
        int userSeq = userService.getUserSeq();
        List<MetaWord> metaWords = metaWordRepository.findByWordContaining(prefix);
        List<MyWordResponseDto.WordInfo> wordInfos = new ArrayList<>();

        for (MetaWord metaWord : metaWords) {
            List<UserWord> userWords = myWordRepository.findUserWordsByUserSeqAndWord(userSeq, metaWord.getWord());

            for (UserWord userWord : userWords) {
                String mean1 = null, mean2 = null;
                if (!metaWord.getWordItemList().isEmpty()) {
                    mean1 = metaWord.getWordItemList().get(0).getDefinition();
                    if (metaWord.getWordItemList().size() > 1) {
                        mean2 = metaWord.getWordItemList().get(1).getDefinition();
                    }
                }

                wordInfos.add(new MyWordResponseDto.WordInfo(
                        userWord.getWordSeq(),
                        metaWord.getWord(),
                        mean1,
                        mean2
                ));
            }
        }
        return new MyWordResponseDto(wordInfos);
    }


}
