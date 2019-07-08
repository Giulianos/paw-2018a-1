import React from 'react';

import MessageModalLayout from './layout';

function MessageModal({ setModal, orderId }) {
  const closeModal = () => {
    setModal(null);
  }

  return <MessageModalLayout onClose={closeModal} orderId={orderId} />;
}

export default MessageModal;