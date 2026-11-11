// app/page.tsx
"use client";

import { useEffect } from "react";
import { ArticleFeed } from "@/features/content";
import TopBar from "@/components/layout/TopBar";
import Sidebar from "@/components/layout/Sidebar";
import Workspace from "@/components/layout/Workspace";
import { refreshSession } from "@/features/auth";
import { fetchInitialData } from "@/features/content/store/contentSlice";
import { useAppDispatch, useAppSelector } from "@/lib/store/hooks";

export default function Home() {
  const dispatch = useAppDispatch(); 

  const { isHydrating } = useAppSelector((state) => state.auth);
  const { status, error } = useAppSelector((state) => state.content);

  useEffect(() => {
    // 1. Silent refresh check via cookie
    void dispatch(refreshSession());
    // 2. Preload public articles & categories
    void dispatch(fetchInitialData());
  }, [dispatch]);

  if (status === "loading" && isHydrating) {
    return (
      <div className="flex-1 flex items-center justify-center text-slate-500">
        Loading workspace feed...
      </div>
    );
  }

  if (status === "failed") {
    return (
      <div className="p-8 text-rose-400">
        <p>Failed to load articles: {error}</p>
      </div>
    );
  }

  return (
    <>
      {/* Top Navigation */}
      <TopBar />
      {/* App Body Grid */}
      <div className="flex flex-1 b overflow-hidden">
        <Sidebar />
        <main className="flex-1 bg-slate-50 m-2 rounded-xl border border-slate-800 shadow-2xl overflow-hidden relative flex flex-col">
          {/* <ArticleFeed /> */}
          <Workspace />
        </main>
      </div>
    </>
  );
}
