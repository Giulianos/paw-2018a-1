import React from 'react';

import OrderCardLayout from './layout';
import Skeleton from './skeleton';

function OrderCard({ className, order, onMessage, onConfirm }) {
  if(!order) {
    return <Skeleton />;
  }
  
  const messageHandler = () => {
    if(onMessage) {
      onMessage(order);
    }
  }

  return <OrderCardLayout className={className} order={order} onMessage={messageHandler} onConfirm={onConfirm} />;
}

export default OrderCard;