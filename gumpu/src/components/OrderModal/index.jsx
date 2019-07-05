import React from 'react';

import OrderModalLayout from './layout';

function OrderModal({ shown, onClose, selectedPublication }) {
  return shown && <OrderModalLayout onClose={onClose} data={selectedPublication} />;
}

export default OrderModal;
