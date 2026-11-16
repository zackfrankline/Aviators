"use client";

import dynamic from "next/dynamic";

const LazySimpleEditor = dynamic(
  () =>
    import("./tiptap-templates/simple/simple-editor").then(
      (module) => module.SimpleEditor,
    ),
  {
    loading: () => <div className="flex-1 h-64 items-center justify-center animate-pulse bg-gray-200 rounded" >Editor loading...</div>,
    ssr: false, // Optional: prevents server-side rendering of this heavy dependency
  },
);

export default LazySimpleEditor;
