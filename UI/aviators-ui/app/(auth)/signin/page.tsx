// app/login/page.tsx
"use client";

import { useState, useEffect, Suspense } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useRouter, useSearchParams } from "next/navigation";
import { registerAdminUser } from "@/app/lib/redux/authSlice";
import { RootState, AppDispatch } from "@/app/lib/redux/store";
import Link from "next/link";

// export interface RegisterAdminRequest {
//     name: string;
//     userName : string;
//     email: string;
//     passwordHash: string;
//     secretKey:string;
// }

function SignUpForm() {
  const dispatch = useDispatch<AppDispatch>();
  const router = useRouter();
  const searchParams = useSearchParams();

  const redirectUrl = searchParams.get("redirect") || "/";

  const { isAuthenticated, status, error } = useSelector(
    (state: RootState) => state.auth,
  );

  const [name, setName] = useState("");
  const [userName, setUserName] = useState("");
  const [passwordHash, setPasswordHash] = useState("");
  const [confirmPasswordHash, setConfirmPasswordHash] = useState("");
  const [email, setEmail] = useState("");
  const [secretKey, setSecretKey] = useState("");
  const [formError, setFormError] = useState<string | null>(null);

  useEffect(() => {
    if (isAuthenticated) {
      router.push(redirectUrl);
    }
  }, [isAuthenticated, redirectUrl, router]);

  const handleSubmit = async (e: React.SubmitEvent) => {
    e.preventDefault();
    await dispatch(
      registerAdminUser({ name, userName, passwordHash, email, secretKey }),
    );
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-950 px-4">
      <div className="w-full max-w-md bg-slate-900 border border-slate-800 p-8 rounded-2xl shadow-xl">
        <h1 className="text-2xl font-bold text-slate-100 mb-2 text-center">
          Sign In
        </h1>
        <p className="text-xs text-slate-400 text-center mb-6">
          Access subscriber-only content and workspace
        </p>

        {error && (
          <div className="mb-4 p-3 bg-rose-500/10 border border-rose-500/30 text-rose-400 text-xs rounded-lg">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="flex flex-col gap-4 mb-1">
          <div>
            <input
              type="text"
              placeholder="Name..."
              aria-label="name"
              required
              value={name}
              onChange={(e) => setName(e.target.value)}
              className="w-full bg-slate-800 border border-slate-700 rounded-lg px-3 py-2 text-sm text-slate-100 focus:outline-none focus:border-indigo-500"
            />
          </div>

          <div>
            <input
              type="text"
              placeholder="Username..."
              aria-label="username"
              required
              value={userName}
              onChange={(e) => setUserName(e.target.value)}
              className="w-full bg-slate-800 border border-slate-700 rounded-lg px-3 py-2 text-sm text-slate-100 focus:outline-none focus:border-indigo-500"
            />
          </div>

          <div>
            <input
              type="text"
              placeholder="Email..."
              aria-label="email"
              required
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full bg-slate-800 border border-slate-700 rounded-lg px-3 py-2 text-sm text-slate-100 focus:outline-none focus:border-indigo-500"
            />
          </div>

          <div>
            <input
              type="password"
              placeholder="Password..."
              aria-label="password"
              required
              value={passwordHash}
              onChange={(e) => setPasswordHash(e.target.value)}
              className="w-full bg-slate-800 border border-slate-700 rounded-lg px-3 py-2 text-sm text-slate-100 focus:outline-none focus:border-indigo-500"
            />
          </div>
          <div>
            <input
              type="password"
              placeholder="Password..."
              aria-label="password"
              required
              value={confirmPasswordHash}
              onChange={(e) => {
                setConfirmPasswordHash(e.target.value);
                if (confirmPasswordHash && passwordHash) {
                  setFormError(
                    !passwordHash.startsWith(confirmPasswordHash)
                      ? "Password doesn't match"
                      : null,
                  );
                }
              }}
              className="w-full bg-slate-800 border border-slate-700 rounded-lg px-3 py-2 text-sm text-slate-100 focus:outline-none focus:border-indigo-500"
            />
          </div>

          <div>
            <input
              type="password"
              placeholder="Admin Secret..."
              aria-label="admin-secret"
              required
              value={secretKey}
              onChange={(e) => setSecretKey(e.target.value)}
              className="w-full bg-slate-800 border border-slate-700 rounded-lg px-3 py-2 text-sm text-slate-100 focus:outline-none focus:border-indigo-500"
            />
          </div>
          {formError && <p style={{ color: "red" }}>{formError}</p>}

          <button
            type="submit"
            disabled={status === "loading" || formError !== null}
            className="mt-2 w-full bg-indigo-600 hover:bg-indigo-500 disabled:opacity-50 text-white font-medium py-2 rounded-lg text-sm transition"
          >
            {status === "loading" ? "Signing In..." : "Sign In"}
          </button>
        </form>
        <Link href="/signin">
          <p className="text-xs text-slate-100 text-center mb-6">
            Don&apos;t have an Account? Sign Up
          </p>
        </Link>
      </div>
    </div>
  );
}

export default function SignInPage() {
  return (
    <Suspense>
      <SignUpForm />
    </Suspense>
  );
}
