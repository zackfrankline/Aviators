import { configureStore, Store } from '@reduxjs/toolkit';
import authReducer from './authSlice';
import contentReducer from './contentSlice';
import { injectStore } from '@/app/api/axiosIntance';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    content: contentReducer,
  },
});

injectStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

// Custom AppStore type definition
export type AppStore = Store<RootState> & {
  dispatch: AppDispatch;
};
