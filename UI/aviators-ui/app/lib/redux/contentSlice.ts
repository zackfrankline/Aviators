import { createSlice, createAsyncThunk , PayloadAction } from "@reduxjs/toolkit";
import { Article, Category } from "../definitions";
import axios from "axios";

interface DataSlice {
    articles: Article[];
    categories: Category[];
    status: 'idle' | 'loading' | 'succeeded' | 'failed';
    error: string | null;
}

const initialData: DataSlice = {
    articles: [],
    categories: [],
    status: 'idle',
    error: null
}

export const fetchInitialData = createAsyncThunk('data/fetchInitial', async () => {
    const [articleResponse, categoriesResponse] = await Promise.all([
        axios.get('/content/articles'),
        axios.get('/content/categories')
    ])
    console.log(articleResponse, categoriesResponse);
    return {
        articles: articleResponse.data,
        categories: categoriesResponse.data,
    }
})

const dataSlice = createSlice({
    name:'data',
    initialState: initialData,
    reducers:{
        setPublishedArticles: (state, action: PayloadAction<Article[]>) => {
            state.articles = action.payload;
        },
        setCategories: (state, action: PayloadAction<Category[]>) => {
            state.categories = action.payload;
        }
    }
})  

export const { setPublishedArticles, setCategories} = dataSlice.actions;
export default dataSlice.reducer;

