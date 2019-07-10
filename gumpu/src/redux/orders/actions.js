const prefix = '@@ORDERS';

export default {
  LIST: `${prefix}/list`,
  LIST_OK: `${prefix}/list_ok`,
  LIST_FAIL: `${prefix}/list_fail`,
  LIST_RESET: `${prefix}/list_reset`,

  RATE: `${prefix}/rate`,
  RATE_OK: `${prefix}/rate_ok`,
  RATE_FAIL: `${prefix}/rate_fail`,
  RATE_RESET: `${prefix}/rate_reset`,

  CONFIRM: `${prefix}/confirm`,
  CONFIRM_OK: `${prefix}/confirm_ok`,
  CONFIRM_FAIL: `${prefix}/confirm_fail`,
  CONFIRM_RESET: `${prefix}/confirm_reset`
};
