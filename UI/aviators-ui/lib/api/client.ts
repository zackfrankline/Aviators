// app/api/axiosInstance.ts
import axios from "axios";
import { AppStore } from "@/lib/store";

// Base API pointing to your API Gateway
const API_BASE_URL =
  process.env.NEXT_PUBLIC_API_BASE_URL || "http://localhost:8080";

export const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true, // Crucial: Sends and receives HttpOnly cookies
});

// Helper store injection to avoid circular dependency
let store: AppStore;
export const injectStore = (_store: AppStore) => {
  store = _store;
};

// Request Interceptor: Attach in-memory Access Token
api.interceptors.request.use(
  (config) => {
    const token = store?.getState()?.auth?.accessToken;
    console.log("Axios Interceptor Requests token:", token);
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error),
);

// Response Interceptor: Catch 401 and execute Silent Refresh
let isRefreshing = false;
let failedQueue: Array<{
  resolve: (value?: unknown) => void;
  reject: (reason?: unknown) => void;
}> = [];

const processQueue = (error: unknown, token: string | null = null) => {
  failedQueue.forEach((prom) => {
    if (error) {
      prom.reject(error);
    } else {
      prom.resolve(token);
    }
  });
  failedQueue = [];
};

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // If error is 401 and we haven't already retried this request
    //Handle Authentication error invalid authentication credentials
    if (error.response?.status === 401 && !originalRequest._retry) {
      if (
        originalRequest.url?.includes("/auth/refresh") ||
        originalRequest.url?.includes("/auth/login")
      ) {
        throw error;
      }

      originalRequest._retry = true;

      if (isRefreshing) {
        // Queue parallel requests while refresh is in-flight
        return new Promise((resolve, reject) => {
          failedQueue.push({ resolve, reject });
        })
          .then((token) => {
            originalRequest.headers.Authorization = `Bearer ${token}`;
            return api(originalRequest);
          })
          .catch((err) => {
            throw err;
          });
      }

      isRefreshing = true;

      try {
        // Backend reads the HttpOnly cookie and returns { token, user }
        const { data } = await axios.post(
          `${API_BASE_URL}/api/auth/refresh-token`,
          {},
          { withCredentials: true },
        );

        const newAccessToken = data.token;

        // Dynamically update Redux state
        const { setAccessToken } = await import("@/features/auth");
        store.dispatch(setAccessToken(newAccessToken));

        processQueue(null, newAccessToken);

        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
        return api(originalRequest);
      } catch (refreshError) {
        processQueue(refreshError, null);
        const { logoutSuccess } = await import("@/features/auth");
        store.dispatch(logoutSuccess());
        throw refreshError;
      } finally {
        isRefreshing = false;
      }
    }

    throw error;
  },
);
