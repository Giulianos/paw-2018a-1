import React from 'react';
import PropTypes from 'prop-types';

import SmartInput from 'components/ui/SmartInput';
import CardContainer from 'components/ui/CardContainer';
import Button from 'components/ui/Button';

import Loader from 'components/ui/Loader';
import styles from './styles.module.scss';
import Failure from './components/Failure';
import Success from './components/Success';

function LoginFormLayout({
  email, password, handleSubmit, resetRequest, loading, error, success,
}) {
  return (
    <CardContainer className={`${styles.cardContainer} column center-alt center`}>
      { loading && <Loader /> }
      { error && <Failure handleRetry={resetRequest} /> }
      { success && <Success /> }
      { (!loading && !error && !success) && (
        <form className="w100 column center-alt center" onSubmit={handleSubmit}>
          <SmartInput id="email" label="Email" className="mb-24 w100" {...email} />
          <SmartInput id="password" label="Password" className="mb-32 w100" {...password} type="password" />
          <Button type="submit" className="pl-32 pr-32">LOGIN</Button>
        </form>
      ) }
    </CardContainer>
  );
}

LoginFormLayout.propTypes = {
  email: PropTypes.object.isRequired,
  password: PropTypes.object.isRequired,
  handleSubmit: PropTypes.func.isRequired,
  resetRequest: PropTypes.func.isRequired,
  loading: PropTypes.bool,
  error: PropTypes.bool,
  success: PropTypes.bool,
};

export default LoginFormLayout;
