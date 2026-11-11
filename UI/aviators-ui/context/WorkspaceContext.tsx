"use client";

import React, {
  createContext,
  useContext,
  useState,
  useCallback,
  useMemo,
} from "react";

// Define the structure of a Tab based on your wireframe
export type Tab = {
  id: string;
  title: string;
  type: "CREATE" | "EDIT" | "VIEW" | "LIST";
  subType: "ARTICLE" | "CATEGORY";
  payload?: unknown; // Holds specific article IDs or Category filters
};

export type NewTab = {
  id?: string;
  title: string;
  type: "CREATE" | "EDIT" | "VIEW" | "LIST";
  subType: "ARTICLE" | "CATEGORY";
  payload?: unknown;
};

export type WorkspaceContextType = {
  tabs: Tab[];
  activeTabId: string | null;
  openTab: (tab: NewTab) => void;
  closeTab: (id: string, e?: React.MouseEvent) => void;
  setActiveTabId: (id: string) => void;
};

const WorkspaceContext = createContext<WorkspaceContextType | null>(null);

let tabIdCounter = 0;

function createTabId() {
  if (typeof crypto !== "undefined") {
    if (typeof crypto.randomUUID === "function") return crypto.randomUUID();

    const values = crypto.getRandomValues(new Uint32Array(2));
    return `tab-${Date.now()}-${values[0].toString(36)}-${values[1].toString(36)}`;
  }

  tabIdCounter += 1;
  return `tab-${Date.now()}-${tabIdCounter}`;
}

export function WorkspaceProvider({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  const [tabs, setTabs] = useState<Tab[]>([]);
  const [activeTabId, setActiveTabId] = useState<string | null>(null);

  const openTab = useCallback((newTab: NewTab) => {
    let id = newTab.id;
    if (!id) {
      id = createTabId();
    }
    const tab: Tab = { ...newTab, id };

    setTabs((prev) => {
      // If tab already exists, just focus it
      if (prev.some((t) => t.id === tab.id)) return prev;
      return [...prev, tab];
    });
    setActiveTabId(id);
  }, []);

  const closeTab = useCallback(
    (idToClose: string, e?: React.MouseEvent) => {
      e?.stopPropagation();
      setTabs((prev) => {
        const filtered = prev.filter((t) => t.id !== idToClose);
        // If we closed the active tab, switch focus to the last available tab
        if (activeTabId === idToClose) {
          setActiveTabId(filtered.at(-1)?.id ?? null);
        }
        return filtered;
      });
    },
    [activeTabId],
  );

  const value = useMemo(
    () => ({ tabs, activeTabId, openTab, closeTab, setActiveTabId }),
    [tabs, activeTabId, openTab, closeTab],
  );

  return (
    <WorkspaceContext.Provider value={value}>
      {children}
    </WorkspaceContext.Provider>
  );
}

// Custom hook for components to easily trigger tabs
export const useWorkspace = () => {
  const context = useContext(WorkspaceContext);
  if (!context)
    throw new Error("useWorkspace must be used within WorkspaceProvider");
  return context;
};
