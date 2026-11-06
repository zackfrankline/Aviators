// components/ArticleFeed.tsx
"use client";

import { useSelector } from "react-redux";
import { RootState } from "@/app/lib/redux/store";
// import {api} from "@/app/api/axiosIntance"

export default function ArticleFeed() {
  const { articles, articlesLoading } = useSelector(
    (state: RootState) => state.content,
  );


  if (articlesLoading) {
    return <div className="p-6 text-slate-400">Filtering articles...</div>;
  }

  if (articles.length === 0) {
    return (
      <div className="p-8 text-center text-slate-500">
        No published articles found in this category.
      </div>
    );
  }

  return (
    <div className="p-6 overflow-y-auto flex-1 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      {articles.map((article) => (
        <article
          key={article.id}
          className="bg-slate-900 border border-slate-800 rounded-xl p-5 flex flex-col justify-between"
        >
          <div>
            <h2 className="text-lg font-bold text-slate-100 mb-2">
              {article.title}
            </h2>
            <p className="text-sm text-slate-400 line-clamp-3 mb-4">
              {article.summary}
            </p>
          </div>

          <div className="flex items-center justify-between border-t border-slate-800 pt-4">
            <span className="text-xs text-indigo-400 font-medium">
              Published
            </span>
          </div>
        </article>
      ))}
    </div>
  );
}
