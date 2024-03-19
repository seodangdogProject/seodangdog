'use client';
import NavBar from '@/components/NavBar';
import style from './layout.module.css';
import { RecoilRoot } from 'recoil';

export default function RootLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <>
            <main className={style.main}>
                <NavBar />
                <RecoilRoot>
                    <section style={{ width: '100%' }}>{children}</section>
                </RecoilRoot>
            </main>
        </>
    );
}
