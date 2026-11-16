"use client";

import { cn } from "@/features/content/utils/tiptap-utils";
import "./textarea.scss";

function Textarea({ className, ...props }: React.ComponentProps<"textarea">) {
  return (
    <textarea
      data-slot="tiptap-textarea"
      className={cn("tiptap-textarea", className)}
      {...props}
    />
  );
}

export { Textarea };
