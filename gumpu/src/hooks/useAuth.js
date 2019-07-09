import { useSelector, useDispatch } from 'react-redux';

import { logout } from 'redux/auth/actionCreators';
import { resetRetrieveUser } from 'redux/user/actionCreators';

const userInfoSelector = state => state.user.retrieve;

function useAuth() {
  const userInfo = useSelector(userInfoSelector);
  const dispatch = useDispatch();

  return {
    user: userInfo.data,
    logged: userInfo.success,
    logging: userInfo.loading,
    error: userInfo.error,
    logout: () => {
      dispatch(logout());
      dispatch(resetRetrieveUser());
    }
  };
}

export default useAuth;
