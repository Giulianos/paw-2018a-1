import React, { Suspense } from 'react';
import { useTranslation, Trans } from 'react-i18next';

import successIcon from 'assets/result_indicator/ok.svg';
import Button from 'components/ui/Button';
import Loader from 'components/ui/Loader';

function SuccessSuspense({ handleOk }) {
  const { t } = useTranslation();
  return (
    <div className="h100 w100 column center center-alt animated fadeIn">
      <img alt="" className="mb-48" src={successIcon} />
      <span className="mb-48">
        <Trans i18nKey="add_image.result_success.message">
          <span className="txt-green txt-medium txt-bold mr-8">Oops!</span>
          <span className="txt-medium txt-gray3">Invalid credentials</span>
        </Trans>
      </span>
      <Button type="button" color="green" variant="secondary" handleClick={handleOk}>{ t('add_image.result_success.action') }</Button>
    </div>
  );
}

function Success(props) {
  return (
    <Suspense fallback={<Loader />}>
      <SuccessSuspense {...props} />
    </Suspense>
  );
}

export default Success;
