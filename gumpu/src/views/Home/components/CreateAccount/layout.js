import React from 'react';
import PropTypes from 'prop-types';

import SmartInput from 'components/ui/SmartInput';
import CardContainer from 'components/ui/CardContainer';
import Button from 'components/ui/Button';

import styles from './styles.module.scss';
import Loader from 'components/ui/Loader';
import Failure from './components/Failure';
import Success from './components/Success';

function CreateAccountLayout({ name, email, password, handleSubmit, loading, error, success }) {
  return (
    <CardContainer className={`${styles.cardContainer} column center-alt center`}>
      { loading && <Loader /> }
      { error && <Failure /> }
      { success && <Success /> }
      { (!loading && !error && !success) && (
        <>
          <SmartInput label="Name" className="mb-24 w100" {...name} />
          <SmartInput label="Email" className="mb-24 w100" {...email} />
          <SmartInput label="New password" className="mb-32 w100" {...password} type="password" />
          <Button handleClick={handleSubmit} className="pl-32 pr-32">CREATE ACCOUNT</Button>
        </>
      ) }
    </CardContainer>
  );
}

CreateAccountLayout.propTypes = {
  name: PropTypes.object,
  email: PropTypes.object,
  passwor: PropTypes.object,
  handleSubmit: PropTypes.func,
  loading: PropTypes.bool,
  error: PropTypes.bool,
  success: PropTypes.bool
};

export default CreateAccountLayout;