import React from 'react';

import Messages from 'components/Messages';
import Modal from 'components/ui/Modal';

function MessageModalLayout({ onClose, order }) {
  return <Modal onClose={onClose}><Messages publicationId={order.publication.id} title={order.publication.description} /></Modal>;
}

export default MessageModalLayout;