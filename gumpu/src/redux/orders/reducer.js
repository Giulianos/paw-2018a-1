import actions from './actions';

const initialState = {
  list: {
    success: false,
    loading: false,
    error: false,
    orders: [],
    nextPage: 0,
    totalPages: 1
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
        orders: [ ...state.list.orders, ...action.payload.orders ],
        nextPage: action.payload.nextPage,
        totalPages: action.payload.totalPages
      } };
    case actions.LIST_FAIL:
      return { ...state, list: { success: false, loading: false, error: true } };
    case actions.LIST_RESET:
      return { ...state, list: { ...initialState.list } };

    default:
      return state;
  }
}

export default reduce;
