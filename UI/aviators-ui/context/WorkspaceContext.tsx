"use client";

import React, { createContext, useContext, useState, useCallback } from "react";

// Define the structure of a Tab based on your wireframe
export type Tab = {
  id: string;
  title: string;
  type: "CREATE" | "EDIT" | "VIEW" | "LIST";
  payload?: any; // Holds specific article IDs or Category filters
};

type WorkspaceContextType = {
  tabs: Tab[];
  activeTabId: string | null;
  openTab: (tab: Tab) => void;
  closeTab: (id: string, e?: React.MouseEvent) => void;
  setActiveTabId: (id: string) => void;
};

const WorkspaceContext = createContext<WorkspaceContextType | null>(null);

export function WorkspaceProvider({ children }: { children: React.ReactNode }) {
  const [tabs, setTabs] = useState<Tab[]>([]);
  const [activeTabId, setActiveTabId] = useState<string | null>(null);

  const openTab = useCallback((newTab: Tab) => {
    setTabs((prev) => {
      // If tab already exists, just focus it
      if (prev.find((t) => t.id === newTab.id)) return prev;
      return [...prev, newTab];
    });
    setActiveTabId(newTab.id);
  }, []);

  const closeTab = useCallback((idToClose: string, e?: React.MouseEvent) => {
    e?.stopPropagation();
    setTabs((prev) => {
      const filtered = prev.filter((t) => t.id !== idToClose);
      // If we closed the active tab, switch focus to the last available tab
      if (activeTabId === idToClose) {
        setActiveTabId(filtered.length > 0 ? filtered[filtered.length - 1].id : null);
      }
      return filtered;
    });
  }, [activeTabId]);

  return (
    <WorkspaceContext.Provider value={{ tabs, activeTabId, openTab, closeTab, setActiveTabId }}>
      {children}
    </WorkspaceContext.Provider>
  );
}

// Custom hook for components to easily trigger tabs
export const useWorkspace = () => {
  const context = useContext(WorkspaceContext);
  if (!context) throw new Error("useWorkspace must be used within WorkspaceProvider");
  return context;
};