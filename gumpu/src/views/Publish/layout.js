import React, { Suspense } from 'react';
import { useTranslation } from 'react-i18next';

import Loader from 'components/ui/Loader';
import CardContainer from 'components/ui/CardContainer';
import SmartInput from 'components/ui/SmartInput';

import styles from './styles.module.scss';
import Step from './components/Step';
import SmartTextArea from 'components/ui/SmartTextArea';
import Button from 'components/ui/Button';

function PublishLayout({ quantity, unitPrice, description, detailedDescription, tags, handleSubmit }) {
  const { t } = useTranslation();

  return (
    <div className="view-container column center-alt">
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
    </div>
  );
}

export default props => (
  <Suspense fallback={<Loader />}><PublishLayout {...props} /></Suspense>
);
