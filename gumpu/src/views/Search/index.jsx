import React, { Suspense } from 'react';
import ProductCard from 'components/ui/ProductCard';

import styles from './styles.module.scss';
import { useTranslation } from 'react-i18next';
import Loader from 'components/ui/Loader';
import SearchBar from 'components/SearchBar';

function SearchSuspense({ match }) {
  const searchTerm = match.params.term;
  const { t } = useTranslation();

  return (
    <div className="view-container column center-alt">
      <div className={styles.searchResults}>
        <div className="w100 mb-32">
          <SearchBar initialValue={searchTerm} hideText className="mb-32" />
        </div>
        <h1 className="txt-large mb-24 txt-gray3 w100">{t('search.title', { term: searchTerm })}</h1>
        <ProductCard className={styles.product} />
        <ProductCard className={styles.product} />
        <ProductCard className={styles.product} />
        <ProductCard className={styles.product} />
        <ProductCard className={styles.product} />
        <ProductCard className={styles.product} />
        <ProductCard className={styles.product} />
      </div>
    </div>
  );
}

function Search(props) {
  return (
    <Suspense fallback={<Loader />}>
      <SearchSuspense {...props} />
    </Suspense>
  )
}

export default Search;
