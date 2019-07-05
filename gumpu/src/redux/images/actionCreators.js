import imageService from 'services/image';
import actions from './actions';

export const retrieveImage = id => async (dispatch) => {
  dispatch({ type: actions.RETRIEVE, payload: { id } });

  try {
    const response = await imageService.retrieve(id);
    if (response.ok) {
      dispatch({ type: actions.RETRIEVE_OK, payload: { image: response.data, id } });
    } else {
      dispatch({ type: actions.RETRIEVE_FAIL, payload: { id } });
    }
  } catch (error) {
    dispatch({ type: actions.RETRIEVE_FAIL, payload: { id } });
  }
};

export const resetRetrieveImage = id => ({ type: actions.RETRIEVE_RESET, payload: { id } });
