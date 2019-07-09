import { create } from 'apisauce';

import { store } from 'redux/store';
import localStorageService from 'services/localStorage';
import { resetRetrieveUser } from 'redux/user/actionCreators';
import { logout } from 'redux/auth/actionCreators';

const api = create({
  baseURL: '/api',
});

// Add token from localStorage (if any)
api.addRequestTransform((req) => {
  if (localStorageService.getToken() || false) {
    req.headers.Authorization = localStorageService.getToken();
  }
});

/** Add unauthorized api call monitor */
api.addMonitor((res) => {
  if (res.status === 403 || res.status === 401) {
    store.dispatch(logout());
    /** Remove logged user (if any) */
    store.dispatch(resetRetrieveUser());
  }
});

export default api;
