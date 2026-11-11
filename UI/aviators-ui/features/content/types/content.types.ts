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