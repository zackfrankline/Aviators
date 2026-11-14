// app/lib/redux/authSlice.ts
import { createAsyncThunk, createSlice, PayloadAction } from "@reduxjs/toolkit";
import axios, { AxiosError } from "axios";
import { AdminRegisterRequestDTO, authApi, AuthError, LoginRequestDTO, User } from "@/features/auth";

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

function handleError(err: unknown, fallbackMessage: string) {
  if (axios.isAxiosError(err)) {
    const error = err as AxiosError<AuthError>;
    if (error.response?.data) return error.response?.data;
  }
  return {
    error: "Authentication Error",
    message: fallbackMessage,
    status: "FAILED",
    timestamp: Date.now().toString(),
  };
}


// 1. Silent Refresh on App Mount (Replaces hardcoded initialAuth)
export const refreshSession = createAsyncThunk(
  "auth/refreshSession",
  async (_, { rejectWithValue }) => {
    try {
      return await authApi.refreshSession();
    } catch (err: unknown) {
      return rejectWithValue(
        handleError(err, "Login failed. Please try again."),
      );
    }
  },
);

// 2. Explicit User Login
export const loginUser = createAsyncThunk(
  "auth/login",
  async (credentials: LoginRequestDTO, { rejectWithValue }) => {
    try {
      return await authApi.login(credentials);
    } catch (err: unknown) {
      return rejectWithValue(
        handleError(err, "Login failed. Please try again."),
      );
    }
  },
);

export const registerAdminUser = createAsyncThunk(
  "/api/auth/register-admin",
  async (credentials: AdminRegisterRequestDTO, { rejectWithValue }) => {
    try {
      return await authApi.registerAdmin(credentials);
    } catch (err: unknown) {
      return rejectWithValue(
        handleError(err, "Registration failed. Please try again."),
      );
    }
  },
);

// 3. User Logout
export const logoutUser = createAsyncThunk("auth/logout", async () => {
  await authApi.logout();
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
