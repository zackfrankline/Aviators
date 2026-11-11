import { api } from "@/lib/api/client";
import { Article, Category } from "@/features/content";

export const contentApi = {
  loadInitialContent: async (): Promise<{
    articles: Article[];
    categories: Category[];
  }> => {
    const [articlesRes, categoriesRes] = await Promise.all([
      api.get<Article[]>("/api/content/articles"),
      api.get<Category[]>("/api/content/categories"),
    ]);
    return {
      articles: articlesRes.data,
      categories: categoriesRes.data,
    };
  },

  loadArticleByCategory: async (categoryId: string | null) : Promise<{articles: Article[], categoryId: string | null}> =>{
    const url = categoryId
        ? `/api/content/articles/public?categoryId=${categoryId}`
        : "/api/content/articles/public";
      const response = await api.get<Article[]>(url);
      return { articles: response.data, categoryId };
  }
};
