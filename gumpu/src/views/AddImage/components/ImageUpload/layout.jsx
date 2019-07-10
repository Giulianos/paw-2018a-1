import React, { Suspense } from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

import SmartInput from 'components/ui/SmartInput';
import CardContainer from 'components/ui/CardContainer';
import Button from 'components/ui/Button';
import Loader from 'components/ui/Loader';

import styles from './styles.module.scss';
import Failure from './components/Failure';
import Success from './components/Success';
import ImageSelector from './components/ImageSelector';
import { Trans, useTranslation } from 'react-i18next';

function LoginFormLayout({
  email, password, handleSubmit, resetRequest, loading, error, success
}) {
  const { t } = useTranslation();

  return (
    <CardContainer className={`${styles.cardContainer} column center-alt center`}>
      { loading && <Loader /> }
      { error && <Failure handleRetry={resetRequest} /> }
      { success && <Success /> }
      { (!loading && !error && !success) && (
        <>
          <h1 className="txt-large mb-64 txt-gray3">{t('add_image.title')}</h1>
          <ImageSelector />
        </>
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

export default props => <Suspense fallback={<div />}><LoginFormLayout {...props} /></Suspense>;
