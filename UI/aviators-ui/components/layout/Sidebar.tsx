"use client";
import { ReactNode, useState } from "react";
import { useWorkspace } from "@/context/WorkspaceContext";
import { NavItem } from "./NavItem";
import { HiOutlineHome } from "react-icons/hi2";
import { Category, fetchArticlesByCategory } from "@/features/content";
import { useAppDispatch, useAppSelector } from "@/lib/store/hooks";

export default function Sidebar() {
  const dispatch = useAppDispatch();

  const { user, isAuthenticated } = useAppSelector((state) => state.auth);
  const { categories, selectedCategoryId } = useAppSelector(
    (state) => state.content,
  );

  let adminStatusCategory: ReactNode;

  const [isCollapsed, setIsCollapsed] = useState(false);
  const { openTab } = useWorkspace();

  const handleOpenList = (
    title: string,
    type: string,
    value: string,
    subType: "ARTICLE" | "CATEGORY",
  ) => {
    openTab({
      id: `list-${value}`,
      title: title,
      subType: subType,
      type: "LIST",
      payload: { filterType: type, filterValue: value },
    });
  };

  if (user?.role === "ROLE_ADMIN" && isAuthenticated) {
    adminStatusCategory = (
      <div>
        {!isCollapsed && (
          <h3 className="text-[10px] uppercase tracking-wider text-secondary-foreground font-bold mb-2 px-2">
            Articles
          </h3>
        )}
        <div className="space-y-1">
          <NavItem
            icon={<HiOutlineHome />}
            label="Drafts"
            selectedCategoryId={selectedCategoryId}
            onClick={() =>
              handleOpenList("Drafts", "STATUS", "DRAFT", "CATEGORY")
            }
            isCollapsed={isCollapsed}
          />
          <NavItem
            icon="⏳"
            label="In Progress"
            selectedCategoryId={selectedCategoryId}
            onClick={() =>
              handleOpenList("In Progress", "STATUS", "PROGRESS", "CATEGORY")
            }
            isCollapsed={isCollapsed}
          />
          <NavItem
            icon="✅"
            label="Published"
            selectedCategoryId={selectedCategoryId}
            onClick={() =>
              handleOpenList("Published", "STATUS", "PUBLISHED", "CATEGORY")
            }
            isCollapsed={isCollapsed}
          />
        </div>
      </div>
    );
  }

  return (
    <aside
      className={`transition-all duration-300 ease-in-out border-r border-border bg-background flex flex-col shadow-popover ${isCollapsed ? "w-16" : "w-64"}`}
    >
      <div className="p-4 border-b border-border flex justify-between items-center shrink-0">
        {!isCollapsed && (
          <span className="text-xs font-bold tracking-widest text-secondary-foreground uppercase">
            Navigation
          </span>
        )}
        <button
          onClick={() => setIsCollapsed(!isCollapsed)}
          className="p-1 hover:bg-secondary rounded text-secondary-foreground"
        >
          {isCollapsed ? "▶" : "◀"}
        </button>
      </div>

      <nav className="flex-1 overflow-y-auto p-3 space-y-6 scrollbar-hide">
        <div>
          <NavItem
            icon={<HiOutlineHome size={24}/>}
            label="Home Dashboard"
            selectedCategoryId={null}
            onClick={() => {}}
            isCollapsed={isCollapsed}
          />
        </div>

        {/* Admin Article Statuses */}
        {adminStatusCategory}

        {/* Categories Section */}
        <div>
          {!isCollapsed && (
            <h3 className="text-[10px] uppercase tracking-wider text-secondary-foreground font-bold mb-2 px-2">
              Categories
            </h3>
          )}
          <div className="space-y-1">
            {categories.map((category: Category) => (
              <NavItem
                key={category.id}
                icon="🚀"
                label="Hypersonic"
                selectedCategoryId={selectedCategoryId}
                onClick={async () => {
                  if (category.id)
                    await dispatch(fetchArticlesByCategory(category.id));
                }}
                isCollapsed={isCollapsed}
              />
            ))}
            {/* <NavItem
              icon="🚀"
              label="Hypersonic"
              onClick={() => dispatch(fetchArticlesByCategory(null))
              }
              isCollapsed={isCollapsed}
            />
            <NavItem
              icon="✈️"
              label="Supersonic"
              onClick={() =>
                handleOpenList("Supersonic", "CATEGORY", "supersonic")
              }
              isCollapsed={isCollapsed}
            /> */}
          </div>
        </div>
      </nav>
    </aside>
  );
}
