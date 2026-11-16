"use client";

import { useState } from "react";
import type { JSONContent } from "@tiptap/core";
import { SimpleEditor } from "@/features/content";

export default function NewArticle() {
  const [articleContent, setArticleContent] = useState<JSONContent | null>(
    null,
  );
  // console.log(JSON.stringify(articleContent, null, 2));
  return (
    <>
      <SimpleEditor onContentChange={setArticleContent} />
      {/* <details>
                <summary>Rich-text content (JSON)</summary>
                <pre>{JSON.stringify(articleContent, null, 2)}</pre>
            </details> */}
    </>
  );
}
