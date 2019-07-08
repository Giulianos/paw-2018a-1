import React from 'react';
import { Switch, Route } from 'react-router-dom';

import Messages from 'components/Messages';
import Modal from 'components/ui/Modal';
import OrdersList from './list';

import styles from './styles.module.scss';
import CardContainer from 'components/ui/CardContainer';
import RoutedMessages from './routedMessages';

function EmptyChat() {
  return <div className={styles.emptyChat}>Seleccione una conversacion de la list</div>
}

function MessageModalLayout({ onClose, orders }) {
  return (
    <Modal onClose={onClose}>
      <div className="row pt-16 pb-16 pl-16 pr-16">
        <OrdersList orders={orders} className="mr-16" />
        <CardContainer>
          <Switch>
            <Route path="/my-account/publications/:pub_id/messages/:ord_id" component={RoutedMessages} />
            <Route path="/my-account/publications/:pub_id/messages" component={EmptyChat} />
          </Switch>
        </CardContainer>
      </div>
    </Modal>
  );
}

export default MessageModalLayout;