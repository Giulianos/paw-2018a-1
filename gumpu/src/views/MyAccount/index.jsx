import React, { Suspense } from 'react';
import Loader from 'components/ui/Loader';

function MyAccountSuspense() {
  return (
    <div className="view-container column center-alt">
      My account
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