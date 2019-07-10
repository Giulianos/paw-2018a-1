import React from 'react';
import { Link } from 'react-router-dom';
import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';
import useAuth from 'hooks/useAuth';
import Loader from 'components/ui/Loader';
import { useTranslation } from 'react-i18next';


function OrdersListSuspense({ className, orders }) {
  const { t } = useTranslation();

  if(orders.loading) {
    return <Loader />;
  }

  return (
    <CardContainer className={className}>
      <h1 className="txt-medium txt-black pt-24 pl-16 mb-32">Suscriptores</h1>
      <ul>
        { orders.orders.filter(o => (o.orderer.id !== o.publication.supervisorId)).map(o => (
          <Link to={`/my-account/publications/${o.publication.id}/messages/${o.orderer.id}`}>
            <li key={o.orderer.id} className={styles.order}>
              <span className="txt-black txt-normal mb-4">{o.orderer.name}</span>
              <span className="txt-gray2 txt-small">{t('my_account.publications.messages.ordered', {quantity: o.quantity})}</span>
            </li>
          </Link>
        ))
        }
      </ul>
    </CardContainer>
  );
}

function OrdersList(props) {
  return (
    <OrdersListSuspense {...props} />
  );
}

export default OrdersList;