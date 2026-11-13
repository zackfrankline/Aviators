import "./globals.css";
import {Lato} from 'next/font/google'
import StoreProvidor from "@/components/providors/StoreProvider";

const lato = Lato({
  weight:'400',
  subsets:['latin'],
})

export const metadata = {
  title: "Aviator Content Workspace",
  description: "Next.js Article Management System",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className={lato.className}>
      <body className="bg-slate-25 text-slate-950 h-screen overflow-hidden flex flex-col selection:bg-indigo-500/30">
        <StoreProvidor>{children}</StoreProvidor>
      </body>
    </html>
  );
}
