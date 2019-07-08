import React, { Suspense } from 'react';
import PropTypes from 'prop-types';

import SmartInput from 'components/ui/SmartInput';
import CardContainer from 'components/ui/CardContainer';
import Button from 'components/ui/Button';

import Loader from 'components/ui/Loader';
import styles from './styles.module.scss';
import Failure from './components/Failure';
import Success from './components/Success';
import { useTranslation } from 'react-i18next';

function CreateAccountLayout({
  name, email, password, handleSubmit, resetRequest, loading, error, success,
}) {
  const { t } = useTranslation();
  return (
    <CardContainer className={`${styles.cardContainer} column center-alt center`}>
      { loading && <Loader /> }
      { error && <Failure handleRetry={resetRequest} /> }
      { success && <Success /> }
      { (!loading && !error && !success) && (
        <form className="w100 column center-alt center" onSubmit={handleSubmit}>
          <SmartInput id="signup-name" label={t('home.register_form.name')} className="mb-24 w100" {...name} />
          <SmartInput id="signup-email" label={t('home.register_form.email')} className="mb-24 w100" {...email} />
          <SmartInput id="signup-pass" label={t('home.register_form.password')} className="mb-32 w100" {...password} type="password" />
          <Button type="submit" className="pl-32 pr-32">{t('home.register_form.action')}</Button>
        </form>
      ) }
    </CardContainer>
  );
}

CreateAccountLayout.propTypes = {
  name: PropTypes.object.isRequired,
  email: PropTypes.object.isRequired,
  password: PropTypes.object.isRequired,
  handleSubmit: PropTypes.func.isRequired,
  resetRequest: PropTypes.func.isRequired,
  loading: PropTypes.bool,
  error: PropTypes.bool,
  success: PropTypes.bool,
};

export default props =><Suspense fallback={<div />}><CreateAccountLayout {...props} /></Suspense>;
