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
};
