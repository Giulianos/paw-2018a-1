import React from 'react';

import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';

function ProductCardSkeleton({ className }) {
  return (
    <CardContainer className={`${styles.productCard} ${className} column center-alt pt-16 pb-16 pl-24 pr-24`}>
      <div className={`${styles.imagePlaceHolder} mb-24`} />
      <div className={`${styles.titlePlaceHolder} mb-24`} />
      <div className="row w100">
        <div className={`${styles.pricePlaceHolder} mb-24`} />
      </div>
    </CardContainer>
  );
}

export default ProductCardSkeleton;
