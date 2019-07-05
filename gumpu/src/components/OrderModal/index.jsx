import React from 'react';
import { useDispatch, useSelector } from 'react-redux';

import useFormInput from 'hooks/useFormInput';
import quantityMax from 'validators/quantityMax';
import { orderPublication, resetOrderPublication } from 'redux/publication/actionCreators';

import OrderModalLayout from './layout';

function OrderModal({ shown, onClose, selectedPublication }) {
  const dispatch = useDispatch();
  const orderState = useSelector(state => state.publication.order.create);

  const form = {
    quantity: useFormInput(1, quantityMax(selectedPublication && selectedPublication.availableQuantity, ''))
  };

  const submitHandler = e => {
    e.preventDefault();

    if(form.quantity.valid) {
      console.log(`Order ${form.quantity.value} of pub:${selectedPublication.id}`);
      dispatch(orderPublication(selectedPublication.id, Number(form.quantity.value)));
    }
  }

  const closeHandler = () => {
    dispatch(resetOrderPublication());
    form.quantity.reset();
    if(onClose) {
      onClose();
    }
  }

  const resetHandler = () => {
    dispatch(resetOrderPublication());
  }

  return shown && <OrderModalLayout {...orderState} {...form} onReset={resetHandler} onClose={closeHandler} onSubmit={submitHandler} publication={selectedPublication} />;
}

export default OrderModal;
