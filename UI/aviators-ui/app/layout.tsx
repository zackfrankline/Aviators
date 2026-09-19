import Sidebar from "@/components/Sidebar";
import "./globals.css";
import TopBar from "@/components/TopBar";
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
  const userRole = "ADMIN";

  return (
    <html lang="en">
      <body className="bg-slate-25 text-slate-950 h-screen overflow-hidden flex flex-col font-sans selection:bg-indigo-500/30">
        <ClientProviders>
          {/* Top Navigation */}
          <TopBar role={userRole} />

          {/* App Body Grid */}
          <div className="flex flex-1 b overflow-hidden">
            <Sidebar role={userRole} />
            <main className="flex-1 bg-slate-50 m-2 rounded-xl border border-slate-800 shadow-2xl overflow-hidden relative flex flex-col">
              {children}
            </main>
          </div>
        </ClientProviders>
      </body>
    </html>
  );
}
