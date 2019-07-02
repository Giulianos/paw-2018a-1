import { create } from 'apisauce';
import localStorageService from 'services/localStorage';

const api = create({
  baseURL: '/api'
})

// Add token from localStorage (if any)
api.addRequestTransform(req => {
  if(localStorageService.getToken() || false) {
    req.headers['Authorization'] = localStorageService.getToken();
  }
})

export default api;
