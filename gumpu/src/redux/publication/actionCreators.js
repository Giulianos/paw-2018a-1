import publicationService from 'services/publication';
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