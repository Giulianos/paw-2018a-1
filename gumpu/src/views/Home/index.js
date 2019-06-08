import React from 'react';
import { Link } from 'react-router-dom';

import CardContainer from 'components/ui/CardContainer';
import Button from 'components/ui/Button';
import SmartInput from 'components/ui/SmartInput';

import useFormInput from 'hooks/useFormInput';

import styles from './styles.module.scss';

function Home() {
  
  const name = useFormInput('', v => !!v && v.length > 3);
  const surname = useFormInput('');
  const email = useFormInput('', v => !!v);

  return (
    <div>
      <h1>This is the Home screen.</h1>
      <CardContainer className={styles.customCard}>
        <h2 className="txt-xlarge mb-48">Inputs</h2>
        <SmartInput label="Name" className="mb-24" {...name} />
        <SmartInput label="Surname" className="mb-24" {...surname} />
        <SmartInput label="Email" className="mb-24" {...email} />
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