import { createSlice } from "@reduxjs/toolkit";
import { Article, Category } from "../definitions";
import { createAsyncThunk } from "@reduxjs/toolkit";
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
        axios.get('http://localhost:8080/api/content/articles'),
        axios.get('http://localhost:8080/api/content/categories')
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
        
    }
})  

