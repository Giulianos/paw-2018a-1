import React, { Suspense } from 'react';
import Loader from 'components/ui/Loader';
import { useTranslation } from 'react-i18next';
import Modal from 'components/ui/Modal';

function ReviewModalLayoutSuspense() {
  useTranslation();
  return <Modal>Review modal</Modal>;
}

function ReviewModalLayout(props) {
  return (
    <Suspense fallback={<Loader />}>
      <ReviewModalLayoutSuspense {...props} />
    </Suspense>
  );
}

export default ReviewModalLayout;