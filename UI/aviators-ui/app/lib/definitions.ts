export type User = {
  id?: string;
  name: string;
  userName: string;
  email: string;
  passwordHash?: string | null;
  role?: "ROLE_ADMIN" | "ROLE_AUDIENCE";
};

export type Category = {
  id?: string;
  name: string;
  description: string;
  isSubscribed: boolean;
};

export type Article = {
  id: string;
  title: string;
  slug: string;
  summary: string;
  content: string;
  status: "Open" | "Working" | "Published";
  categoryId: string;
  authorId: string;
  bannerUrl: string;
  youtubeLink: string;
  pdfUrl: string;
  publishedAt: Date;
  createdAt: Date;
  updatedAt: Date;
};

export interface AuthResponse {
  token: string;
  name: string;
  userName: string;
  email: string;
  role: User["role"];
}

export interface RegisterAdminRequest {
  name: string;
  userName: string;
  email: string;
  passwordHash: string;
  secretKey: string;
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
