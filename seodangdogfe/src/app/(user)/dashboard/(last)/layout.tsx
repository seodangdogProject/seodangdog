import NavBar from "@/components/NavBar";
import style from "./layout.module.css";
export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return <>{children}</>;
}
