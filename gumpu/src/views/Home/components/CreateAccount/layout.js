import React from 'react';

import SmartInput from 'components/ui/SmartInput';
import CardContainer from 'components/ui/CardContainer';
import Button from 'components/ui/Button';

import styles from './styles.module.scss';

function CreateAccountLayout({ name, email, password }) {
  return (
    <CardContainer className={`${styles.cardContainer} column center-alt`}>
      <SmartInput label="Name" className="mb-24 w100" {...name} />
      <SmartInput label="Email" className="mb-24 w100" {...email} />
      <SmartInput label="New password" className="mb-32 w100" {...password} type="password" />
      <Button className="pl-32 pr-32">CREATE ACCOUNT</Button>
    </CardContainer>
  );
}

export default CreateAccountLayout;