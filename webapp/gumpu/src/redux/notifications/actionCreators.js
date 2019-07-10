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
