import messageService from 'services/message';
import actions from './actions';

export const listOrderMessages = (userId, orderId) => async (dispatch) => {
  dispatch({ type: actions.LIST });

  try {
    const response = await messageService.order.list(userId, orderId);
    if (response.ok) {
      dispatch({ type: actions.LIST_OK, payload: response.data });
    } else {
      dispatch({ type: actions.LIST_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.LIST_FAIL });
  }
};

export const listUnseenOrderMessages = (userId, orderId) => async (dispatch) => {
  dispatch({ type: actions.LIST });

  try {
    const response = await messageService.order.unseen.list(userId, orderId);
    if (response.ok) {
      dispatch({ type: actions.LIST_OK, payload: response.data });
    } else {
      dispatch({ type: actions.LIST_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.LIST_FAIL });
  }
};

export const listPublicationMessages = (publicationId, ordererId) => async (dispatch) => {
  dispatch({ type: actions.LIST });

  try {
    const response = await messageService.publication.list(publicationId, ordererId);
    if (response.ok) {
      dispatch({ type: actions.LIST_OK, payload: response.data });
    } else {
      dispatch({ type: actions.LIST_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.LIST_FAIL });
  }
};

export const listUnseenPublicationMessages = (publicationId, ordererId) => async (dispatch) => {
  dispatch({ type: actions.LIST });

  try {
    const response = await messageService.publication.unseen.list(publicationId, ordererId);
    if (response.ok) {
      dispatch({ type: actions.LIST_OK, payload: response.data });
    } else {
      dispatch({ type: actions.LIST_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.LIST_FAIL });
  }
};

export const resetList = () => ({ type: actions.LIST_RESET });

export const sendMessageToOrder = (userId, orderId) => message => async (dispatch) => {
  dispatch({ type: actions.SEND });

  try {
    const response = await messageService.order.send(userId, orderId, message);
    if (response.ok) {
      dispatch({ type: actions.SEND_OK, payload: response.data });
    } else {
      dispatch({ type: actions.SEND_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.SEND_FAIL });
  }
};

export const sendMessageToPublication = (publicationId, ordererId) => message => async (dispatch) => {
  dispatch({ type: actions.SEND });

  try {
    const response = await messageService.publication.send(publicationId, ordererId, message);
    if (response.ok) {
      dispatch({ type: actions.SEND_OK, payload: response.data });
    } else {
      dispatch({ type: actions.SEND_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.SEND_FAIL });
  }
};

export const resetSend = () => ({ type: actions.SEND_RESET });
