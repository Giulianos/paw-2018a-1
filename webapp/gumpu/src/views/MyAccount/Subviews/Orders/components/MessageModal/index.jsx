import React from 'react';

import MessageModalLayout from './layout';

function MessageModal({ setModal, order}) {
  const closeModal = () => {
    setModal(null);
  }

  return <MessageModalLayout onClose={closeModal} order={order} />;
}

export default MessageModal;