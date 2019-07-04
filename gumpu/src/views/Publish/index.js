import React, {Suspense} from 'react';
import { useTranslation } from 'react-i18next';

import useFormInput from 'hooks/useFormInput';
import Loader from 'components/ui/Loader';
import quantity from 'validators/quantity';
import price from 'validators/price';
import minMaxString from 'validators/minMaxString';
import maxString from 'validators/maxString';
import tagList from 'validators/tagList';

import PublishLayout from './layout';

function Publish() {

  const { t } = useTranslation();

  const form = {
    description: useFormInput('', minMaxString(3, 30, t('publish.step1.form.description_error', { min: 3, max: 30 }))),
    unitPrice: useFormInput('', price('')),
    quantity: useFormInput('', quantity('')),
    detailedDescription: useFormInput('', maxString('', 1000)),
    tags: useFormInput('', tagList(3, t('publish.step2.form.tags_error', { min: 3 })))
  };

  const handleSubmit = e => {
    e.preventDefault();
    console.log(form.description.value);
    console.log(form.unitPrice.value);
    console.log(form.detailedDescription.value);
    console.log(form.tags.value);
  }

  return <PublishLayout handleSubmit={handleSubmit} {...form} />;
}

export default () => <Suspense fallback={<Loader />}><Publish /></Suspense>;