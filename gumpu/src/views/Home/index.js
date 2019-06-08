import React, { useState } from 'react';
import { Link } from 'react-router-dom';

import CardContainer from 'components/ui/CardContainer';
import Button from 'components/ui/Button';
import Input from 'components/ui/Input';

import styles from './styles.module.scss';

function Home() {
  const [name, setName] = useState('John Doe');

  const handleChangeName = e => setName(e.target.value);

  return (
    <div>
      <h1>This is the Home screen.</h1>
      <CardContainer className={styles.customCard}>
        <h2 className="txt-xlarge mb-48">Inputs</h2>
        <label className="txt-input-label">Name</label>
        <Input className="mb-24" value={name} onChange={handleChangeName} />
        <label className="txt-input-label">Surname</label>
        <Input className="mb-24" variant="invalid" value={name} onChange={handleChangeName} />
        <label className="txt-input-label">Email</label>
        <Input className="mb-24" variant="valid" value={name} onChange={handleChangeName} />
      </CardContainer>

      <CardContainer className={styles.customCard}>
        <h2 className="txt-xlarge mb-48">Buttons</h2>
        <Button className="mb-8">PRIMARY</Button>
        <Button className="mb-8" color="red">PRIMARY</Button>
        <Button className="mb-8" color="yellow">PRIMARY</Button>
        <Button className="mb-16" color="green">PRIMARY</Button>

        <Button className="mb-8" variant="secondary">SECONDARY</Button>
        <Button className="mb-8" variant="secondary" color="red">SECONDARY</Button>
        <Button className="mb-8" variant="secondary" color="yellow">SECONDARY</Button>
        <Button className="mb-8" variant="secondary" color="green">SECONDARY</Button>
      </CardContainer>
      <Link to="/secondary">Go to secondary...</Link>
    </div>
  );
}

export default Home;