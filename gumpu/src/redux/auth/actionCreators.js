import authService from 'services/auth';
import localStorageService from 'services/localStorage';
import history from 'router/history';
import actions from './actions';
import { retrieveUser } from '../user/actionCreators';

export const login = (credentials, redirect) => async (dispatch) => {
  dispatch({ type: actions.LOGIN });

  try {
    const response = await authService.login(credentials);
    if (response.ok) {
      /** Save token in localStorage (to persist session) */
      localStorageService.setToken(response.headers.authorization);
      /** Go back to where we were */
      dispatch({ type: actions.LOGIN_OK });
      dispatch(retrieveUser());
      history.replace(redirect || '/');
    } else {
      dispatch({ type: actions.LOGIN_FAIL });
    }
  } catch (error) {
    dispatch({ type: actions.LOGIN_FAIL });
  }
};

export const resetLogin = () => ({ type: actions.LOGIN_RESET });

export const logout = () => {
  /** Remove token from localStorage */
  localStorageService.removeToken();
  /** Reset login (to allow subsequent logins) */
  return resetLogin();
};
