// app/lib/redux/authSlice.ts
import { createAsyncThunk, createSlice, PayloadAction } from "@reduxjs/toolkit";
import {
  AuthError,
  AuthResponse,
  RegisterAdminRequest,
  User,
} from "../definitions";
import { api } from "../../api/axiosIntance";
import axios, { AxiosError } from "axios";

interface AuthState {
  user: Omit<User, "passwordHash"> | null;
  accessToken: string | null;
  isAuthenticated: boolean;
  isHydrating: boolean; // True during app boot while checking silent refresh
  status: "idle" | "loading" | "succeeded" | "failed";
  error: string | null;
}

const initialState: AuthState = {
  user: null,
  accessToken: null,
  isAuthenticated: false,
  isHydrating: true,
  status: "idle",
  error: null,
};

// 1. Silent Refresh on App Mount (Replaces hardcoded initialAuth)
export const refreshSession = createAsyncThunk(
  "auth/refreshSession",
  async (_, { rejectWithValue }) => {
    try {
      const response = await api.post<AuthResponse>("/api/auth/refresh-token");
      return response.data;
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        const error = err as AxiosError<AuthError>;
        if (error.response?.data) return rejectWithValue(error.response?.data);
      }
      return rejectWithValue({
        error: "Login Error",
        message: "Login failed. Please try again.",
        status: "FAILED",
        timestamp: Date.now().toString(),
      });
    }
  },
);

// 2. Explicit User Login
export const loginUser = createAsyncThunk(
  "auth/login",
  async (
    credentials: { userName: string; password: string },
    { rejectWithValue },
  ) => {
    try {
      const response = await api.post<AuthResponse>(
        "/api/auth/login",
        credentials,
      );
      return response.data;
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        const error = err as AxiosError<AuthError>;
        if (error.response?.data) return rejectWithValue(error.response?.data);
      }
      return rejectWithValue({
        error: "Login Error",
        message: "Login failed. Please try again.",
        status: "FAILED",
        timestamp: Date.now().toString(),
      });
    }
  },
);

export const registerAdminUser = createAsyncThunk(
  "/api/auth/register-admin",
  async (credentials: RegisterAdminRequest, { rejectWithValue }) => {
    try {
      const response = await api.post<AuthResponse>(
        "/api/auth/register-admin",
        credentials,
      );
      return response.data;
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        const error = err as AxiosError<AuthError>;
        if (error.response?.data) return rejectWithValue(error.response?.data);
      }
      return rejectWithValue({
        error: "Regsitration Error",
        message: "Registration Failed",
        status: "FAILED",
        timestamp: Date.now().toString(),
      });
    }
  },
);

// 3. User Logout
export const logoutUser = createAsyncThunk("auth/logout", async () => {
  await api.post("/api/auth/logout");
});

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setAccessToken: (state, action: PayloadAction<string>) => {
      state.accessToken = action.payload;
    },
    logoutSuccess: (state) => {
      state.user = null;
      state.accessToken = null;
      state.isAuthenticated = false;
      state.isHydrating = false;
    },
  },
  extraReducers: (builder) => {
    builder
      // Refresh Session
      .addCase(refreshSession.pending, (state) => {
        state.isHydrating = true;
      })
      .addCase(refreshSession.fulfilled, (state, action) => {
        state.user = {
          name: action.payload.name,
          userName: action.payload.userName,
          email: action.payload.email,
          role: action.payload.role,
        };
        state.accessToken = action.payload.token;
        state.isAuthenticated = true;
        state.isHydrating = false;
      })
      .addCase(refreshSession.rejected, (state) => {
        state.user = null;
        state.accessToken = null;
        state.isAuthenticated = false;
        state.isHydrating = false;
      })

      // Login
      .addCase(loginUser.pending, (state) => {
        state.status = "loading";
        state.error = null;
      })
      .addCase(loginUser.fulfilled, (state, action) => {
        state.user = {
          name: action.payload.name,
          userName: action.payload.userName,
          email: action.payload.email,
          role: action.payload.role,
        };
        state.accessToken = action.payload.token;
        state.isAuthenticated = true;
        state.status = "succeeded";
      })
      .addCase(loginUser.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.payload as string;
      })
      //RegisterAdminUser
      .addCase(registerAdminUser.pending, (state) => {
        state.status = "loading";
        state.error = null;
      })
      .addCase(registerAdminUser.fulfilled, (state, action) => {
        state.user = {
          name: action.payload.name,
          userName: action.payload.userName,
          email: action.payload.email,
          role: action.payload.role,
        };
        state.status = "succeeded";
        state.accessToken = action.payload.token;
        state.isAuthenticated = true;
      })
      .addCase(registerAdminUser.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.payload as string;
      })
      // Logout
      .addCase(logoutUser.fulfilled, (state) => {
        state.user = null;
        state.accessToken = null;
        state.isAuthenticated = false;
      });
  },
});

export const { setAccessToken, logoutSuccess } = authSlice.actions;
export default authSlice.reducer;
