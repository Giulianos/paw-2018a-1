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

export const rating = userId => async (dispatch) => {
  dispatch({ type: actions.RATING, payload:  { id: userId } });

  try {
    const response = await userService.rating(userId);
    if (response.ok) {
      dispatch({ type: actions.RATING_OK, payload: { id: userId, rating: response.data.rating} });
    } else {
      dispatch({ type: actions.RATING_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.RATING_FAIL });
  }
};

export const resetRating = () => ({ type: actions.RATING_RESET });