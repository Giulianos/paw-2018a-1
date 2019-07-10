import React, { Suspense } from 'react';
import { useTranslation } from 'react-i18next';
import { Route } from 'react-router-dom';

import CardContainer from 'components/ui/CardContainer';
import OrderCard from 'components/ui/OrderCard';
import Loader from 'components/ui/Loader';

import MessageModal from './components/MessageModal';
import styles from './styles.module.scss';

import { getPurchased, getFulfilled, getOrphan, getInProgress } from './filters';
import ReviewModal from './components/ReviewModal';
import history from 'router/history';

const orderMapper = (messageHandler, deleteHandler) => o => (
  <li key={o && o.publication.id}>
    <OrderCard
      order={o}
      className="mb-16"
      onMessage={messageHandler}
      onConfirm={() => history.replace(`/my-account/orders/${o && o.publication.id}/confirmation`)}
      onDelete={deleteHandler && deleteHandler(o && o.publication.id)}
    />
  </li>
);

function OrdersLayoutSuspense({ orders, loading, messageModal, setMessageModal, onDelete }) {
  const { t } = useTranslation();

  const purchasedOrders = getPurchased(orders).map(orderMapper(setMessageModal));
  const fulfilledOrders = getFulfilled(orders).map(orderMapper(setMessageModal));
  const orphanOrders = getOrphan(orders).map(orderMapper(null, onDelete));
  const inProgressOrders = getInProgress(orders).map(orderMapper(null, onDelete));

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
      <Route path="/my-account/orders/:ord_id/confirmation" render={props => (
        <ReviewModal {...props}/>
      )} />
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