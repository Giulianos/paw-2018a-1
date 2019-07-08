import React from 'react';
import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';

function OrdersList({ className }) {
  return (
    <CardContainer className={className}>
      <h1 className="txt-medium txt-black pt-24 pl-16 mb-32">Suscriptores</h1>
      <ul>
        <li className={styles.order}><span className="txt-black txt-normal mb-4">Giuliano ITBA</span><span className="txt-gray2 txt-small">Ordeno 10</span></li>
        <li className={styles.order}><span className="txt-black txt-normal mb-4">Giuliano ITBA</span><span className="txt-gray2 txt-small">Ordeno 10</span></li>
        <li className={styles.order}><span className="txt-black txt-normal mb-4">Giuliano ITBA</span><span className="txt-gray2 txt-small">Ordeno 10</span></li>
        <li className={styles.order}><span className="txt-black txt-normal mb-4">Giuliano ITBA</span><span className="txt-gray2 txt-small">Ordeno 10</span></li>
      </ul>
    </CardContainer>
  );
}

export default OrdersList;