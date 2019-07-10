import actions from './actions';

const initialStateSingle = {
  success: false,
  loading: true,
  error: false,
  data: undefined,
};

const intialState = {
  create: {
    success: false,
    loading: false,
    error: false,
  },
  retrieve: {
    success: false,
    loading: false,
    error: false,
    data: undefined,
  },
  rating: {
  },
};

function reduce(state = intialState, action) {
  let id;
  switch (action.type) {
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
      return {
        ...state,
        retrieve: {
          success: true, loading: false, error: false, data: action.payload,
        },
      };
    case actions.RETRIEVE_FAIL:
      return { ...state, retrieve: { success: false, loading: false, error: true } };
    case actions.RETRIEVE_RESET:
      return { ...state, retrieve: { ...intialState.retrieve } };

    /** RATING actions */
    case actions.RATING:
      id = action.payload.id;
      return {
        ...state,
        rating: {
          ...state.rating,
          [id]: initialStateSingle
        }
      };
    case actions.RATING_OK:
      id = action.payload.id;
      return {
        ...state,
        rating: {
          ...state.rating,
          [id]: { 
            success: true, loading: false, error: false, data: action.payload.rating
          },
        },
      };
    case actions.RATING_FAIL:
      id = action.payload.id;
      return {
        ...state,
        rating: {
          ...state.rating,
          [id]: { 
            success: false, loading: false, error: true
          },
        },
      };
    case actions.RATING_RESET:
      id = action.payload.id;
      return {
        ...state,
        rating: {
          ...state.rating,
          [id]: undefined
        },
      };
    default:
      return state;
  }
}

export default reduce;
