import React from 'react';

import OrderCardLayout from './layout';
import Skeleton from './skeleton';

function OrderCard({ className, order, onMessage }) {
  if(!order) {
    return <Skeleton />;
  }
  
  const messageHandler = () => {
    console.log("Chatting")
    if(onMessage) {
      onMessage(order.publication.id);
    }
  }

  return <OrderCardLayout className={className} order={order} onMessage={messageHandler} />;
}

export default OrderCard;