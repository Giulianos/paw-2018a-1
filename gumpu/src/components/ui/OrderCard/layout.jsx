import React, { Suspense } from 'react';

import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';
import OrderCardSkeleton from './skeleton';
import PublicationImage from 'components/PublicationImage';
import { useTranslation } from 'react-i18next';

import ActionButton from './components/ActionButton';

function OrderCardLayoutSuspense({ order, className, onMessage, onConfirm, onDelete, onAdopt }) {
  const { t } = useTranslation();
  const publicationState = order.publication.status;
  const ownOrder = order.orderer.id === order.publication.supervisorId;

  return (
    <CardContainer className={`${styles.cardContainer} ${className} ${ownOrder ? styles.ownOrder : ''}`}>
      <div className="row mb-16">
        <PublicationImage className={styles.publicationImage} imageId={order.publication.images[0]} />
        <div className="column ml-8">
          <h2 className={`${styles.orderTitle} txt-medium20 mb-8`}>{order.publication.description}</h2>
          { (publicationState === 'IN_PROGRESS' || publicationState === 'ORPHAN') && (
              <span className="txt-gray3">{t('my_account.orders.card.remaining_quantity', { count: order.publication.availableQuantity })}</span>
            )
          }
          { (publicationState === 'PURCHASED' || publicationState === 'FULFILLED') && (
              <span className="txt-gray3">{t('my_account.orders.card.orderer_quantity', { count: order.quantity })}</span>
            )
          }
        </div>
      </div>
      <div className="row">
        { (publicationState === 'IN_PROGRESS' || publicationState === 'ORPHAN') && (
            <ActionButton onClick={onDelete} className="mr-16" action="delete" label={t('my_account.orders.card.delete')}/>
          )
        }
        { (publicationState === 'PURCHASED' || publicationState === 'FULFILLED' && !ownOrder) && (
            <ActionButton onClick={onMessage} className="mr-16" action="chat" label={t('my_account.orders.card.chat')}/>
          )
        }
        { publicationState === 'PURCHASED' && !ownOrder && (
            <ActionButton onClick={onConfirm} className="mr-16" action="check" label={t('my_account.orders.card.confirm')}/>
          )
        }
        { publicationState === 'ORPHAN' && (
            <ActionButton onClick={onAdopt} className="mr-16" action="supervise" label={t('my_account.orders.card.adopt')}/>
          )
        }
      </div>
    </CardContainer>
  );
}

function OrderCardLayout(props) {
  return (
    <Suspense fallback={<OrderCardSkeleton />}>
      <OrderCardLayoutSuspense {...props} />
    </Suspense>
  );
}

export default OrderCardLayout;