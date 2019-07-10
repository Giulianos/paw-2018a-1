import React, { Suspense, useState } from 'react';
import Loader from 'components/ui/Loader';
import { useTranslation } from 'react-i18next';
import Modal from 'components/ui/Modal';

import styles from './styles.module.scss';
import SmartTextArea from 'components/ui/SmartTextArea';
import SmartInput from 'components/ui/SmartInput';
import CardContainer from 'components/ui/CardContainer';
import Button from 'components/ui/Button';

function ReviewModalLayoutSuspense({onReview, reviewForm}) {
  const { t } = useTranslation();

  return (
    <Modal>
      <div class={styles.container}>
        <h1 className="txt-xlarge mb-32">{t('my_account.orders.confirm_modal.title')}</h1>
        <CardContainer className={styles.reviewContainer}>
          <form onSubmit={onReview}>
            <span className="txt-medium20 txt-gray2">{t('my_account.orders.confirm_modal.leave_review.hint')}</span>
            <SmartTextArea {...reviewForm.comment} className="mt-24 mb-16" label={t('my_account.orders.confirm_modal.leave_review.comment_label')} />
            <div class="row flex-end-alt mb-24">
              <SmartInput {...reviewForm.rating} label={t('my_account.orders.confirm_modal.leave_review.rating_label')} className={styles.shortInput} placeholder="5"/>
              <span className={`${styles.ratingDescription} txt-small txt-gray2`}>{t('my_account.orders.confirm_modal.leave_review.rating_description')}</span>
            </div>
            <div className="row w100 flex-end">
              <Button variant="green">{t('my_account.orders.confirm_modal.leave_review.action')}</Button>
            </div>
          </form>
        </CardContainer>
        <div class="row w100 space-between center-alt">
          <span className={`${styles.ratingDescription} txt-normal txt-gray3`}>{t('my_account.orders.confirm_modal.leave_review.no_thanks')}</span>
          <Button>{t('my_account.orders.confirm_modal.action')}</Button>
        </div>
      </div>
    </Modal>
  );
}

function ReviewModalLayout(props) {
  return (
    <Suspense fallback={<Loader />}>
      <ReviewModalLayoutSuspense {...props} />
    </Suspense>
  );
}

export default ReviewModalLayout;