import "./globals.css";
import ClientProviders from "./lib/redux/Providers";

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
        <ClientProviders>{children}</ClientProviders>
      </body>
    </html>
  );
}
