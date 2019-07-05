import React from 'react';

import OrderModalLayout from './layout';

import mockPublication from 'mocks/publication';

function OrderModal() {
  return <OrderModalLayout data={mockPublication} />;
}

export default OrderModal;