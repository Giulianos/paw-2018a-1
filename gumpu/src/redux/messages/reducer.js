import uniqBy from 'lodash/uniqBy';
import actions from './actions';

const initialState = {
  list: {
    success: false,
    loading: false,
    error: false,
    messages: []
  },
  send: {
    sucess: false,
    loading: false,
    error: false
  }
};

function reduce(state = initialState, action) {
  switch (action.type) {
    /** LIST actions */
    case actions.LIST:
      return { ...state, list: { ...state.list, loading: true, error: false } };
    case actions.LIST_OK:
      return { ...state, list: {
        success: true,
        loading: false,
        error: false,
        messages: uniqBy([ ...state.list.messages, ...action.payload.messages ], m => m.id)
      } };
    case actions.LIST_FAIL:
      return { ...state, list: { success: false, loading: false, error: true } };
    case actions.LIST_RESET:
      return { ...state, list: { ...initialState.list } };

    /** SEND actions */
    case actions.SEND:
      return { ...state, send: { ...state.send, loading: true, error: false } };
    case actions.SEND_OK:
      return { ...state, send: { success: true, loading: false, error: false } };
    case actions.SEND_FAIL:
      return { ...state, send: { success: false, loading: false, error: true } };
    case actions.SEND_RESET:
      return { ...state, send: { ...initialState.send } };
    default:
      return state;
  }
}

export default reduce;
