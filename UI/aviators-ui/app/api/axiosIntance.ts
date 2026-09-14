import axios from "axios";

// 1. Create a reusable instance with your base URL
export const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

// 2. Inject your Redux store into this file dynamically to avoid circular dependencies
let store: any;

export const injectStore = (_store: any) => {
  store = _store;
};

// 3. Add a request interceptor to attach the JWT dynamically
api.interceptors.request.use(
  (config) => {
    if (store) {
      // Access the auth state directly from the Redux store
      const token = store.getState().auth.accessToken;
      
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
