import React from 'react';

import Messages from 'components/Messages';
import Modal from 'components/ui/Modal';

function MessageModalLayout({ onClose, publication }) {
  return <Modal onClose={onClose}>Showing messages of {publication.description}</Modal>;
}

export default MessageModalLayout;