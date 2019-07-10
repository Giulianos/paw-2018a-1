import React, { Suspense } from 'react';
import { NavLink } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';
import Loader from 'components/ui/Loader';

function SidebarSuspense() {
  const { t } = useTranslation();

  return (
    <CardContainer className={`${styles.sidebarContainer} column center-alt pt-48`}>
      <h1 className="txt-medium mb-32">{t('my_account.title')}</h1>
      <ul className="column center-alt">
        <li><NavLink activeClassName={styles.active} to='/my-account/publications' className={styles.link}>{t('my_account.publications.title')}</NavLink></li>
        <li><NavLink activeClassName={styles.active} to='/my-account/orders' className={styles.link}>{t('my_account.orders.title')}</NavLink></li>
      </ul>
    </CardContainer>
  );
}

function Sidebar(props) {
  return (
    <Suspense fallback={<Loader />}>
      <SidebarSuspense {...props} />
    </Suspense>
  );
}

export default Sidebar;