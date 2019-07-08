import React from 'react';

import MessageModalLayout from './layout';

function MessageModal({ setModal, publication}) {
  const closeModal = () => {
    setModal(null);
  }

  return <MessageModalLayout onClose={closeModal} publication={publication} />;
}

export default MessageModal;