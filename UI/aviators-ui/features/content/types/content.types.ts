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
  bannerUrl: string | null;
  youtubeLink: string | null;
  pdfUrl: string | null;
  publishedAt: Date | null;
  createdAt: Date;
  updatedAt: Date;
};