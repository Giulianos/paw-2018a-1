import React, { Suspense } from 'react';
import { useTranslation, Trans } from 'react-i18next';

import Loader from 'components/ui/Loader';
import CardContainer from 'components/ui/CardContainer';
import SmartInput from 'components/ui/SmartInput';
import Button from 'components/ui/Button';
import SmartTextArea from 'components/ui/SmartTextArea';

import Step from './components/Step';
import Success from './components/Success';
import Failure from './components/Failure';

import styles from './styles.module.scss';

function PublishLayout({ quantity, unitPrice, description, detailedDescription, tags, handleSubmit, handleRetry, handleAccept, handleAddImage, loading, error, success }) {
  const { t } = useTranslation();

  return (
    <div className="view-container column center-alt">
      {loading && (
        <Loader />
      )}
      {error && (
        <Failure buttonMessage={t('publish.result_fail.action')} handleRetry={handleRetry}>
          <Trans i18nKey="publish.result_fail.message">
            <span className="txt-red txt-medium txt-bold mr-8">Oops!</span>
            <span className="txt-medium txt-gray3">Invalid credentials</span>
          </Trans>
        </Failure>
      )}
      {success && (
        <Success acceptMessage={t('publish.result_success.action')} addImageMessage={t('publish.result_success.action_add')} handleAccept={handleAccept} handleAddImage={handleAddImage}>
          <Trans i18nKey="publish.result_success.message">
            <span className="txt-green txt-medium txt-bold mr-8">Genial!</span>
            <span className="txt-medium txt-gray3">Tu producto ya fue publicado!</span>
          </Trans>
        </Success>
      )}
      {!error && !success && !loading &&
      <form onSubmit={handleSubmit} className="w100">
        <div className="w100 row space-between pl-64 pr-64 flex-start-alt mb-32">
          <Step text={t('publish.step1.title')} moreInfo="" number="1" />
          <CardContainer className={`${styles.stepCard} pb-48 pt-48 pl-32 pr-32`}>
            <SmartInput {...description} placeholder={t('publish.step1.form.description_placeholder')} className="mb-24" label={t('publish.step1.form.description')} />
            <SmartInput {...unitPrice} className={`${styles.smallInput} mb-24`} label={t('publish.step1.form.unit_price')} />
            <SmartInput {...quantity} className={styles.smallInput} label={t('publish.step1.form.total_quantity')} />
          </CardContainer>
        </div>
        <div className="w100 row space-between pl-64 pr-64 flex-start-alt">
          <Step text={t('publish.step2.title')} moreInfo="" number="2" />
          <div className={`${styles.stepCard} column center-alt`}>
            <CardContainer className="w100 pb-48 pt-48 pl-32 pr-32 mb-48">
              <SmartTextArea {...detailedDescription} className="mb-24" label={t('publish.step2.form.detailed_description')} maxLength={1000} />
              <SmartInput placeholder={t('publish.step2.form.tags_placeholder')} {...tags} label={t('publish.step2.form.tags')} />
            </CardContainer>
            <Button type="submit">{t('publish.action')}</Button>
          </div>
        </div>
      </form>
      }
    </div>
  );
}

export default props => (
  <Suspense fallback={<Loader />}><PublishLayout {...props} /></Suspense>
);
