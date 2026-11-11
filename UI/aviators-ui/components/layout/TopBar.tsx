"use client";

import Link from "next/link";
import { useWorkspace } from "@/context/WorkspaceContext";
import { IoSearchOutline } from "react-icons/io5";
import { IoIosNotificationsOutline } from "react-icons/io";

import { TfiWrite } from "react-icons/tfi";
import { ReactNode } from "react";
import { useAppSelector } from "@/lib/store/hooks";

export default function TopBar() {
  // const dispatch = useDispatch<AppDispatch>();
  const { user, isAuthenticated, isHydrating } = useAppSelector(
    (state) => state.auth,
  );

  const { openTab } = useWorkspace();
  console.log("User", user, "isAuthenticated:", isAuthenticated);

  const handleCreateNew = () => {
    openTab({
      subType: "ARTICLE",
      title: "New Article",
      type: "CREATE",
    });
  };

  //profileUI for Role Based UI
  let profileUI: ReactNode;
  let adminCreateUI: ReactNode;

  if (isHydrating) {
    profileUI = (
      <span className="text-xs text-slate-500">Checking session...</span>
    );
  } else if (isAuthenticated && user) {
    profileUI = (
      <div className="flex items-center gap-4">
        {/* Notifications (Dynamic based on role) */}
        <button className="p-2 text-slate-400 transition-colors hover:text-slate-500 relative">
          <IoIosNotificationsOutline size={24} />
        </button>

        {/* Profile Avatar */}
        <div className="w-9 h-9 rounded-full bg-slate-500 border border-slate-500 flex items-center justify-center cursor-pointer">
          <span className="text-sm font-semibold">AD</span>
        </div>
      </div>
    );

    if (user.role === "ROLE_ADMIN") {
      adminCreateUI = (
        <button
          type="button"
          onClick={handleCreateNew}
          className="flex flex-row items-center gap-2 rounded-md px-3 py-2 text-slate-400 transition-colors hover:text-slate-500"
        >
          <TfiWrite size={18} aria-hidden="true" />
          <span className="hidden sm:inline">Write</span>
        </button>
      );
    }
  } else {
    profileUI = (
      <div className="">
        <Link
          href="/login"
          className="text-xs bg-indigo-400 hover:bg-indigo-500 text-white font-medium px-3 py-1.5 rounded transition"
        >
          Sign In
        </Link>
      </div>
    );
  }

  return (
    <header className="h-16 border-b-4  border-slate-100 bg-slate-50 flex items-center justify-between px-6 shrink-0 z-10">
      {/* Brand & Search */}
      <div className="flex items-center gap-8 w-1/2">
        <h1 className="text-xl font-bold tracking-widest text-indigo-400">
          AVIATOR
        </h1>
        <div className="relative flex flex-row gap-4 items-centerw-full  text-sm rounded-full px-4 py-2  transition-colors">
          <input
            type="text"
            placeholder="Search articles globally..."
            className="focus:outline-none"
          />
          <IoSearchOutline color="#708090" size={23} />
        </div>
      </div>

      {/* Utilities */}
      <div className="flex items-center gap-4">
        {/* ADMIN CREATE UI */}
        {adminCreateUI}
        {profileUI}
      </div>
    </header>
  );
}
