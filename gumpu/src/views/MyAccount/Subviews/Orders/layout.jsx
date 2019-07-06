import React, { Suspense } from 'react';
import CardContainer from 'components/ui/CardContainer';
import OrderCard from 'components/ui/OrderCard';

import styles from './styles.module.scss';
import { useTranslation } from 'react-i18next';
import Loader from 'components/ui/Loader';

import { getPurchased, getFulfilled, getOrphan, getInProgress } from './filters';

const orderMapper = o => (<li key={o.publication.id}><OrderCard order={o} className="mb-16" /></li>);

function OrdersLayoutSuspense({ orders, loading }) {
  const { t } = useTranslation();

  if(loading) {
    return <Loader />
  }

  const purchasedOrders = getPurchased(orders).map(orderMapper);
  const fulfilledOrders = getFulfilled(orders).map(orderMapper);
  const orphanOrders = getOrphan(orders).map(orderMapper);
  const inProgressOrders = getInProgress(orders).map(orderMapper);

  return (
    <div className="row h100">
      <CardContainer className={`${styles.orderList} column mr-16`}>
        <h1 className="txt-medium mb-32">{t('my_account.orders.status.purchased')}</h1>
        <ul className="flex-grow">
          {purchasedOrders}
        </ul>
      </CardContainer>
      <CardContainer className={`${styles.orderList} column mr-16`}>
        <h1 className="txt-medium mb-32">{t('my_account.orders.status.fulfilled')}</h1>
        <ul className="flex-grow">
          {fulfilledOrders}
        </ul>
      </CardContainer>
      <CardContainer className={`${styles.orderList} column`}>
        <h1 className="txt-medium mb-32">{t('my_account.orders.status.in_progress')}</h1>
        <ul className="flex-grow">
          {orphanOrders}
          {inProgressOrders}
        </ul>
      </CardContainer>
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