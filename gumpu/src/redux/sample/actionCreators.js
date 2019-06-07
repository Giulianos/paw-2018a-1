import actions from "./actions";
import history from 'router/history';
import sampleService from "services/sample";

export const createSample = () => dispatch => {
  sampleService.get();
  setTimeout(() => {
    history.push('/');
    dispatch({
      type: actions.SAMPLE,
      payload: { msg: 'Hello World!' }
    });
  }, 2000);
}