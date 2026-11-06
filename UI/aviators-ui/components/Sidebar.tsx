"use client";
import { ReactNode, useState } from "react";
import { useWorkspace } from "@/context/WorkspaceContext";
import { NavItem } from "./NavItem";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/app/lib/redux/store";
import { fetchArticlesByCategory } from "@/app/lib/redux/contentSlice";
import { Category } from "@/app/lib/definitions";

export default function Sidebar() {
  const dispatch = useDispatch<AppDispatch>();

  const { user, isAuthenticated } = useSelector(
    (state: RootState) => state.auth,
  );
  const { categories, selectedCategoryId } = useSelector(
    (state: RootState) => state.content,
  );

  let adminStatusCategory: ReactNode;

  const [isCollapsed, setIsCollapsed] = useState(false);
  const { openTab } = useWorkspace();

  const handleOpenList = (title: string, type: string, value: string) => {
    openTab({
      id: `list-${value}`,
      title: title,
      type: "LIST",
      payload: { filterType: type, filterValue: value },
    });
  };

  if (user?.role === "ROLE_ADMIN" && isAuthenticated) {
    adminStatusCategory = (
      <div>
        {!isCollapsed && (
          <h3 className="text-[10px] uppercase tracking-wider text-slate-500 font-bold mb-2 px-2">
            Articles
          </h3>
        )}
        <div className="space-y-1">
          <NavItem
            icon="📝"
            label="Drafts"
            selectedCategoryId={selectedCategoryId}
            onClick={() => handleOpenList("Drafts", "STATUS", "DRAFT")}
            isCollapsed={isCollapsed}
          />
          <NavItem
            icon="⏳"
            label="In Progress"
            selectedCategoryId={selectedCategoryId}
            onClick={() => handleOpenList("In Progress", "STATUS", "PROGRESS")}
            isCollapsed={isCollapsed}
          />
          <NavItem
            icon="✅"
            label="Published"
            selectedCategoryId={selectedCategoryId}
            onClick={() => handleOpenList("Published", "STATUS", "PUBLISHED")}
            isCollapsed={isCollapsed}
          />
        </div>
      </div>
    );
  }

  return (
    <aside
      className={`transition-all duration-300 ease-in-out border-r border-slate-800 bg-slate-5 flex flex-col ${isCollapsed ? "w-16" : "w-64"}`}
    >
      <div className="p-4 border-b border-slate-800 flex justify-between items-center shrink-0">
        {!isCollapsed && (
          <span className="text-xs font-bold tracking-widest text-slate-500 uppercase">
            Navigation
          </span>
        )}
        <button
          onClick={() => setIsCollapsed(!isCollapsed)}
          className="p-1 hover:bg-slate-800 rounded text-slate-400"
        >
          {isCollapsed ? "▶" : "◀"}
        </button>
      </div>

      <nav className="flex-1 overflow-y-auto p-3 space-y-6 scrollbar-hide">
        <div>
          <NavItem
            icon="🏠"
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
            <h3 className="text-[10px] uppercase tracking-wider text-slate-500 font-bold mb-2 px-2">
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
                onClick={() => dispatch(fetchArticlesByCategory(category.id))}
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
