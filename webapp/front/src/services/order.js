import api from 'api';

const ENDPOINT = userId => `users/${userId}/orders`;

export default {
  list: (userId, page, pageSize) => api.get(ENDPOINT(userId), { page, pageSize })
};
