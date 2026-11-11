export type User = {
  id?: string;
  name: string;
  userName: string;
  email: string;
  passwordHash?: string | null;
  role?: "ROLE_ADMIN" | "ROLE_AUDIENCE";
};

export interface LoginRequestDTO {
  username: string;
  password: string;
}

export interface AuthError {
  error: string;
  message: string;
  status: string;
  timestamp: string;
  fieldErrors?: Record<string, string>;
}

export enum Role {
  ROLE_ADMIN,
  ROLE_AUDIENCE,
}

export interface AdminRegisterRequestDTO {
  name: string;
  userName: string;
  email: string;
  passwordHash: string;
  secretKey: string;
}

export interface AuthResponse {
  token: string;
  name: string;
  userName: string;
  email: string;
  role: User["role"];
}

export interface AdminRegisterRequest extends AdminRegisterRequestDTO {
  name: string;
  userName: string;
  email: string;
  passwordHash: string;
  confirmPasswordHash?: string;
  secretKey: string;
}
