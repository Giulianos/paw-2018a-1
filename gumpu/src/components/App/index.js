import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import PropTypes from 'prop-types';

import Home from 'views/Home';
import Secondary from 'views/Secondary';

function App({ store }) {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/secondary" component={Secondary} />
        <Route path="/" component={Home} />
      </Switch>
    </BrowserRouter>
  );
}

App.propTypes = {
  store: PropTypes.object.isRequired
}

export default App;
