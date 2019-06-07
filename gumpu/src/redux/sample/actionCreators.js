import actions from "./actions";
import history from 'router/history';

export const createSample = () => dispatch => {
  setTimeout(() => {
    history.push('/');
    dispatch({
      type: actions.SAMPLE,
      payload: { msg: 'Hello World!' }
    });
  }, 2000);
}