import notificationsService from 'services/notifications';
import actions from './actions';

export const retrieveNotifications = id => async (dispatch) => {
  dispatch({ type: actions.RETRIEVE });

  try {
    const response = await notificationsService.retrieve(id);
    if (response.ok) {
      dispatch({ type: actions.RETRIEVE_OK, payload: response.data });
    } else {
      dispatch({ type: actions.RETRIEVE_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.RETRIEVE_FAIL });
  }
};

export const resetRetrieveNotifications = () => ({ type: actions.RETRIEVE_RESET });

export const clearNotifications = userId => async (dispatch) => {
  dispatch({ type: actions.CLEAR });

  try {
    const response = await notificationsService.clear(userId);
    if (response.ok) {
      dispatch({ type: actions.CLEAR_OK, payload: response.data });
      dispatch(resetRetrieveNotifications());
    } else {
      dispatch({ type: actions.CLEAR_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.CLEAR_FAIL });
  }
};

export const resetClearNotifications = () => ({ type: actions.RETRIEVE_RESET });
