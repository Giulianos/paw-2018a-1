import userService from 'services/user';
import actions from './actions';

export const createUser = newUser => async (dispatch) => {
  dispatch({ type: actions.CREATE });

  try {
    const response = await userService.create(newUser);
    if (response.ok) {
      dispatch({ type: actions.CREATE_OK });
    } else {
      dispatch({ type: actions.CREATE_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.CREATE_FAIL });
  }
};

export const resetCreateUser = () => ({ type: actions.CREATE_RESET });

export const retrieveUser = () => async (dispatch) => {
  dispatch({ type: actions.RETRIEVE });

  try {
    const response = await userService.retrieve();
    if (response.ok) {
      dispatch({ type: actions.RETRIEVE_OK, payload: response.data });
    } else {
      dispatch({ type: actions.RETRIEVE_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.RETRIEVE_FAIL });
  }
};

export const resetRetrieveUser = () => ({ type: actions.RETRIEVE_RESET });
