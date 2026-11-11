"use client";

import React from "react";
import { Provider } from "react-redux";
import { store } from "../../lib/store";
import { WorkspaceProvider } from "@/context/WorkspaceContext";

export default function StoreProvider({
  children,
}: {
  readonly children: React.ReactNode;
}) {
  return (
    <Provider store={store}>
      <WorkspaceProvider>{children}</WorkspaceProvider>
    </Provider>
  );
}
