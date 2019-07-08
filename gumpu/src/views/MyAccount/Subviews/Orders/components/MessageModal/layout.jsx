import React from 'react';

import Modal from 'components/ui/Modal';

function MessageModalLayout({ onClose, orderId }) {
  return <Modal onClose={onClose}>chatting in {orderId}</Modal>;
}

export default MessageModalLayout;