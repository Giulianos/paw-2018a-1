import api from 'api';

const ENDPOINT = userId => `users/${userId}/orders`;

export default {
  list: (userId, page, pageSize) => api.get(ENDPOINT(userId), { page, pageSize }),
  rate: (userId, orderId, rating, comment) => api.post(`${ENDPOINT(userId)}/${orderId}/reviews`, { rating, comment }),
  confirm: (userId, orderId) => api.post(`${ENDPOINT(userId)}/${orderId}/confirmation`)
};
