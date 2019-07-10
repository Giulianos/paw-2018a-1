import React, { Suspense } from 'react';
import { Switch, Route } from 'react-router-dom';

import Publications from './Subviews/Publications';
import Orders from './Subviews/Orders';

import Loader from 'components/ui/Loader';

import Sidebar from './components/Sidebar';

function MyAccountSuspense() {
  return (
    <div className="view-container row">
      <Sidebar />
      <div className="flex-grow ml-16">
        <Switch>
          <Route path="/my-account/publications" component={Publications} />
          <Route path="/my-account/orders" component={Orders} />
        </Switch>
      </div>
    </div>
  );
}

function MyAccount(props) {
  return (
    <Suspense fallback={<Loader />}>
      <MyAccountSuspense {...props} />
    </Suspense>
  );
}

export default MyAccount;