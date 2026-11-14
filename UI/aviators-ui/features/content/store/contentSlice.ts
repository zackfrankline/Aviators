// app/lib/redux/contentSlice.ts
import { createSlice, createAsyncThunk, PayloadAction } from "@reduxjs/toolkit";
import { Article, Category, contentApi } from "@/features/content";
import { AxiosError } from "axios";

interface ContentState {
  articles: Article[];
  categories: Category[];
  selectedCategoryId: string | null;
  status: "idle" | "loading" | "succeeded" | "failed";
  articlesLoading: boolean;
  error: string | null;
}

const initialState: ContentState = {
  articles: [],
  categories: [],
  selectedCategoryId: null,
  status: "idle",
  articlesLoading: false,
  error: null,
};

// 1. Initial Page Load: Preload published articles & all categories
export const fetchInitialData = createAsyncThunk(
  "content/fetchInitialData",
  async (_, { rejectWithValue }) => {
    try {
      return await contentApi.loadInitialContent();
    } catch (err: unknown) {
      return rejectWithValue((err as AxiosError).response?.data|| "Failed to load feed");
    }
  }
);

// 2. Fetch articles by selected category
export const fetchArticlesByCategory = createAsyncThunk(
  "content/fetchArticlesByCategory",
  async (categoryId: string | null, { rejectWithValue }) => {
    try {
      return await contentApi.loadArticleByCategory(categoryId);
    } catch (err: unknown) {
      return rejectWithValue((err as AxiosError).response?.data || "Failed to load category articles");
    }
  }
);

const contentSlice = createSlice({
  name: "content",
  initialState,
  reducers: {
    setSelectedCategory: (state, action: PayloadAction<string | null>) => {
      state.selectedCategoryId = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      // Initial Load
      .addCase(fetchInitialData.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchInitialData.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.articles = action.payload.articles;
        state.categories = action.payload.categories;
        state.error = null;
      })
      .addCase(fetchInitialData.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.payload as string;
      })

      // Fetch By Category
      .addCase(fetchArticlesByCategory.pending, (state) => {
        state.articlesLoading = true;
      })
      .addCase(fetchArticlesByCategory.fulfilled, (state, action) => {
        state.articlesLoading = false;
        state.articles = action.payload.articles;
        state.selectedCategoryId = action.payload.categoryId;
      })
      .addCase(fetchArticlesByCategory.rejected, (state, action) => {
        state.articlesLoading = false;
        state.error = action.payload as string;
      });
  },
});

export const { setSelectedCategory } = contentSlice.actions;
export default contentSlice.reducer;