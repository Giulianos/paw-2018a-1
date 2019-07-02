import React from 'react';

import styles from './styles.module.scss';
import Input from 'components/ui/Input';
import Button from 'components/ui/Button';

function SearchBar() {
  return (
    <div className={`column center-alt w100`}>
      <h2 className="txt-large txt-gray3 mb-48">What are you looking for?</h2>
      <div className={styles.searchBox}>
        <Input id="search" placeholder="E.g. soda" className={`mr-8 ${styles.searchInput}`} />
        <Button>SEARCH</Button>
      </div>
    </div>
  );
}

export default SearchBar;