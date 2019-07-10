const prefix = '@@USER';

export default {
  CREATE: `${prefix}/create`,
  CREATE_OK: `${prefix}/create_ok`,
  CREATE_FAIL: `${prefix}/create_fail`,
  CREATE_RESET: `${prefix}/create_reset`,

  RETRIEVE: `${prefix}/retrieve`,
  RETRIEVE_OK: `${prefix}/retrieve_ok`,
  RETRIEVE_FAIL: `${prefix}/retrieve_fail`,
  RETRIEVE_RESET: `${prefix}/retrieve_reset`,

  RATING: `${prefix}/rating`,
  RATING_OK: `${prefix}/rating_ok`,
  RATING_FAIL: `${prefix}/rating_fail`,
  RATING_RESET: `${prefix}/rating_reset`,
};
