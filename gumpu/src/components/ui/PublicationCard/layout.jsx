import React, { Suspense } from 'react';

import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';
import PublicationCardSkeleton from './skeleton';
import PublicationImage from 'components/PublicationImage';
import { useTranslation } from 'react-i18next';

import ActionButton from './components/ActionButton';

function PublicationCardLayoutSuspense({ publication, className, onMessage }) {
  const { t } = useTranslation();
  const publicationState = publication.status;
  return (
    <CardContainer className={`${styles.cardContainer} ${className}`}>
      <div className="row mb-16">
        <PublicationImage className={styles.publicationImage} imageId={publication.images[0]} />
        <div className="column ml-8">
          <h2 className={`${styles.publicationTitle} txt-medium20 mb-8`}>{publication.description}</h2>
          { (publicationState === 'IN_PROGRESS') && (
              <span className="txt-gray3">{t('my_account.publications.card.remaining_quantity', { count: publication.availableQuantity })}</span>
            )
          }
        </div>
      </div>
      <div className="row">
        { (publicationState === 'IN_PROGRESS') && (
            <ActionButton className="mr-16" action="delete" label={t('my_account.publications.card.delete')}/>
          )
        }
        { (publicationState === 'PURCHASED' || publicationState === 'FULFILLED') && (
            <ActionButton onClick={onMessage} className="mr-16" action="chat" label={t('my_account.publications.card.chat')}/>
          )
        }
      </div>
    </CardContainer>
  );
}

function PublicationCardLayout(props) {
  return (
    <Suspense fallback={<PublicationCardSkeleton />}>
      <PublicationCardLayoutSuspense {...props} />
    </Suspense>
  );
}

export default PublicationCardLayout;