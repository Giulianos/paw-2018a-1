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
  },
  latest: {
    success: false,
    loading: false,
    error: false,
    publications: []
  },
  ordersList: {
    success: false,
    loading: false,
    error: false,
    orders: [],
    nextPage: 0,
    totalPages: 1
  },
  search: {
    sucess: false,
    loading: true,
    error: false,
    results: [],
    nextPage: 0
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
      }
    };
    case actions.LIST_USER_FAIL:
      return { ...state, userList: { success: false, loading: false, error: true } };
    case actions.LIST_USER_RESET:
      return { ...state, userList: { ...initialState.userList } };

    /** LATEST actions */
    case actions.LATEST:
      return { ...state, latest: { ...state.latest, loading: true, error: false } };
    case actions.LATEST_OK:
      return { ...state, latest: {
        success: true,
        loading: false,
        error: false,
        publications: [ ...state.latest.publications, ...action.payload.publications ]
      }
    };
    case actions.LATEST_FAIL:
      return { ...state, latest: { success: false, loading: false, error: true } };
    case actions.LATEST_RESET:
      return { ...state, latest: { ...initialState.latest } };

    /** LIST_ORDERS actions */
    case actions.LIST_ORDERS:
      return { ...state, ordersList: { ...state.ordersList, loading: true, error: false } };
    case actions.LIST_ORDERS_OK:
      return { ...state, ordersList: {
        success: true,
        loading: false,
        error: false,
        orders: [ ...state.ordersList.orders, ...action.payload.orders ]
      }
    };
    case actions.LIST_ORDERS_FAIL:
      return { ...state, ordersList: { success: false, loading: false, error: true } };
    case actions.LIST_ORDERS_RESET:
      return { ...state, ordersList: { ...initialState.ordersList } };

    default:
      return state;
  }
}

export default reduce;
