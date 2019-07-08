import React, { Suspense } from 'react';
import { useTranslation } from 'react-i18next';

import CardContainer from 'components/ui/CardContainer';
import OrderCard from 'components/ui/OrderCard';
import Loader from 'components/ui/Loader';

import MessageModal from './components/MessageModal';
import styles from './styles.module.scss';

import { getPurchased, getFulfilled, getOrphan, getInProgress } from './filters';

const orderMapper = messageHandler => o => (<li key={o && o.publication.id}><OrderCard order={o} className="mb-16" onMessage={messageHandler} /></li>);

function OrdersLayoutSuspense({ orders, loading, messageModal, setMessageModal }) {
  const { t } = useTranslation();

  const purchasedOrders = getPurchased(orders).map(orderMapper(setMessageModal));
  const fulfilledOrders = getFulfilled(orders).map(orderMapper(setMessageModal));
  const orphanOrders = getOrphan(orders).map(orderMapper());
  const inProgressOrders = getInProgress(orders).map(orderMapper());

  return (
    <div className="row h100">
      <CardContainer className={`${styles.orderList} column mr-16`}>
        <h1 className="txt-medium mb-32">{t('my_account.orders.status.purchased')}</h1>
        <ul className="flex-grow">
          {purchasedOrders}
          {loading && orderMapper(null)}
          {!loading && !purchasedOrders.length && <span className="txt-gray2">{t('my_account.orders.status.empty_list')}</span>}
        </ul>
      </CardContainer>
      <CardContainer className={`${styles.orderList} column mr-16`}>
        <h1 className="txt-medium mb-32">{t('my_account.orders.status.fulfilled')}</h1>
        <ul className="flex-grow">
          {fulfilledOrders}
          {loading && orderMapper(null)}
          {!loading && !fulfilledOrders.length && <span className="txt-gray2">{t('my_account.orders.status.empty_list')}</span>}
        </ul>
      </CardContainer>
      <CardContainer className={`${styles.orderList} column`}>
        <h1 className="txt-medium mb-32">{t('my_account.orders.status.in_progress')}</h1>
        <ul className="flex-grow">
          {orphanOrders}
          {inProgressOrders}
          {loading && orderMapper(null)}
          {!loading && !inProgressOrders.length && !orphanOrders.length && <span className="txt-gray2">{t('my_account.orders.status.empty_list')}</span>}
        </ul>
      </CardContainer>
      {messageModal && <MessageModal setModal={setMessageModal} order={messageModal}/>}
    </div>
  );
}

function OrdersLayout(props) {
  return (
    <Suspense fallback={<Loader />}>
      <OrdersLayoutSuspense {...props} />
    </Suspense>
  );
}

export default OrdersLayout;