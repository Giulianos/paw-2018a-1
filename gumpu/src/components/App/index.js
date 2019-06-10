import React from 'react';
import { Router, Route, Switch } from 'react-router-dom';

import history from 'router/history';

import Header from 'components/Header';

import Home from 'views/Home';
import Secondary from 'views/Secondary';

import styles from './styles.module.scss';

function App() {
  return (
    <Router history={history}>
      <Header />
      <div className={styles.appContainer}>
        <Switch>
          <Route path="/secondary" component={Secondary} />
          <Route path="/" component={Home} />
        </Switch>
      </div>
    </Router>
  );
}

export default App;
