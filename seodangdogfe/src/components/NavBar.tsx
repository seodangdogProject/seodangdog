'use client';

// next 모듈
import Image from 'next/image';

// 외부 모듈
import classNames from 'classnames/bind';

// 내부 모듈
import style from './NavBar.module.css';

// SVG 파일
import MainIcon from '../assets/main-icon.svg';
import DashboardIcon from '../assets/dashboard-icon.svg';
import DictIcon from '../assets/dict-icon.svg';
import GameIcon from '../assets/game-icon.svg';
import LogoutIcon from '../assets/logout-icon.svg';
export default function NavBar() {
    const cx = classNames.bind(style);
    return (
        <>
            <nav className={style.nav}>
                <h3 className={style.logo}>서당독</h3>
                <ul className={style.item_container}>
                    <li
                        className={cx('item', {
                            active: true,
                        })}
                    >
                        <div>
                            <MainIcon />
                        </div>
                        <div>메인페이지</div>
                    </li>
                    <li className={cx('item', {})}>
                        <div>
                            <DashboardIcon />
                        </div>
                        <div>대시보드</div>
                    </li>
                    <li className={cx('item', {})}>
                        <div>
                            <DictIcon />
                        </div>
                        <div>단어장</div>
                    </li>
                    <li className={cx('item', {})}>
                        <div>
                            <GameIcon />
                        </div>
                        <div>게임</div>
                    </li>
                </ul>
                <div className={cx('logout')}>
                    <div className={cx('item', 'logout_item')}>
                        <div>
                            <LogoutIcon />
                        </div>
                        <div>로그아웃</div>
                    </div>
                </div>
            </nav>
        </>
    );
}
