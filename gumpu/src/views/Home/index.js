import React from 'react';
import { Link } from 'react-router-dom';

import CardContainer from 'components/ui/CardContainer';
import Button from 'components/ui/Button';

import styles from './styles.module.scss';

function Home() {
  return (
    <div>
      <h1>This is the Home screen.</h1>
      <CardContainer className={styles.customCard}>This is the card container</CardContainer>
      <Button>PRIMARY</Button>
      <Button color="red">PRIMARY</Button>
      <Button color="yellow">PRIMARY</Button>
      <Button color="green">PRIMARY</Button>
      <br/>
      <br/>
      <Button variant="secondary">SECONDARY</Button>
      <Button variant="secondary" color="red">SECONDARY</Button>
      <Button variant="secondary" color="yellow">SECONDARY</Button>
      <Button variant="secondary" color="green">SECONDARY</Button>
      <Link to="/secondary">Go to secondary...</Link>
    </div>
  );
}

export default Home;