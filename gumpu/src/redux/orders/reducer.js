import actions from './actions';

const initialState = {
  list: {
    success: false,
    loading: false,
    error: false,
    orders: [],
    nextPage: 0,
    totalPages: 1
  },
  rate: {
    success: false,
    loading: false,
    error: false
  },
  confirm: {
    success: false,
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
        orders: [ ...state.list.orders, ...action.payload.orders ],
        nextPage: action.payload.nextPage,
        totalPages: action.payload.totalPages
      } };
    case actions.LIST_FAIL:
      return { ...state, list: { success: false, loading: false, error: true } };
    case actions.LIST_RESET:
      return { ...state, list: { ...initialState.list } };

    /** RATE actions */
    case actions.RATE:
      return { ...state, rate: { ...state.rate, loading: true, error: false } };
    case actions.RATE_OK:
      return { ...state, rate: { success: true, loading: false, error: false } };
    case actions.RATE_FAIL:
      return { ...state, rate: { success: false, loading: false, error: true } };
    case actions.RATE_RESET:
      return { ...state, rate: { ...initialState.rate } };

    /** CONFIRM actions */
    case actions.CONFIRM:
      return { ...state, confirm: { ...state.confirm, loading: true, error: false } };
    case actions.CONFIRM_OK:
      return { ...state, confirm: { success: true, loading: false, error: false } };
    case actions.CONFIRM_FAIL:
      return { ...state, confirm: { success: false, loading: false, error: true } };
    case actions.CONFIRM_RESET:
      return { ...state, confirm: { ...initialState.confirm } };

    default:
      return state;
  }
}

export default reduce;
