import React, { Suspense } from 'react';
import PropTypes from 'prop-types';
import { Trans, useTranslation } from 'react-i18next';

import failureIcon from 'assets/result_indicator/fail.svg';
import Button from 'components/ui/Button';
import Loader from 'components/ui/Loader';

function FailureSuspense({ handleRetry }) {
  const { t } = useTranslation();

  return (
    <div className="h100 w100 column center center-alt animated fadeIn">
      <img alt="" className="mb-48" src={failureIcon} />
      <span className="mb-48">
        <Trans i18nKey="add_image.result_fail.message">
          <span className="txt-red txt-medium txt-bold mr-8">Oops!</span>
          <span className="txt-medium txt-gray3">Invalid credentials</span>
        </Trans>
      </span>
      <Button type="button" color="red" variant="secondary" handleClick={handleRetry}>{ t('add_image.result_fail.action') }</Button>
    </div>
  );
}

function Failure(props) {
  return (
    <Suspense fallback={<Loader />}>
      <FailureSuspense {...props} />
    </Suspense>
  );
}

FailureSuspense.propTypes = {
  handleRetry: PropTypes.func.isRequired,
};

export default Failure;
