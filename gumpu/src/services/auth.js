import api from 'api';

const ENDPOINT = '/login';

export default {
  login: credentials => api.post(ENDPOINT, credentials),
};
