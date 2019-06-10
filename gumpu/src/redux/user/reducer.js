import actions from "./actions";

const intialState = {
  create: {
    success: false,
    loading: false,
    error: false
  }
}

function reduce(state = intialState, action) {
  switch(action.type) {
    case actions.CREATE:
      return { ...state, create: { loading: true, error: false } };
    case actions.CREATE_OK:
      return { ...state, create: { success: true, loading: false, error: false } };
    case actions.CREATE_FAIL:
      return { ...state, create: { success: false, loading: false, error: true } };
    case actions.CREATE_RESET:
      return intialState;
    default:
      return state;
  }
}

export default reduce;