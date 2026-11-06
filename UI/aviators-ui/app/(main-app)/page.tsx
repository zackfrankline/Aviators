// app/page.tsx
"use client";

import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState, AppDispatch } from "@/app/lib/redux/store";
import { refreshSession } from "@/app/lib/redux/authSlice";
import { fetchInitialData } from "@/app/lib/redux/contentSlice";
import ArticleFeed from "@/components/ArticleFeed";
import TopBar from "@/components/TopBar";
import Sidebar from "@/components/Sidebar";
import Workspace from "@/components/Workspace";

export default function Home() {
  const dispatch = useDispatch<AppDispatch>();

  const { isHydrating } = useSelector((state: RootState) => state.auth);
  const { status, error } = useSelector((state: RootState) => state.content);

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
