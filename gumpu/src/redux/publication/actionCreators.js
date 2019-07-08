import publicationService from 'services/publication';
import userService from 'services/user';
import actions from './actions';

export const createPublication = newPublication => async (dispatch) => {
  dispatch({ type: actions.CREATE });

  try {
    const response = await publicationService.create(newPublication);
    if (response.ok) {
      dispatch({ type: actions.CREATE_OK, payload: response.data });
    } else {
      dispatch({ type: actions.CREATE_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.CREATE_FAIL });
  }
};

export const resetCreatePublication = () => ({ type: actions.CREATE_RESET });

export const orderPublication = (id, quantity) => async (dispatch) => {
  dispatch({ type: actions.ORDER });

  try {
    const response = await publicationService.orders.create(id, quantity);
    if (response.ok) {
      dispatch({ type: actions.ORDER_OK, payload: response.data });
    } else {
      dispatch({ type: actions.ORDER_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.ORDER_FAIL });
  }
};

export const resetOrderPublication = () => ({ type: actions.ORDER_RESET });

export const listUserPublications = (userId, page, pageSize) => async (dispatch) => {
  dispatch({ type: actions.LIST_USER });

  try {
    const response = await userService.publications.list(userId, page, pageSize);
    if (response.ok) {
      dispatch({ type: actions.LIST_USER_OK, payload: response.data });
    } else {
      dispatch({ type: actions.LIST_USER_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.LIST_USER_FAIL });
  }
};

export const resetListUserPublications = () => ({ type: actions.LIST_USER_RESET });

export const getLatestPublications = pageSize => async (dispatch) => {
  dispatch({ type: actions.LATEST });

  try {
    const response = await publicationService.getLatest(pageSize);
    if (response.ok) {
      dispatch({ type: actions.LATEST_OK, payload: response.data });
    } else {
      dispatch({ type: actions.LATEST_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.LATEST_FAIL });
  }
};

export const resetGetLatest = () => ({ type: actions.LATEST_RESET });

export const listPublicationOrders = (publicationId, page, pageSize) => async (dispatch) => {
  dispatch({ type: actions.LIST_ORDERS });

  try {
    const response = await publicationService.orders.list(publicationId, page, pageSize);
    if (response.ok) {
      dispatch({ type: actions.LIST_ORDERS_OK, payload: response.data });
    } else {
      dispatch({ type: actions.LIST_ORDERS_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.LIST_ORDERS_FAIL });
  }
};

export const resetListPublicationOrders = () => ({ type: actions.LIST_ORDERS_RESET });