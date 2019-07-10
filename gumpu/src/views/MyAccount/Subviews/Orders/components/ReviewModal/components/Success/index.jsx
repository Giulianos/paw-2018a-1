import React, { Suspense } from 'react';
import { useTranslation, Trans } from 'react-i18next';

import successIcon from 'assets/result_indicator/ok.svg';
import Loader from 'components/ui/Loader';
import Button from 'components/ui/Button';

function SuccessSuspense({ handleOk }) {
  const { t } = useTranslation();

  return (
    <div className="h100 w100 column center center-alt animated fadeIn">
      <img alt="" className="mb-48" src={successIcon} />
      <span className="mb-48">
        <Trans i18nKey="my_account.orders.confirm_modal.result_success.message">
          <span className="txt-green txt-medium txt-bold mr-8">Yay!</span>
          <span className="txt-medium txt-gray3">Your account has been created!</span>
        </Trans>
      </span>
      <Button type="button" color="green" variant="secondary" handleClick={handleOk}>{ t('my_account.orders.confirm_modal.result_success.action') }</Button>
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
