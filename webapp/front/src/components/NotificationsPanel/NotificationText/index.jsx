import React from 'react';

import NewMessage from './NewMessage';
import OrderPurchased from './OrderPurchased';
import PublicationFulfilled from './PublicationFulfilled';
import SupervisorLeft from './SupervisorLeft';

function NotificationText({ notification }) {
  switch (notification.type) {
    case 'NEW_MESSAGES':
      return <NewMessage notification={notification} />;
    case 'ORDER_PURCHASED':
      return <OrderPurchased notification={notification} />;
    case 'PUBLICATION_FULFILLED':
      return <PublicationFulfilled notification={notification} />;
    case 'PUBLICATION_ORPHAN':
      return <SupervisorLeft notification={notification} />;
    default:
      return <div />;
  }
}

export default NotificationText;
