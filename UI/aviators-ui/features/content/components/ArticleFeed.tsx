// components/ArticleFeed.tsx
"use client";

import { useAppSelector } from "@/lib/store/hooks";
import { dummyArticles } from "./ArticleDummy";
// import {api} from "@/app/api/axiosIntance"

export function ArticleFeed() {
  const articles = dummyArticles;
  // const { articles, articlesLoading } = useAppSelector(
  //   (state) => state.content,
  // );

  // if (articlesLoading) {
  //   return <div className="p-6 text-slate-400">Filtering articles...</div>;
  // }

  // if (articles.length === 0) {
  //   return (
  //     <div className="p-8 text-center text-slate-500">
  //       No published articles found in this category.
  //     </div>
  //   );
  // }

  const getFormattedDate = (date: Date | null): string => {
    if (!date) return "";
    const formatter = new Intl.DateTimeFormat("en-US", {
      month: "short", // 'Nov', 'Sep', etc.
      year: "numeric", // '2026'
    });

    // Using formatToParts to combine with a comma: "Nov, 2026"
    const parts = formatter.formatToParts(date);
    const month = parts.find((p) => p.type === "month")?.value;
    const year = parts.find((p) => p.type === "year")?.value;

    return `${month}, ${year}`;
  };

  return (
    <div className="p-6 flex-1 overflow-scroll">
      {articles.map((article) => (
        <article
          key={article.id}
          className="  p-5 my-2 flex flex-col justify-between border-b-2 border-secondary"
        >
          <div>
            <div className="flex">
              <p className="text-sm text-secondary-foreground line-clamp-3 mb-2">
                {article.authorId +
                  " : " +
                  getFormattedDate(article.publishedAt)}
              </p>
            </div>
            <h2 className="text-lg font-bold text-foreground mb-1">
              {article.title}
            </h2>
            <p className="text-sm text-secondary-foreground line-clamp-3 mb-2">
              {article.summary}
            </p>
            <p className="text-sm text-secondary-foreground line-clamp-3 mb-2">
              {article.content.substring(
                0,
                Math.min(200, article.content.length),
              )}
              ...
            </p>
          </div>

          {/* <div className="flex items-center justify-between border-t border-slate-800 pt-4">
            <span className="text-xs text-indigo-400 font-medium">
              Published
            </span>
          </div> */}
        </article>
      ))}
    </div>
  );
}
