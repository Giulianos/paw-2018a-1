import React from 'react';
import { Redirect, Route } from 'react-router-dom';
import useAuth from 'hooks/useAuth';

import FullScreenLoader from 'components/ui/FullScreenLoader';  

function AuthenticatedRoute(props) {
  const auth = useAuth()

  if(auth.logged) {
    /** User is logged in */
    return <Route {...props} />;
  } else if(auth.logging) {
    return <FullScreenLoader />;
  } else {
    /** User is not logged in */
    return <Redirect to={{ pathname: '/login', state: { ref: props.location.pathname } }} />;
  }
}

export default AuthenticatedRoute;