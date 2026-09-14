import { createAsyncThunk, createSlice, PayloadAction } from "@reduxjs/toolkit";
import { User } from "../definitions";
import { api } from "@/app/api/axiosIntance";


interface AuthState{
    user: Omit<User, 'passwordHash'> | null;
    accessToken: string | null;
    isAuthenticated: boolean;
    status: 'idle'|'loading'|'succeeded'|'failed';
    error: string|null;
}

const initialState: AuthState = {
    user: null,
    accessToken: null,
    isAuthenticated: false,
    status: 'idle',
    error: null,
}

interface AuthResponse {
    token?: string | null;
    name?: string;
    userName?: string;
    email?: string;
    role: User['role'];
}

const normalizeAuthPayload = (payload: AuthResponse) => {
    const user = {
        name: payload?.name ?? '',
        userName: payload?.userName ?? '',
        email: payload?.email ?? '',
        role: payload?.role,
    };

    return {
        user,
        accessToken: payload.token
    };
};

export const initialAuth = createAsyncThunk('auth/initialize', async () =>{
    const userDummy = {
        userName: 'super-admin',
        password: 'something'
    }
    const response = await api.post('/auth/login', userDummy);
    return normalizeAuthPayload(response.data);
})

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers:{
        setCredential: (state, action: PayloadAction<{user: Omit<User, 'passwordHash'>}>) => {
            console.log('auth setCredentail',action);
            state.user = action.payload.user;
            state.isAuthenticated = true;
            state.status = 'succeeded';
        },
        logoutSuccess: (state) => {
            state.user = null;
            state.isAuthenticated = false;
            state.status = 'failed';
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(initialAuth.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(initialAuth.fulfilled, (state, action) => {
                state.user = action.payload.user ?? null;
                state.accessToken = action.payload.accessToken ?? null;
                state.isAuthenticated = Boolean(action.payload.accessToken);
                state.status = 'succeeded';
                state.error = null;
            })
            .addCase(initialAuth.rejected, (state, action) => {
                state.user = null;
                state.accessToken = null;
                state.isAuthenticated = false;
                state.status = 'failed';
                state.error = action.error.message || 'Session Expired';
            })
    }
});

export const { setCredential, logoutSuccess } = authSlice.actions;
export default authSlice.reducer;
