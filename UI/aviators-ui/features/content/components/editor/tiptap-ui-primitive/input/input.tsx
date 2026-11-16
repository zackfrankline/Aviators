"use client";

import { cn } from "@/features/content/utils/tiptap-utils";
import "@/features/content/components/editor/tiptap-ui-primitive/input/input.scss";

function Input({ className, type, ...props }: React.ComponentProps<"input">) {
  return (
    <input
      type={type}
      data-slot="tiptap-input"
      className={cn("tiptap-input", className)}
      {...props}
    />
  );
}

export { Input };
