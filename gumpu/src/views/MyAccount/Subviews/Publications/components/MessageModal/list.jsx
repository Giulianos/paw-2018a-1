import React from 'react';
import { Link } from 'react-router-dom';
import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';


function OrdersList({ className, orders }) {
  if(orders.loading) {
    return <div>Cargando...</div>;
  }

  return (
    <CardContainer className={className}>
      <h1 className="txt-medium txt-black pt-24 pl-16 mb-32">Suscriptores</h1>
      <ul>
        { orders.orders.map(o => (
          <Link to={`/my-account/publications/${o.publication.id}/messages/${o.orderer.id}`}><li key={o.orderer.id} className={styles.order}><span className="txt-black txt-normal mb-4">{o.orderer.name}</span><span className="txt-gray2 txt-small">Ordeno {o.quantity}</span></li></Link>
        ))
        }
      </ul>
    </CardContainer>
  );
}

export default OrdersList;