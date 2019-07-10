import api from 'api';

const ENDPOINT = '/publications';

export default {
  create: newPublication => api.post(ENDPOINT, newPublication),
  delete: publicationId => api.delete(`${ENDPOINT}/${publicationId}`),
  retrieve: id => api.get(`${ENDPOINT}/${id}`),
  markPurchased: id => api.patch(`${ENDPOINT}/${id}`),
  leave: id => api.delete(`${ENDPOINT}/${id}`),
  getLatest: pageSize => api.get(`${ENDPOINT}/latest`, { pageSize }),
  search: (term, page, pageSize) => api.get(`${ENDPOINT}/searches/${term}`, { page, pageSize }),
  adopt: publicationId => api.post(`${ENDPOINT}/${publicationId}/supervisor`),
  orders: {
    create: (id, quantity) => api.post(`${ENDPOINT}/${id}/orders`, { quantity }),
    list: (id, page, pageSize) => api.get(`${ENDPOINT}/${id}/orders`, { page, pageSize }),
  }
};
