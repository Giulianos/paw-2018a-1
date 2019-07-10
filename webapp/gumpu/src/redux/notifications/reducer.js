import actions from './actions';

const intialState = {
  retrieve: {
    success: false,
    loading: false,
    error: false,
    data: undefined,
  },
  clear: {
    success: false,
    loading: false,
    error: false,
  },
};

function reduce(state = intialState, action) {
  switch (action.type) {
    /** RETRIEVE actions */
    case actions.RETRIEVE:
      return { ...state, retrieve: { ...state.retrieve, loading: true, error: false } };
    case actions.RETRIEVE_OK:
      return {
        ...state,
        retrieve: {
          success: true, loading: false, error: false, data: action.payload,
        },
      };
    case actions.RETRIEVE_FAIL:
      return { ...state, retrieve: { success: false, loading: false, error: true } };
    case actions.RETRIEVE_RESET:
      return { ...state, retrieve: { ...intialState.retrieve } };

    /** CLEAR actions */
    case actions.CLEAR:
      return { ...state, clear: { ...state.clear, loading: true, error: false } };
    case actions.CLEAR_OK:
      return {
        ...state,
        clear: {
          success: true, loading: false, error: false, data: action.payload,
        },
      };
    case actions.CLEAR_FAIL:
      return { ...state, clear: { success: false, loading: false, error: true } };
    case actions.CLEAR_RESET:
      return { ...state, clear: { ...intialState.clear } };

    default:
      return state;
  }
}

export default reduce;
