import { configureStore, Store } from '@reduxjs/toolkit';
import authReducer from '../../features/auth/store/authSlice';
import contentReducer from '../../features/content/store/contentSlice';
import { injectStore } from '@/lib/api/client';

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
