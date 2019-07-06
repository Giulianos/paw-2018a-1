import React from 'react';

import OrderCardLayout from './layout';

/** TODO: replace with integration */
import mockOrder from 'mocks/order';

function OrderCard({ className }) {
  return <OrderCardLayout className={className} order={mockOrder} />;
}

export default OrderCard;