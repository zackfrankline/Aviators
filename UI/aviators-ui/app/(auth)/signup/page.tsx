"use client";

import { Suspense } from "react";

import { SignUpForm } from "@/features/auth";

export default function SignUpPage() {
  return (
    <Suspense>
      <SignUpForm />
    </Suspense>
  );
}
