import { useSelector } from 'react-redux';

const userInfoSelector = state => state.user.retrieve;

function useAuth() {
  const userInfo = useSelector(userInfoSelector);

  return {
    user: userInfo.data,
    logged: userInfo.success,
    logging: userInfo.loading,
  };
}

export default useAuth;
