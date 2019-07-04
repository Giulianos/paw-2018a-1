import React, {Suspense} from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';

import useFormInput from 'hooks/useFormInput';
import Loader from 'components/ui/Loader';
import quantity from 'validators/quantity';
import price from 'validators/price';
import minMaxString from 'validators/minMaxString';
import maxString from 'validators/maxString';
import tagList from 'validators/tagList';
import { createPublication } from 'redux/publication/actionCreators';

import PublishLayout from './layout';
import serializePublication from './serializer';

function Publish() {

  const { t } = useTranslation();
  const dispatch = useDispatch();
  const publicationCreateState = useSelector(state => state.publication.create);

  const form = {
    description: useFormInput('', minMaxString(3, 30, t('publish.step1.form.description_error', { min: 3, max: 30 }))),
    unitPrice: useFormInput('', price('')),
    quantity: useFormInput('', quantity('')),
    detailedDescription: useFormInput('', maxString('', 1000)),
    tags: useFormInput('', tagList(3, t('publish.step2.form.tags_error', { min: 3 })))
  };

  const handleSubmit = e => {
    e.preventDefault();
    const values = {
      description: form.description.value,
      unitPrice: form.unitPrice.value,
      quantity: form.quantity.value,
      detailedDescription: form.detailedDescription.value,
      tags: form.tags.value
    }
    dispatch(createPublication(serializePublication(values)))
  }

  return <PublishLayout handleSubmit={handleSubmit} {...form} />;
}

export default () => <Suspense fallback={<Loader />}><Publish /></Suspense>;