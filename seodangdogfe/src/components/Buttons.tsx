import { IPageObj } from '../atoms/type';
import { cls } from '@/utils/cls';

interface IButtonsProps {
    pageObjArray: IPageObj[];
    currentPageNum: number;
    handlePointClick: (pageNum: number) => void;
}

const Buttons = (props: IButtonsProps) => {
    return (
        <>
            {props.pageObjArray.map((item, index) => {
                return (
                    <div
                        key={item.pageNum}
                        style={{
                            width: '0.5rem', // w-4
                            height: '0.5rem', // h-4
                            borderRadius: '9999px', // rounded-full
                            cursor: 'pointer', // cursor-pointer
                            transition: 'all 0.3s', // transition-all

                            backgroundColor:
                                props.currentPageNum === item.pageNum
                                    ? 'black'
                                    : 'gray',
                        }}
                        onClick={() => {
                            props.handlePointClick(item.pageNum);
                        }}
                    ></div>
                );
            })}
        </>
    );
};

export default Buttons;
