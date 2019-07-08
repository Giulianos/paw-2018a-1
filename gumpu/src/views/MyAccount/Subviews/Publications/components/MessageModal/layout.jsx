import React from 'react';

import Messages from 'components/Messages';
import Modal from 'components/ui/Modal';
import OrdersList from './list';

import styles from './styles.module.scss';
import CardContainer from 'components/ui/CardContainer';

function MessageModalLayout({ onClose, publication }) {
  return (
    <Modal onClose={onClose}>
      <div className="row pt-16 pb-16 pl-16 pr-16">
        <OrdersList className="mr-16" />
        <CardContainer className={styles.emptyChat}>Seleccione una conversacion de la lista</CardContainer>
      </div>
    </Modal>
  );
}

export default MessageModalLayout;