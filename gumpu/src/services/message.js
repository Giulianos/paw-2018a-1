import api from 'api';

export default {
  order: {
    list: (userId, orderId) => api.get(`/users/${userId}/orders/${orderId}/messages`),
    send: (userId, orderId, message) => api.post(`/users/${userId}/orders/${orderId}/messages`, { message }),
    unseen: {
      list: (userId, orderId) => api.get(`/users/${userId}/orders/${orderId}/messages/unseen`)
    }
  },
  publication: {
    list: (publicationId, ordererId) => api.get(`publications/${publicationId}/orders/${ordererId}/messages`),
    send: (publicationId, ordererId, message) => api.post(`publications/${publicationId}/orders/${ordererId}/messages`, { message }),
    unseen: {
      list: (publicationId, ordererId) => api.get(`publications/${publicationId}/orders/${ordererId}/messages/unseen`)
    }
  }
};
