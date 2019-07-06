import React from 'react';
import { NavLink } from 'react-router-dom';

import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';

function Sidebar() {
  return (
    <CardContainer className={`${styles.sidebarContainer} column center-alt pt-48`}>
      <h1 className="txt-medium mb-32">My Account</h1>
      <ul className="column center-alt">
        <li><NavLink activeClassName={styles.active} exact to='/my-account' className={styles.link}>Summary</NavLink></li>
        <li><NavLink activeClassName={styles.active} to='/my-account/publications' className={styles.link}>Publications</NavLink></li>
        <li><NavLink activeClassName={styles.active} to='/my-account/orders' className={styles.link}>Orders</NavLink></li>
      </ul>
    </CardContainer>
  );
}

export default Sidebar;