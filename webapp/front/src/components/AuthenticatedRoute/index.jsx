import React from 'react';
import { Redirect, Route } from 'react-router-dom';
import useAuth from 'hooks/useAuth';

import FullScreenLoader from 'components/ui/FullScreenLoader';

function AuthenticatedRoute(props) {
  const auth = useAuth();

  if (auth.logged) {
    console.log('User logged')
    /** User is logged in */
    return <Route {...props} />;
  } if (auth.logging) {
    console.log('User logging')
    return <FullScreenLoader />;
  }

  console.log('User not logged')
  /** User is not logged in */
  return <Redirect to={{ pathname: '/login', state: { ref: props.location.pathname } }} />;
}

export default AuthenticatedRoute;
