import React, { useEffect } from 'react';
import { Router, Route, Switch } from 'react-router-dom';
import { useDispatch } from 'react-redux';

import history from 'router/history';
import localStorageService from 'services/localStorage';
import {
  retrieveUser as retrieveUserAction,
} from 'redux/user/actionCreators';

import Header from 'components/Header';
import AuthenticatedRoute from 'components/AuthenticatedRoute';
import FullScreenLoader from 'components/ui/FullScreenLoader';

import Home from 'views/Home';
import Secondary from 'views/Secondary';
import Login from 'views/Login';
import Publish from 'views/Publish';

import useAuth from 'hooks/useAuth';
import Search from 'views/Search';
import styles from './styles.module.scss';

const token = localStorageService.getToken();

function App() {
  const dispatch = useDispatch();
  const auth = useAuth();
  useEffect(() => {
    /** If we have a stored token retrieve userInfo */
    if (token || false) {
      dispatch(retrieveUserAction());
    }
  }, [dispatch]);

  if (token && auth.logging) {
    return <FullScreenLoader />;
  }
  return (
    <Router history={history}>
      <Header />
      <div className={styles.appContainer}>
        <Switch>
          <Route path="/secondary" component={Secondary} />
          <Route path="/login" component={Login} />
          <AuthenticatedRoute path="/publish" component={Publish} />
          <Route path="/search/:term" component={Search} />
          <Route path="/" component={Home} />
        </Switch>
      </div>
    </Router>
  );
}

export default App;
