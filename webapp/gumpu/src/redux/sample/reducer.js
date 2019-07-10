import actions from './actions';

const intialState = {
  msg: 'No message!',
};

function reduce(state = intialState, action) {
  switch (action.type) {
    case actions.SAMPLE:
      return { ...state, ...action.payload };
    default:
      return state;
  }
}

export default reduce;
