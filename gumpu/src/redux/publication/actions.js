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

  LIST_ORDERS: `${prefix}/list_orders`,
  LIST_ORDERS_OK: `${prefix}/list_orders_ok`,
  LIST_ORDERS_FAIL: `${prefix}/list_orders_fail`,
  LIST_ORDERS_RESET: `${prefix}/list_orders_reset`,

  SEARCH: `${prefix}/search`,
  SEARCH_OK: `${prefix}/search_ok`,
  SEARCH_FAIL: `${prefix}/search_fail`,
  SEARCH_RESET: `${prefix}/search_reset`,

  RETRIEVE: `${prefix}/retrieve`,
  RETRIEVE_OK: `${prefix}/retrieve_ok`,
  RETRIEVE_FAIL: `${prefix}/retrieve_fail`,
  RETRIEVE_RESET: `${prefix}/retrieve_reset`,

  DELETE: `${prefix}/delete`,
  DELETE_OK: `${prefix}/delete_ok`,
  DELETE_FAIL: `${prefix}/delete_fail`,
  DELETE_RESET: `${prefix}/delete_reset`,

  ADOPT: `${prefix}/adopt`,
  ADOPT_OK: `${prefix}/adopt_ok`,
  ADOPT_FAIL: `${prefix}/adopt_fail`,
  ADOPT_RESET: `${prefix}/adopt_reset`,

  MARK_PURCHASED: `${prefix}/mark_purchased`,
  MARK_PURCHASED_OK: `${prefix}/mark_purchased_ok`,
  MARK_PURCHASED_FAIL: `${prefix}/mark_purchased_fail`,
  MARK_PURCHASED_RESET: `${prefix}/mark_purchased_reset`,
};
