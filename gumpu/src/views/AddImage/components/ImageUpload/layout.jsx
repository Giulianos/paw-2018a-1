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

function AddImageLayoutSuspense({
  handleUpload, loading, error, success, base64State
}) {
  const { t } = useTranslation();

  return (
    <CardContainer className={`${styles.cardContainer} column center-alt center`}>
      { loading && <Loader /> }
      { error && <Failure /> }
      { success && <Success /> }
      { (!loading && !error && !success) && (
        <>
          <h1 className="txt-large mb-64 txt-gray3">{t('add_image.title')}</h1>
          <ImageSelector b64Field={base64State} />
          <Button className="mt-24" handleClick={handleUpload}>{t('add_image.action')}</Button>
        </>
      ) }
    </CardContainer>
  );
}

function AddImageLayout(props) {
  return (
    <Suspense fallback={<Loader />}>
      <AddImageLayoutSuspense {...props} />
    </Suspense>
  );
}

export default AddImageLayout;
