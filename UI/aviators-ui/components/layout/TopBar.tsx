"use client";

import Link from "next/link";
import { useWorkspace } from "@/context/WorkspaceContext";
import { IoSearchOutline } from "react-icons/io5";
import { IoIosNotificationsOutline } from "react-icons/io";

import { TfiWrite } from "react-icons/tfi";
import { ReactNode, useEffect, useRef, useState } from "react";
import { useAppSelector } from "@/lib/store/hooks";
import { ProfileCard } from "@/features/profile";

export default function TopBar() {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const menuRef = useRef<HTMLDivElement>(null);

  // Close the card when clicking anywhere outside
  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (menuRef.current && !menuRef.current.contains(event.target as Node)) {
        setIsOpen(false);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  // const dispatch = useDispatch<AppDispatch>();
  const { user, isAuthenticated, isHydrating } = useAppSelector(
    (state) => state.auth,
  );

  const { openTab } = useWorkspace();
  // console.log("User", user, "isAuthenticated:", isAuthenticated);

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
      <span className="text-xs text-secondary-foreground">
        Checking session...
      </span>
    );
  } else if (isAuthenticated && user) {
    profileUI = (
      <div className="flex items-center gap-4">
        {/* Notifications (Dynamic based on role) */}
        <button className="p-2 text-secondary-foreground transition-colors hover:text-foreground relative">
          <IoIosNotificationsOutline size={24} />
        </button>

        {/* Profile Avatar */}
        <div ref={menuRef} className="relative">
          <button
            type="button"
            onClick={() => setIsOpen((prev) => !prev)}
            aria-expanded={isOpen}
            className={`w-9 h-9 rounded-full bg-secondary border border-border text-secondary-foreground flex items-center justify-center cursor-pointer ${isOpen && 'outline-1 outline-solid'}`}
          >
            <span className="text-sm font-semibold">
              {user.name.substring(0, 1)}
            </span>
          </button>
          {isOpen && (
            <div
              className="
              absolute
              right-0
              top-12
              z-10
              rounded-lg
              bg-background
              px-3
              py-2
              shadow-popover"
            >
              <ProfileCard />
            </div>
          )}
        </div>
      </div>
    );

    if (user.role === "ROLE_ADMIN") {
      adminCreateUI = (
        <button
          type="button"
          onClick={handleCreateNew}
          className="flex flex-row items-center gap-2 rounded-md px-3 py-2 text-secondary-foreground transition-colors hover:text-foreground"
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
          className="text-xs bg-primary hover:bg-primary/90 text-primary-foreground font-medium px-3 py-1.5 rounded transition"
        >
          Sign In
        </Link>
      </div>
    );
  }

  return (
    <header className="h-16 border-b-1 border-border bg-background flex items-center justify-between px-6 shrink-0 z-10">
      {/* Brand & Search */}
      <div className="flex items-center gap-8 w-1/2">
        <h1 className="text-xl font-bold tracking-widest text-primary">
          AVIATOR
        </h1>
        <div className="relative flex flex-row gap-4 items-centerw-full  text-sm rounded-full px-4 py-2  transition-colors">
          <input
            type="text"
            placeholder="Search articles globally..."
            className="focus:outline-none"
          />
          <IoSearchOutline className="text-secondary-foreground" size={23} />
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
