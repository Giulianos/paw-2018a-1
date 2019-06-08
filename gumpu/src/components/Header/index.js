import React from 'react';
import { Link } from 'react-router-dom';

import styles from './styles.module.scss';
import logo from 'assets/logo.svg';

function Header() {
  return (
    <div className={styles.headerContainer}>
      <div className={styles.header}>
        <img src={logo} alt="Gumpu" />
        <div>
          <Link to="/" className="mr-64 txt-normal">Categories</Link>
          <Link to="/" className="mr-64 txt-normal">Publish</Link>
          <Link to="/">Login</Link>
        </div>
      </div>
    </div>
  );
}

export default Header;