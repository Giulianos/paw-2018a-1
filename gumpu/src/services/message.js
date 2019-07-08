import api from 'api';

export default {
  order: {
    list: (userId, orderId) => api.get(`/users/${userId}/orders/${orderId}/messages`),
    unseen: {
      list: (userId, orderId) => api.get(`/users/${userId}/orders/${orderId}/messages/unseen`)
    }
  },
  publication: {
    list: (publicationId, ordererId) => api.get(`publications/${publicationId}/orders/${ordererId}/messages`),
    unseen: {
      list: (publicationId, ordererId) => api.get(`publications/${publicationId}/orders/${ordererId}/messages/unseen`)
    }
  }
};
