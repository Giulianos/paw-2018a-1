import React from 'react';

import OrderCardLayout from './layout';
import Skeleton from './skeleton';

function OrderCard({ className, order, onMessage }) {
  if(!order) {
    return <Skeleton />;
  }
  
  const messageHandler = () => {
    if(onMessage) {
      onMessage(order);
    }
  }

  return <OrderCardLayout className={className} order={order} onMessage={messageHandler} />;
}

export default OrderCard;