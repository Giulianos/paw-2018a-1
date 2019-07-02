import actions from "./actions";

const intialState = {
  login: {
    success: false,
    loading: false,
    error: false
  }
}

function reduce(state = intialState, action) {
  switch(action.type) {
    case actions.LOGIN:
      return { ...state, login: { loading: true, error: false } };
    case actions.LOGIN_OK:
      return { ...state, login: { success: true, loading: false, error: false } };
    case actions.LOGIN_FAIL:
      return { ...state, login: { success: false, loading: false, error: true } };
    case actions.LOGIN_RESET:
      return intialState;
    default:
      return state;
  }
}

export default reduce;