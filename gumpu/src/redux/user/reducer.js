import actions from "./actions";

const intialState = {
  create: {
    success: false,
    loading: false,
    error: false
  },
  retrieve: {
    success: false,
    loading: false,
    error: false,
    data: undefined
  }
}

function reduce(state = intialState, action) {
  switch(action.type) {
    /** CREATE actions */
    case actions.CREATE:
      return { ...state, create: { loading: true, error: false } };
    case actions.CREATE_OK:
      return { ...state, create: { success: true, loading: false, error: false } };
    case actions.CREATE_FAIL:
      return { ...state, create: { success: false, loading: false, error: true } };
    case actions.CREATE_RESET:
      return { ...state, create: { ...intialState.create } };
    
    /** RETRIEVE actions */
    case actions.RETRIEVE:
      return { ...state, retrieve: { loading: true, error: false } };
    case actions.RETRIEVE_OK:
      return { ...state, retrieve: { success: true, loading: false, error: false, data: action.payload } };
    case actions.RETRIEVE_FAIL:
      return { ...state, retrieve: { success: false, loading: false, error: true } };
    case actions.RETRIEVE_RESET:
      return { ...state, retrieve: { ...intialState.retrieve } };
  
    default:
      return state;
  }
}

export default reduce;