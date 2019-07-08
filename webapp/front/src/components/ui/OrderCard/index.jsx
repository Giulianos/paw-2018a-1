import React from 'react';

import OrderCardLayout from './layout';
import Skeleton from './skeleton';

function OrderCard({ className, order }) {
  if(!order) {
    return <Skeleton />;
  }
  return <OrderCardLayout className={className} order={order} />;
}

export default OrderCard;