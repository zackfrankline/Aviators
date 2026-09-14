"use client";

import React from "react";
import { Provider } from "react-redux";
import { store } from "./store";
import { WorkspaceProvider } from "@/context/WorkspaceContext";

export default function ClientProviders({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <Provider store={store}>
      <WorkspaceProvider>{children}</WorkspaceProvider>
    </Provider>
  );
}
