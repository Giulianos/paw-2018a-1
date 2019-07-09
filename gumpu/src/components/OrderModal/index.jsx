import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import useFormInput from 'hooks/useFormInput';
import quantityMax from 'validators/quantityMax';
import { orderPublication, resetOrderPublication, retrieve, resetRetrieve } from 'redux/publication/actionCreators';

import OrderModalLayout from './layout';

function OrderModal({ shown, onClose, selectedPublication }) {
  const dispatch = useDispatch();
  const orderState = useSelector(state => state.publication.order.create);
  const retrieved = useSelector(state => state.publication.retrieve);

  useEffect(() => {
    if(selectedPublication)
      dispatch(retrieve(selectedPublication.id));
  }, [selectedPublication]);

  const form = {
    quantity: useFormInput(1, quantityMax(selectedPublication && selectedPublication.availableQuantity, ''))
  };

  const submitHandler = e => {
    e.preventDefault();

    if(form.quantity.valid) {
      dispatch(orderPublication(selectedPublication.id, Number(form.quantity.value)));
    }
  }

  const closeHandler = () => {
    dispatch(resetOrderPublication());
    dispatch(resetRetrieve());
    form.quantity.reset();
    if(onClose) {
      onClose();
    }
  }

  const resetHandler = () => {
    dispatch(resetOrderPublication());
  }

  return shown && <OrderModalLayout {...orderState} {...form} updatedQuantity={retrieved.publication && retrieved.publication.availableQuantity} onReset={resetHandler} onClose={closeHandler} onSubmit={submitHandler} publication={selectedPublication} />;
}

export default OrderModal;
