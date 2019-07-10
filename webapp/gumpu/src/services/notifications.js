import api from 'api';

const ENDPOINT = id => `/users/${id}/notifications`;

export default {
  retrieve: userId => api.get(ENDPOINT(userId)),
  clear: userId => api.delete(ENDPOINT(userId)),
};
