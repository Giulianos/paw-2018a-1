import api from 'api';

const ENDPOINT = id => `/images/${id}`;

export default {
  retrieve: id => api.get(ENDPOINT(id))
};
