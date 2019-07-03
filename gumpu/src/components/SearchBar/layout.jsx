import React from 'react';
import PropTypes from 'prop-types';

import Input from 'components/ui/Input';
import Button from 'components/ui/Button';
import styles from './styles.module.scss';

function SearchBarLayout({ handleSubmit, term }) {
  return (
    <div className="column center-alt w100">
      <h2 className="txt-large txt-gray3 mb-48">What are you looking for?</h2>
      <form onSubmit={handleSubmit} className={styles.searchBox}>
        <Input {...term} id="search" placeholder="E.g. soda" className={`mr-8 ${styles.searchInput}`} />
        <Button type="submit">SEARCH</Button>
      </form>
    </div>
  );
}

SearchBarLayout.propTyeps = {
  handleSubmit: PropTypes.func.isRequired,
};

export default SearchBarLayout;
