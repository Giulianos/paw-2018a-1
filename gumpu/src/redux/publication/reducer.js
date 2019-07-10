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
    loading: false,
    error: false,
    results: [],
    nextPage: 0
  },
  retrieve: {
    sucess: false,
    loading: false,
    error: false,
    publication: null
  },
  delete: {
    success: false,
    loading: false,
    error: false
  },
  adopt: {
    success: false,
    loading: false,
    error: false
  },
  markPurchased: {
    success: false,
    loading: false,
    error: false
  },
  addImage: {
    success: false,
    loading: false,
    error: false
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

    /** SEARCH actions */
    case actions.SEARCH:
      return { ...state, search: { ...state.search, loading: true, error: false } };
    case actions.SEARCH_OK:
      return { ...state, search: {
        success: true,
        loading: false,
        error: false,
        results: [ ...state.search.results, ...action.payload.publications ],
        nextPage: action.payload.nextPage
      }
    };
    case actions.SEARCH_FAIL:
      return { ...state, search: { ...state.search, success: false, loading: false, error: true } };
    case actions.SEARCH_RESET:
      return { ...state, search: { ...initialState.search } };

    /** RETRIEVE actions */
    case actions.RETRIEVE:
      return { ...state, retrieve: { ...state.retrieve, loading: true, error: false } };
    case actions.RETRIEVE_OK:
      return { ...state, retrieve: {
        success: true,
        loading: false,
        error: false,
        publication: action.payload
      }
    };
    case actions.RETRIEVE_FAIL:
      return { ...state, retrieve: { ...state.retrieve, success: false, loading: false, error: true } };
    case actions.RETRIEVE_RESET:
      return { ...state, retrieve: { ...initialState.retrieve } };
    
    /** DELETE actions */
    case actions.DELETE:
      return { ...state, delete: { ...state.delete, loading: true, error: false } };
    case actions.DELETE_OK:
      return { ...state, delete: { success: true, loading: false, error: false } };
    case actions.DELETE_FAIL:
      return { ...state, delete: { success: false, loading: false, error: true } };
    case actions.DELETE_RESET:
      return { ...state, delete: { ...initialState.delete } };

    /** ADOPT actions */
    case actions.ADOPT:
      return { ...state, adopt: { ...state.adopt, loading: true, error: false } };
    case actions.ADOPT_OK:
      return { ...state, adopt: { success: true, loading: false, error: false } };
    case actions.ADOPT_FAIL:
      return { ...state, adopt: { success: false, loading: false, error: true } };
    case actions.ADOPT_RESET:
      return { ...state, adopt: { ...initialState.adopt } };

    /** MARK_PURCHASED actions */
    case actions.MARK_PURCHASED:
      return { ...state, markPurchased: { ...state.markPurchased, loading: true, error: false } };
    case actions.MARK_PURCHASED_OK:
      return { ...state, markPurchased: { success: true, loading: false, error: false } };
    case actions.MARK_PURCHASED_FAIL:
      return { ...state, markPurchased: { success: false, loading: false, error: true } };
    case actions.MARK_PURCHASED_RESET:
      return { ...state, markPurchased: { ...initialState.markPurchased } };

    /** ADD_IMAGE actions */
    case actions.ADD_IMAGE:
      return { ...state, addImage: { ...state.addImage, loading: true, error: false } };
    case actions.ADD_IMAGE_OK:
      return { ...state, addImage: { success: true, loading: false, error: false } };
    case actions.ADD_IMAGE_FAIL:
      return { ...state, addImage: { success: false, loading: false, error: true } };
    case actions.ADD_IMAGE_RESET:
      return { ...state, addImage: { ...initialState.addImage } };

    default:
      return state;
  }
}

export default reduce;
