import React from 'react';
import commonStyles from './Section_common.module.css';
import Image from 'next/image';

interface ISectionProps {
    pageNum: number;
    window: Window;
    pageRefs: React.MutableRefObject<HTMLDivElement[]>;
}
const Section = (props: ISectionProps) => {
    return (
        <div
            ref={(element) => {
                props.pageRefs.current[props.pageNum] = element!;
            }}
            style={{
                width: '100vw',
                height: '100vh',
                overflowX: 'hidden',
            }}
        >
            <main className={commonStyles.main}>
                <Image
                    src="/images/landing-fifth.png"
                    alt="fifthImage"
                    width={600}
                    height={400}
                    style={{
                        marginRight: '40px',
                    }}
                />
                <div className={commonStyles.description}>
                    <div className={commonStyles.title}>
                        저장한 단어로
                        <br />
                        <span style={{ color: 'rgba(151, 88, 255, 1)' }}>
                            퀴즈
                        </span>
                        를 풀어보세요!
                    </div>
                    <div className={commonStyles.content}>
                        단어장에서 단어 10개를 선정해 <br />
                        스피드 퀴즈를 풀어볼 수 있습니다.{' '}
                    </div>
                </div>
            </main>
        </div>
    );
};

export default Section;
