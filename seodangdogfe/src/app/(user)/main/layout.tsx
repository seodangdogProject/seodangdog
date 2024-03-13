export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <>
    <h3>네브바</h3>
    {children}
    </>
  );
}
