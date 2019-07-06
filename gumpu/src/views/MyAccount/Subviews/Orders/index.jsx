import React, { Suspense } from 'react';
import CardContainer from 'components/ui/CardContainer';
import OrderCard from 'components/ui/OrderCard';

import styles from './styles.module.scss';
import { useTranslation } from 'react-i18next';
import Loader from 'components/ui/Loader';

/** TODO: replace with integration */
import mockOrders from 'mocks/order';

function OrdersLayoutSuspense() {
  const { t } = useTranslation();

  return (
    <div className="row h100">
      <CardContainer className={`${styles.orderList} column mr-16`}>
        <h1 className="txt-medium mb-32">{t('my_account.orders.status.purchased')}</h1>
        <ul className="flex-grow">
          <li><OrderCard order={mockOrders.purchased} className="mb-16" /></li>
          <li><OrderCard order={mockOrders.purchased} /></li>
        </ul>
      </CardContainer>
      <CardContainer className={`${styles.orderList} column mr-16`}>
        <h1 className="txt-medium mb-32">{t('my_account.orders.status.fulfilled')}</h1>
        <ul className="flex-grow">
          <li><OrderCard order={mockOrders.fulfilled} className="mb-16" /></li>
          <li><OrderCard order={mockOrders.fulfilled} className="mb-16" /></li>
          <li><OrderCard order={mockOrders.fulfilled} /></li>
        </ul>
      </CardContainer>
      <CardContainer className={`${styles.orderList} column`}>
        <h1 className="txt-medium mb-32">{t('my_account.orders.status.in_progress')}</h1>
        <ul className="flex-grow">
          <li className="animated"><OrderCard order={mockOrders.orphan} className="mb-16" /></li>
          <li><OrderCard order={mockOrders.inProgress} className="mb-16" /></li>
          <li><OrderCard order={mockOrders.inProgress} /></li>
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