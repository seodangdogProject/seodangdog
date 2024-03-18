import NavBar from '@/components/NavBar';
import style from './layout.module.css';
export default function RootLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <>
            <main className={style.main}>
                <NavBar />
                <section style={{ width: '100%' }}>{children}</section>
            </main>
        </>
    );
}
