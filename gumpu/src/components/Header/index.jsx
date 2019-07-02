import React from 'react';
import { Link } from 'react-router-dom';

import useAuth from 'hooks/useAuth';

import logo from 'assets/logo.svg';
import styles from './styles.module.scss';

function Header() {
  const auth = useAuth();
  return (
    <div className={styles.headerContainer}>
      <div className={styles.header}>
        <img src={logo} alt="Gumpu" />
        <div>
          <Link to="/" className="mr-64 txt-normal">Categories</Link>
          <Link to="/" className="mr-64 txt-normal">Publish</Link>
          { !auth.logged && <Link to="/login">Login</Link> }
        </div>
      </div>
    </div>
  );
}

export default Header;
