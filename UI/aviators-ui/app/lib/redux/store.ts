import { configureStore } from '@reduxjs/toolkit';
import authReducer from './authSlice';
import { injectStore } from '@/app/api/axiosIntance';

export const store = configureStore({
  reducer: {
    auth: authReducer,
  },
});

injectStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
