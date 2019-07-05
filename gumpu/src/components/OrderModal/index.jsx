import React, { useEffect } from 'react';

import useFormInput from 'hooks/useFormInput';
import quantityMax from 'validators/quantityMax';

import OrderModalLayout from './layout';

function OrderModal({ shown, onClose, selectedPublication }) {
  const form = {
    quantity: useFormInput(1, quantityMax(selectedPublication && selectedPublication.availableQuantity, ''))
  };

  useEffect(() => {
    form.quantity.reset();
  }, [selectedPublication]);

  const submitHandler = e => {
    e.preventDefault();

    if(form.quantity.valid) {
      console.log(`Order ${form.quantity.value} of pub:${selectedPublication.id}`);
    }
  }

  return shown && <OrderModalLayout {...form} onClose={onClose} onSubmit={submitHandler} data={selectedPublication} />;
}

export default OrderModal;
