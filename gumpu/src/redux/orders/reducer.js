import actions from './actions';

const initialState = {
  list: {
    success: false,
    loading: false,
    error: false,
    data: undefined
  }
};

function reduce(state = initialState, action) {
  switch (action.type) {
    /** LIST actions */
    case actions.LIST:
      return { ...state, list: { loading: true, error: false } };
    case actions.LIST_OK:
      return { ...state, list: { success: true, loading: false, error: false, data: action.payload } };
    case actions.LIST_FAIL:
      return { ...state, list: { success: false, loading: false, error: true } };
    case actions.LIST_RESET:
      return { ...state, list: { ...initialState.list } };

    default:
      return state;
  }
}

export default reduce;
