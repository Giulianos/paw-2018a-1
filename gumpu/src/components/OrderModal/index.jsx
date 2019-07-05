import React from 'react';

import OrderModalLayout from './layout';

import mockPublication from 'mocks/publication';

function OrderModal({ shown, onClose }) {
  return shown && <OrderModalLayout onClose={onClose} data={mockPublication} />;
}

export default OrderModal;