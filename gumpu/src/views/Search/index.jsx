import React, { Suspense, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { Trans, useTranslation } from 'react-i18next';

import ProductCard from 'components/ui/ProductCard';
import Button from 'components/ui/Button';
import { search as newSearch, resetSearch as newResetSearch } from 'redux/publication/actionCreators';

import styles from './styles.module.scss';
import Loader from 'components/ui/Loader';
import SearchBar from 'components/SearchBar';

const LOADED_QUANTITY = 1;

function SearchSuspense({ match }) {
  const searchTerm = match.params.term;
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const search = useSelector(state => state.publication.search);
  const loadMore = () => {
    if(search.nextPage && !search.loading) {
      dispatch(newSearch(searchTerm, search.nextPage, LOADED_QUANTITY));
    } 
  }
  useEffect(() => {
    dispatch(newResetSearch());
    dispatch(newSearch(searchTerm, search.nextPage, LOADED_QUANTITY));
  }, [searchTerm])

  return (
    <div className="view-container column center-alt">
      <div className={styles.searchResults}>
        <div className="w100 mb-32">
          <SearchBar initialValue={searchTerm} hideText className="mb-32" />
        </div>
        <h1 className="txt-large mb-24 txt-gray3 w100">{t('search.title', { term: searchTerm })}</h1>
        { search.results.map(p => <ProductCard key={p.id} product={p} className={styles.product} />) }
        { search.results.length !== 0 && search.nextPage && !search.loading && (
          <div className="w100 row center">
            <Button handleClick={loadMore}>{t('search.load_more')}</Button>
          </div>
        )}
        { search.results.length === 0 && !search.loading && (
          <div className="w100 mt-64 column center-alt txt-medium txt-gray2">
            <span className="mb-16">{t('search.empty_state.description')}</span>
            <span className="txt-gray3">
              <Trans i18nKey="search.empty_state.action">
                Por que no <Link className="txt-blue" to="/publish">creas una publicacion</Link>?
              </Trans>
            </span>
          </div>
        )}
        { search.loading && (
          <>
            <ProductCard className={styles.product} />
            <ProductCard className={styles.product} />
            <ProductCard className={styles.product} />
            <ProductCard className={styles.product} />
          </>
        )}
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
