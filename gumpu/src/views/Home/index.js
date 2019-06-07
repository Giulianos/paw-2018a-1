import React from 'react';
import { Link } from 'react-router-dom';

import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';

function Home() {
  return (
    <div>
      <h1>This is the Home screen.</h1>
      <CardContainer className={styles.customCard}>This is the card container</CardContainer>
      <Link to="/secondary">Go to secondary...</Link>
    </div>
  );
}

export default Home;