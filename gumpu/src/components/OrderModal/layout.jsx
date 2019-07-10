import React, { Suspense } from 'react';
import { useTranslation, Trans } from 'react-i18next';

import Modal from 'components/ui/Modal';
import ProductCard from 'components/ui/ProductCard';
import CardContainer from 'components/ui/CardContainer';
import Button from 'components/ui/Button';
import SmartInput from 'components/ui/SmartInput';
import Loader from 'components/ui/Loader';

import Failure from './components/Failure';
import Success from './components/Success';
import styles from './styles.module.scss';
import Skeleton from './skeleton';

function OrderModalLayout({ publication, quantity, onClose, onSubmit, onReset, loading, success, error, updatedQuantity, disableOrder }) {
  const { t } = useTranslation();
  const totalPrice = quantity.valid ? publication.unitPrice * Number(quantity.value) : '-';

  const restrictedCloseHandler = () => {
    if(!loading) {
      onClose();
    } 
  }

  return (
    <Modal onClose={restrictedCloseHandler}>
      {loading && (
        <div className="pt-64 pb-64 pl-64 pr-64"><Loader /></div>
      )}
      {error && (
        <Failure buttonMessage={t('order_modal.result_fail.action')} handleRetry={onReset}>
          <Trans i18nKey="order_modal.result_fail.message">
            <span className="txt-red txt-medium txt-bold mr-8">Oops!</span>
            <span className="txt-medium txt-gray3">We couldn't take your order</span>
          </Trans>
        </Failure>
      )}
      {success && (
        <Success buttonMessage={t('order_modal.result_success.action')} handleAccept={onClose}>
          <Trans i18nKey="order_modal.result_success.message">
            <span className="txt-green txt-medium txt-bold mr-8">Great!</span>
            <span className="txt-medium txt-gray3">Your order was made successfully!</span>
          </Trans>
        </Success>
      )}
      {!error && !success && !loading &&
        <div className={styles.container}>
          <div className="row mb-32"><h1 className="txt-xlarge">{t('order_modal.title')}</h1></div>
          <div className="row">
            <ProductCard product={publication} className="mr-24" />
            <div className="column space-between">
              <CardContainer className={styles.detContainer}>
                <h2 className="txt-medium mb-16">{t('order_modal.description')}</h2>
                <span className="txt-normal txt-gray3">
                  {publication.detailedDescription}
                </span>
              </CardContainer>
              {!disableOrder && (
                <form onSubmit={onSubmit} className="column">
                  <div className="row flex-end-alt flex-end mb-24">
                    <SmartInput label={t('order_modal.quantity')} {...quantity} className={styles.shortInput} />
                    { !updatedQuantity && <span className="ml-16 txt-gray2 txt-bold">{t('order_modal.availability', {count: publication.availableQuantity})}</span> }
                    { updatedQuantity && <span className="ml-16 txt-green txt-bold">{t('order_modal.availability', {count: updatedQuantity})}</span> }
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
              )}
              {disableOrder && (
                <div className="txt-gray2 flex-grow column center center-alt">{t('order_modal.login_hint')}</div>
              )}
            </div>
          </div>
        </div> 
      }    
    </Modal>
  );
}

export default props => (<Suspense fallback={<Skeleton />}><OrderModalLayout {...props} /></Suspense>);