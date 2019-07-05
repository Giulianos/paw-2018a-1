import React, { Suspense } from 'react';
import { useTranslation, Trans } from 'react-i18next';

import Modal from 'components/ui/Modal';

import styles from './styles.module.scss';
import Skeleton from './skeleton';
import ProductCard from 'components/ui/ProductCard';
import CardContainer from 'components/ui/CardContainer';
import Button from 'components/ui/Button';
import SmartInput from 'components/ui/SmartInput';

function OrderModalLayout({ data, onClose }) {
  const { t } = useTranslation();
  const totalPrice = data.unitPrice * 10; // TODO: integrate with form
  return (
    <Modal onClose={onClose}>
      <div className={styles.container}>
        <div className="row mb-32"><h1 className="txt-xlarge">{t('order_modal.title')}</h1></div>
        <div className="row">
          <ProductCard className="mr-24" />
          <div className="column space-between">
            <CardContainer className={styles.detContainer}>
              <h2 className="txt-medium mb-16">{t('order_modal.description')}</h2>
              <span className="txt-normal txt-gray3">
                {data.detailedDescription}
              </span>
            </CardContainer>
            <form className="column">
              <div className="row center-alt flex-end mb-24">
                <SmartInput className={styles.shortInput} />
                <span className="ml-16 txt-green txt-bold">{t('order_modal.availability', {count: data.availableQuantity})}</span>
              </div>
              <div className="row center-alt flex-end mb-32 txt-medium20">
                <Trans i18nKey="order_modal.you_will_pay">
                  You will pay <span className="txt-blue txt-bold ml-8">{{ price: `$${totalPrice}` }}</span>
                </Trans>
              </div>
              <div className="row flex-end">
                <Button handleClick={onClose} type="button" variant="secondary" className="mr-8">{t('order_modal.actions.cancel')}</Button>
                <Button>{t('order_modal.actions.order')}</Button>
              </div>
            </form>
          </div>
        </div>
      </div>     
    </Modal>
  );
}

export default props => (<Suspense fallback={<Skeleton />}><OrderModalLayout {...props} /></Suspense>);