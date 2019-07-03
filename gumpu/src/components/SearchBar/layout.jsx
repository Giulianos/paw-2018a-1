import React, { Suspense } from 'react';
import PropTypes from 'prop-types';
import { useTranslation } from 'react-i18next';

import Input from 'components/ui/Input';
import Button from 'components/ui/Button';
import styles from './styles.module.scss';

function SearchBarLayout({ handleSubmit, term }) {
  const { t } = useTranslation();

  return (
    <div className="column center-alt w100">
      <h2 className="txt-large txt-gray3 mb-48">{t('search_bar.question')}</h2>
      <form onSubmit={handleSubmit} className={styles.searchBox}>
        <Input {...term} id="search" placeholder={t('search_bar.placeholder')} className={`mr-8 ${styles.searchInput}`} />
        <Button type="submit">{t('search_bar.action')}</Button>
      </form>
    </div>
  );
}

SearchBarLayout.propTyeps = {
  handleSubmit: PropTypes.func.isRequired,
};

function Loader() {
  return <div />;
}

export default props => <Suspense fallback={<Loader />}><SearchBarLayout {...props} /></Suspense>;
