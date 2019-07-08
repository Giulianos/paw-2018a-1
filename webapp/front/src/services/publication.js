import api from 'api';

const ENDPOINT = '/publications';

export default {
  create: newPublication => api.post(ENDPOINT, newPublication),
  retrieve: id => api.get(`${ENDPOINT}/${id}`),
  markPurchased: id => api.patch(`${ENDPOINT}/${id}`),
  leave: id => api.delete(`${ENDPOINT}/${id}`),
  orders: {
    create: (id, quantity) => api.post(`${ENDPOINT}/${id}/orders`, { quantity })
  }
};
