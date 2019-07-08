import actions from './actions';

const initialState = {
  create: {
    success: false,
    loading: false,
    error: false,
    data: undefined
  },
  order: {
    create: {
      success: false,
      loading: false,
      error: false,
      data: undefined
    }
  },
  userList: {
    success: false,
    loading: false,
    error: false,
    publications: [],
    nextPage: 0,
    totalPages: 1
  }
};

function reduce(state = initialState, action) {
  switch (action.type) {
    /** CREATE actions */
    case actions.CREATE:
      return { ...state, create: { loading: true, error: false } };
    case actions.CREATE_OK:
      return { ...state, create: { success: true, loading: false, error: false, data: action.payload } };
    case actions.CREATE_FAIL:
      return { ...state, create: { success: false, loading: false, error: true } };
    case actions.CREATE_RESET:
      return { ...state, create: { ...initialState.create } };

    /** ORDER actions */
    case actions.ORDER:
      return { ...state, order: { ...initialState.order, create: { loading: true, error: false } } };
    case actions.ORDER_OK:
      return { ...state, order: { ...initialState.order, create: { success: true, loading: false, error: false, data: action.payload } } };
    case actions.ORDER_FAIL:
      return { ...state, order: { ...initialState.order, create: { success: false, loading: false, error: true} } };
    case actions.ORDER_RESET:
      return { ...state, order: { ...initialState.order, create: { ...initialState.order.create } } };

    /** USER_LIST actions */
    case actions.LIST_USER:
      return { ...state, userList: { ...state.userList, loading: true, error: false } };
    case actions.LIST_USER_OK:
      return { ...state, userList: {
        success: true,
        loading: false,
        error: false,
        publications: [ ...state.userList.publications, ...action.payload.publications ]
      } };
    case actions.LIST_USER_FAIL:
      return { ...state, userList: { success: false, loading: false, error: true } };
    case actions.LIST_USER_RESET:
      return { ...state, userList: { ...initialState.userList } };

    default:
      return state;
  }
}

export default reduce;
