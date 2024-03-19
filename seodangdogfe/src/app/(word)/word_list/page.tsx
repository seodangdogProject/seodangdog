'use client';
// WordGame.tsx
import React, {
    useState,
    useEffect,
    useCallback,
    startTransition,
} from 'react';
import {
    useRecoilState,
    RecoilRoot,
    useRecoilCallback,
    useRecoilValue,
} from 'recoil';
import styles from './wordlist_layout.module.css';
import {
    koWordListState,
    engWordListState,
    Item,
} from '../../../atoms/wordListRecoil';
import SearchIcon from '../../../assets/search-icon.svg';
import WordDetailModal from '../../../components/wordComponent/wordDetailModal';

const OneWord: React.FC = () => {
    const engWordList = useRecoilValue(engWordListState);
    const koWordList = useRecoilValue(koWordListState);
    const [language, setLanguage] = useState('ko');
    const [engColor, setEngColor] = useState(styles.unselected_toggle_item);
    const [koColor, setKoColor] = useState(styles.selected_toggle_item);
    const [wordList, setWordList] = useState(koWordList);
    const [isOpenModal, setOpenModal] = useState<boolean>(false);
    const [clickedWord, setClickedWord] = useState<Item | null>(null);

    useEffect(() => {
        if (language === 'ko') {
            setWordList(koWordList);
        } else {
            setWordList(engWordList);
        }
    }, [language, koWordList, engWordList]);

    // 이동
    const handleOpenModal = (word: Item) => {
        setClickedWord((prevClickedWord) => word); // 함수형 업데이트 사용
        setOpenModal(true);
    };

    const handleCloseModal = () => {
        setOpenModal(false);
    };

    const handleToggleKo = () => {
        console.log('한글로 변경 버튼');
        setLanguage('ko');
        setEngColor(styles.unselected_toggle_item);
        setKoColor(styles.selected_toggle_item);
        // setWordList(koWordList);
    };

    const handleToggleEng = () => {
        console.log('영어로 변경 버튼');
        setLanguage('eng');
        setEngColor(styles.selected_toggle_item);
        setKoColor(styles.unselected_toggle_item);
        // setWordList(engWordList);
    };

    return (
        <>
            <div className={styles.container}>
                {isOpenModal && (
                    <WordDetailModal
                        isOpen={isOpenModal}
                        onClose={handleCloseModal}
                        clickedWord={clickedWord}
                    ></WordDetailModal>
                )}
                <div className={styles.content_cotainer}>
                    <div className={styles.search_cotinaer}>
                        <input type="text" className={styles.search_input} />
                        <SearchIcon />
                    </div>
                    <div className={styles.toggel_container}>
                        <div
                            className={`${styles.toggle_item} ${koColor}`}
                            onClick={handleToggleKo}
                        >
                            한글
                        </div>
                        <div
                            className={`${styles.toggle_item} ${engColor}`}
                            onClick={handleToggleEng}
                        >
                            영어
                        </div>
                    </div>
                    <div className={styles.background_container}>
                        {wordList.map((item, index) => (
                            <div
                                key={item.idx}
                                className={styles.wordBox}
                                onClick={() => handleOpenModal(item)}
                            >
                                <div className={styles.word}>{item.word}</div>
                                <div className={styles.mean_box}>
                                    {item.mean.map((mean, idx) => (
                                        <div key={idx} className={styles.mean}>
                                            {mean}
                                        </div>
                                    ))}
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </>
    );
};

export default OneWord;
