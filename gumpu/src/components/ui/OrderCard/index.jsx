import React from 'react';

import OrderCardLayout from './layout';

function OrderCard({ className, order }) {
  return <OrderCardLayout className={className} order={order} />;
}

export default OrderCard;