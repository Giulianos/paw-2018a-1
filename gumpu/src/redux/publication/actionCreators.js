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

export const search = (term, page, pageSize) => async (dispatch) => {
  dispatch({ type: actions.SEARCH });

  try {
    const response = await publicationService.search(term, page, pageSize);
    if (response.ok) {
      dispatch({ type: actions.SEARCH_OK, payload: response.data });
    } else {
      dispatch({ type: actions.SEARCH_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.SEARCH_FAIL });
  }
};

export const resetSearch = () => ({ type: actions.SEARCH_RESET });

export const retrieve = publicationId => async (dispatch) => {
  dispatch({ type: actions.RETRIEVE });

  try {
    const response = await publicationService.retrieve(publicationId);
    if (response.ok) {
      dispatch({ type: actions.RETRIEVE_OK, payload: response.data });
    } else {
      dispatch({ type: actions.RETRIEVE_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.RETRIEVE_FAIL });
  }
};

export const resetRetrieve = () => ({ type: actions.RETRIEVE_RESET });

export const deletePublication = (publicationId) => async (dispatch) => {
  dispatch({ type: actions.DELETE });

  try {
    const response = await publicationService.delete(publicationId);
    if (response.ok) {
      dispatch({ type: actions.DELETE_OK, payload: response.data });
    } else {
      dispatch({ type: actions.DELETE_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.DELETE_FAIL });
  }
};

export const resetDeletePublication = () => ({ type: actions.DELETE_RESET });

export const adoptPublication = (publicationId) => async (dispatch) => {
  dispatch({ type: actions.ADOPT });

  try {
    const response = await publicationService.adopt(publicationId);
    if (response.ok) {
      dispatch({ type: actions.ADOPT_OK, payload: response.data });
    } else {
      dispatch({ type: actions.ADOPT_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.ADOPT_FAIL });
  }
};

export const resetAdoptPublication = () => ({ type: actions.ADOPT_RESET });

export const markPurchased = publicationId => async (dispatch) => {
  dispatch({ type: actions.MARK_PURCHASED });

  try {
    const response = await publicationService.markPurchased(publicationId);
    if (response.ok) {
      dispatch({ type: actions.MARK_PURCHASED_OK, payload: response.data });
    } else {
      dispatch({ type: actions.MARK_PURCHASED_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.MARK_PURCHASED_FAIL });
  }
};

export const resetMarkPurchased = () => ({ type: actions.MARK_PURCHASED_RESET });

export const addImage = (publicationId, base64Image) => async (dispatch) => {
  dispatch({ type: actions.ADD_IMAGE });

  try {
    const response = await publicationService.addImage(publicationId, base64Image);
    if (response.ok) {
      dispatch({ type: actions.ADD_IMAGE_OK, payload: response.data });
    } else {
      dispatch({ type: actions.ADD_IMAGE_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.ADD_IMAGE_FAIL });
  }
};

export const resetAddImage = () => ({ type: actions.ADD_IMAGE_RESET });