import actions from "./actions";

const intialState = {
  create: {
    loading: false,
    error: false
  }
}

function reduce(state = intialState, action) {
  switch(action.type) {
    case actions.CREATE:
      return { ...state, create: { loading: true, error: false } };
    case actions.CREATE_OK:
      return { ...state, create: { loading: false, error: false } };
    case actions.CREATE_FAIL:
      return { ...state, create: { loading: false, error: true } };
    default:
      return state;
  }
}

export default reduce;