"use client";
import { useState } from "react";
import { useWorkspace } from "@/context/WorkspaceContext";

export default function Sidebar({ role }: { role: string }) {
  const [isCollapsed, setIsCollapsed] = useState(false);
  const { openTab } = useWorkspace();

  const handleOpenList = (title: string, type: string, value: string) => {
    openTab({
      id: `list-${value}`,
      title: title,
      type: "LIST",
      payload: { filterType: type, filterValue: value }
    });
  };

  const NavItem = ({ icon, label, onClick }: { icon: string, label: string, onClick: () => void }) => (
    <button 
      onClick={onClick}
      className="w-full flex items-center text-left p-2.5 text-sm text-slate-500 hover:text-slate-700  rounded-lg transition-colors"
    >
      <span className="text-lg mr-3">{icon}</span>
      {!isCollapsed && <span className="font-medium truncate">{label}</span>}
    </button>
  );

  return (
    <aside className={`transition-all duration-300 ease-in-out border-r border-slate-800 bg-slate-5 flex flex-col ${isCollapsed ? "w-16" : "w-64"}`}>
      <div className="p-4 border-b border-slate-800 flex justify-between items-center shrink-0">
        {!isCollapsed && <span className="text-xs font-bold tracking-widest text-slate-500 uppercase">Navigation</span>}
        <button onClick={() => setIsCollapsed(!isCollapsed)} className="p-1 hover:bg-slate-800 rounded text-slate-400">
          {isCollapsed ? "▶" : "◀"}
        </button>
      </div>

      <nav className="flex-1 overflow-y-auto p-3 space-y-6 scrollbar-hide">
        <div>
          <NavItem icon="🏠" label="Home Dashboard" onClick={() => {}} />
        </div>

        {/* Admin Article Statuses */}
        {role === "ADMIN" && (
          <div>
            {!isCollapsed && <h3 className="text-[10px] uppercase tracking-wider text-slate-500 font-bold mb-2 px-2">Articles</h3>}
            <div className="space-y-1">
              <NavItem icon="📝" label="Drafts" onClick={() => handleOpenList("Drafts", "STATUS", "DRAFT")} />
              <NavItem icon="⏳" label="In Progress" onClick={() => handleOpenList("In Progress", "STATUS", "PROGRESS")} />
              <NavItem icon="✅" label="Published" onClick={() => handleOpenList("Published", "STATUS", "PUBLISHED")} />
            </div>
          </div>
        )}

        {/* Categories Section */}
        <div>
          {!isCollapsed && <h3 className="text-[10px] uppercase tracking-wider text-slate-500 font-bold mb-2 px-2">Categories</h3>}
          <div className="space-y-1">
            <NavItem icon="🚀" label="Hypersonic" onClick={() => handleOpenList("Hypersonic", "CATEGORY", "hypersonic")} />
            <NavItem icon="✈️" label="Supersonic" onClick={() => handleOpenList("Supersonic", "CATEGORY", "supersonic")} />
          </div>
        </div>
      </nav>
    </aside>
  );
}