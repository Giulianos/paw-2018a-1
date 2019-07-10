import api from 'api';

const ENDPOINT = '/users';

export default {
  create: newUser => api.post(ENDPOINT, newUser),
  retrieve: () => api.get(ENDPOINT),
  rating: userId => api.get(`${ENDPOINT}/${userId}/ratings`),
  publications: {
    list: (userId, page, pageSize) => api.get(`${ENDPOINT}/${userId}/publications`, { pageSize, page })
  }
};
