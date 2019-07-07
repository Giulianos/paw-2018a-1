import React, { Suspense } from 'react';
import CardContainer from 'components/ui/CardContainer';
import PublicationCard from 'components/ui/PublicationCard';

import styles from './styles.module.scss';
import { useTranslation } from 'react-i18next';
import Loader from 'components/ui/Loader';

import { getPurchased, getFulfilled, getOrphan, getInProgress } from './filters';

const publicationMapper = p => (<li key={p && p.id}><PublicationCard publication={p} className="mb-16" /></li>);

function PublicationsLayoutSuspense({ publications, loading }) {
  const { t } = useTranslation();

  const purchasedPublications = getPurchased(publications).map(publicationMapper);
  const fulfilledPublications = getFulfilled(publications).map(publicationMapper);
  const orphanPublications = getOrphan(publications).map(publicationMapper);
  const inProgressPublications = getInProgress(publications).map(publicationMapper);

  return (
    <div className="row h100">
      <CardContainer className={`${styles.publicationList} column mr-16`}>
        <h1 className="txt-medium mb-32">{t('my_account.publications.status.purchased')}</h1>
        <ul className="flex-grow">
          {purchasedPublications}
          {loading && publicationMapper(null)}
          {!purchasedPublications.length && <span>{t('my_account.publications.status.empty_list')}</span>}
        </ul>
      </CardContainer>
      <CardContainer className={`${styles.publicationList} column mr-16`}>
        <h1 className="txt-medium mb-32">{t('my_account.publications.status.fulfilled')}</h1>
        <ul className="flex-grow">
          {fulfilledPublications}
          {loading && publicationMapper(null)}
        </ul>
      </CardContainer>
      <CardContainer className={`${styles.publicationList} column`}>
        <h1 className="txt-medium mb-32">{t('my_account.publications.status.in_progress')}</h1>
        <ul className="flex-grow">
          {orphanPublications}
          {inProgressPublications}
          {loading && publicationMapper(null)}
        </ul>
      </CardContainer>
    </div>
  );
}

function PublicationsLayout(props) {
  return (
    <Suspense fallback={<Loader />}>
      <PublicationsLayoutSuspense {...props} />
    </Suspense>
  );
}

export default PublicationsLayout;