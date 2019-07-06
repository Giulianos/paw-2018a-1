import api from 'api';

const ENDPOINT = userId => `users/${userId}/orders`;

export default {
  list: userId => api.post(ENDPOINT(userId))
};
