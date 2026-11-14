import { api } from "@/lib/api/client";
import type {
  AdminRegisterRequestDTO,
  AuthResponse,
  LoginRequestDTO,
} from "@/features/auth";

export const authApi = {
  login: async (credentials: LoginRequestDTO): Promise<AuthResponse> => {
    const response = await api.post<AuthResponse>(
      "/api/auth/login",
      credentials,
    );
    return response.data;
  },

  refreshSession: async (): Promise<AuthResponse> => {
    const response = await api.post<AuthResponse>("/api/auth/refresh-token");
    return response.data;
  },

  registerAdmin: async (
    credentials: AdminRegisterRequestDTO,
  ): Promise<AuthResponse> => {
    const response = await api.post<AuthResponse>(
      "/api/auth/register-admin",
      credentials,
    );
    return response.data;
  },

  logout: async (): Promise<void> => {
    await api.post("/api/auth/logout");
  },
};
