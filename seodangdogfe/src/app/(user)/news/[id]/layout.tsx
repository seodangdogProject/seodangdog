import NewsDetailContainer from "@/containers/NewsDetailContainer";

export default function Layout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return <>{children}</>;
}
