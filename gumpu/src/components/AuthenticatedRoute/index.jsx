import React from 'react';
import { useSelector } from 'react-redux';
import { Redirect, Route } from 'react-router-dom';

function AuthenticatedRoute(props) {
  const userInfo = useSelector(state => state.user.info) || false;

  if(userInfo) {
    /** User is logged in */
    return <Route {...props} />;
  } else {
    /** User is not logged in */
    return <Redirect to="/login" />;
  }
}

export default AuthenticatedRoute;