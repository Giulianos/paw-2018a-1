import orderService from 'services/order';
import actions from './actions';

export const listOrders = userId => async (dispatch) => {
  dispatch({ type: actions.LIST });

  try {
    const response = await orderService.list(userId, 0, 10);
    if (response.ok) {
      dispatch({ type: actions.LIST_OK, payload: response.data });
    } else {
      dispatch({ type: actions.LIST_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.LIST_FAIL });
  }
};

export const resetListOrders = () => ({ type: actions.LIST_RESET });

export const rate = (userId, orderId, comment, rating) => async (dispatch) => {
  dispatch({ type: actions.RATE });

  try {
    const response = await orderService.rate(userId, orderId, rating, comment);
    if (response.ok) {
      dispatch({ type: actions.RATE_OK, payload: response.data });
    } else {
      dispatch({ type: actions.RATE_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.RATE_FAIL });
  }
};

export const resetRate = () => ({ type: actions.RATE_RESET });

export const confirm = (userId, orderId) => async (dispatch) => {
  dispatch({ type: actions.CONFIRM });

  try {
    const response = await orderService.confirm(userId, orderId);
    if (response.ok) {
      dispatch({ type: actions.CONFIRM_OK, payload: response.data });
    } else {
      dispatch({ type: actions.CONFIRM_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.CONFIRM_FAIL });
  }
};

export const resetConfirm = () => ({ type: actions.CONFIRM_RESET });

export const deleteOrder = (userId, orderId, callback) => async (dispatch) => {
  dispatch({ type: actions.DELETE });

  try {
    const response = await orderService.delete(userId, orderId);
    if (response.ok) {
      dispatch({ type: actions.DELETE_OK, payload: response.data });
      if(callback) { callback(); }
    } else {
      dispatch({ type: actions.DELETE_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.DELETE_FAIL });
  }
};

export const resetDeleteOrder = () => ({ type: actions.DELETE_RESET });