import api from 'api';

const ENDPOINT = '/users';

export default {
  create: newUser => api.post(ENDPOINT, newUser)
}