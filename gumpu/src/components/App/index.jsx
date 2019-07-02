import React, { useEffect } from 'react';
import { Router, Route, Switch } from 'react-router-dom';
import { useDispatch } from 'react-redux';

import history from 'router/history';
import localStorageService from 'services/localStorage';
import {
  retrieveUser as retrieveUserAction
} from 'redux/user/actionCreators';

import Header from 'components/Header';
import FullScreenLoader from 'components/ui/FullScreenLoader';

import Home from 'views/Home';
import Secondary from 'views/Secondary';
import Login from 'views/Login';

import styles from './styles.module.scss';
import useAuth from 'hooks/useAuth';

function App() {
  const dispatch = useDispatch();
  const auth = useAuth();
  useEffect(() => {
    const token = localStorageService.getToken();
    /** If we have a stored token retrieve userInfo */
    if(token || false) {
      dispatch(retrieveUserAction())
    }
  }, [dispatch])

  return (
    <Router history={history}>
      {auth.logging ?
        <FullScreenLoader /> :
        (<>
          <Header />
      <div className={styles.appContainer}>
        <Switch>
          <Route path="/secondary" component={Secondary} />
          <Route path="/login" component={Login} />
          <Route path="/" component={Home} />
        </Switch>
      </div>
        </>)}
    </Router>
  );
}

export default App;
