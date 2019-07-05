import actions from './actions';

const initialStateSingle = {
  success: false,
  loading: true,
  error: false,
  data: undefined,
};

const intialState = {
  retrieve: {
  },
};

function reduce(state = intialState, action) {
  let id;
  switch (action.type) {
    /** RETRIEVE actions */
    case actions.RETRIEVE:
      id = action.payload.id;
      return {
        ...state,
        retrieve: {
          ...state.retrieve,
          [id]: initialStateSingle
        }
      };
    case actions.RETRIEVE_OK:
      id = action.payload.id;
      return {
        ...state,
        retrieve: {
          ...state.retrieve,
          [id]: { 
            success: true, loading: false, error: false, data: action.payload.image
          },
        },
      };
    case actions.RETRIEVE_FAIL:
      id = action.payload.id;
      return {
        ...state,
        retrieve: {
          ...state.retrieve,
          [id]: { 
            success: false, loading: false, error: true
          },
        },
      };
    case actions.RETRIEVE_RESET:
      id = action.payload.id;
      return {
        ...state,
        retrieve: {
          ...state.retrieve,
          [id]: undefined
        },
      };

    default:
      return state;
  }
}

export default reduce;
