import "./globals.css";
import StoreProvidor from "@/components/providors/StoreProvider";

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
    <html lang="en">
      <body className="bg-slate-25 text-slate-950 h-screen overflow-hidden flex flex-col font-sans selection:bg-indigo-500/30">
        <StoreProvidor>{children}</StoreProvidor>
      </body>
    </html>
  );
}
