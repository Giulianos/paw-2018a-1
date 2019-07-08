const prefix = '@@PUBLICATION';

export default {
  CREATE: `${prefix}/create`,
  CREATE_OK: `${prefix}/create_ok`,
  CREATE_FAIL: `${prefix}/create_fail`,
  CREATE_RESET: `${prefix}/create_reset`,
  
  ORDER: `${prefix}/order`,
  ORDER_OK: `${prefix}/order_ok`,
  ORDER_FAIL: `${prefix}/order_fail`,
  ORDER_RESET: `${prefix}/order_reset`,

  LIST_USER: `${prefix}/list_user`,
  LIST_USER_OK: `${prefix}/list_user_ok`,
  LIST_USER_FAIL: `${prefix}/list_user_fail`,
  LIST_USER_RESET: `${prefix}/list_user_reset`,

  LATEST: `${prefix}/latest`,
  LATEST_OK: `${prefix}/latest_ok`,
  LATEST_FAIL: `${prefix}/latest_fail`,
  LATEST_RESET: `${prefix}/latest_reset`,
};
