import React from 'react';

import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';

function PublicationCardSkeleton({ className }) {
  return (
    <CardContainer className={`${styles.cardContainer} ${styles.skeleton} ${className}`}>
    <div className="row mb-16">
      <div className={styles.image} />
        <div className={styles.data}>
          <div />
          <div />
        </div>
      </div>
    <div className={styles.actions} />
    </CardContainer>
  );
}

export default PublicationCardSkeleton;