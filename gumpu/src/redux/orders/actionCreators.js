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
