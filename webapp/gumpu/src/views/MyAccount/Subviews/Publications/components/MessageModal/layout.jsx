import React, { Suspense } from 'react';
import { Switch, Route } from 'react-router-dom';

import Modal from 'components/ui/Modal';
import OrdersList from './list';

import styles from './styles.module.scss';
import CardContainer from 'components/ui/CardContainer';
import RoutedMessages from './routedMessages';
import { useTranslation } from 'react-i18next';
import Loader from 'components/ui/Loader';

function EmptyChat() {
  return <div className={styles.emptyChat}>Seleccione una conversacion de la list</div>
}

function MessageModalLayoutSuspense({ onClose, orders }) {
  const { t } = useTranslation()

  return (
    <Modal onClose={onClose}>
      <div className="row pt-16 pb-16 pl-16 pr-16">
        <OrdersList orders={orders} className="mr-16" />
        <CardContainer>
          <Switch>
            <Route path="/my-account/publications/:pub_id/messages/:ord_id" component={RoutedMessages} />
            <Route path="/my-account/publications/:pub_id/messages" render={() => (
              <div className={styles.emptyChat}>{t('my_account.publications.messages.select_from_list')}</div>
            )} />
          </Switch>
        </CardContainer>
      </div>
    </Modal>
  );
}

function MessageModalLayout(props) {
  return (
    <Suspense fallback={<Loader />}>
      <MessageModalLayoutSuspense {...props} />  
    </Suspense>
  );
}

export default MessageModalLayout;