"use client";

import { useEffect } from "react";
import Workspace from "@/components/Workspace";
import { RootState } from "@/app/lib/redux/store";
import { useDispatch, useSelector } from "react-redux";

import { initialAuth } from "@/app/lib/redux/authSlice";

//auth data (jwt-service call : http://localhost:8080)
// const data =

export default function Home() {
  const dispatch = useDispatch();

  const { isAuthenticated, user, accessToken } = useSelector(
    (state: RootState) => state.auth,
  );

  useEffect(() => {
    dispatch(initialAuth() as any).then((response: any) => {
      if (response.payload) {
        console.log("Auth payload:", response.payload);
      }
    });
  }, [dispatch]);

  if (isAuthenticated) {
    return user?.role === 'ROLE_AUDIENCE'? 
            <h2>Log Out</h2>:
            <Workspace />;
  }

  return <Workspace />;
}
