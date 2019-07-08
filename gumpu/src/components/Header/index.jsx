import React, { Suspense } from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

import useAuth from 'hooks/useAuth';

import logo from 'assets/logo.svg';
import styles from './styles.module.scss';
import NotificationsMenu from './NotificationsMenu';

function Header() {
  const auth = useAuth();
  const { t } = useTranslation();
  return (
    <div className={styles.headerContainer}>
      <div className={styles.header}>
        <img src={logo} alt="Gumpu" />
        <div className="row center-alt relative">
          <Link to="/publish" className="mr-64 txt-normal">{t('header.publish')}</Link>
          { !auth.logged && <Link to="/create-account" className="txt-normal mr-64">{t('header.create_account')}</Link> }
          { !auth.logged && <Link to="/login" className="txt-normal mr-64">{t('header.login')}</Link> }
          { auth.logged && <Link to="/my-account" className="txt-normal mr-64">{t('header.my_account')}</Link> }
          { auth.logged && <NotificationsMenu>{t('header.notifications')}</NotificationsMenu> }
        </div>
      </div>
    </div>
  );
}

function HeaderLoader() {
  return <div />;
}

export default () => <Suspense fallback={<HeaderLoader />}><Header /></Suspense>;
