import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import useFormInput from 'hooks/useFormInput';
import quantityMax from 'validators/quantityMax';
import {
  confirm as confirmAction,
  rate as rateAction,
  resetConfirm as resetConfirmAction,
  resetRate as resetRateAction,
  resetListOrders,
  listOrders
} from 'redux/orders/actionCreators';

import ReviewModalLayout from './layout';
import useAuth from 'hooks/useAuth';
import history from 'router/history';

function ReviewModal({ match }) {
  const orderId = match.params.ord_id;
  const auth = useAuth();
  const dispatch = useDispatch();
  const rate = useSelector(state => state.orders.rate);
  const confirm = useSelector(state => state.orders.confirm);
  useEffect(() => {
    dispatch(resetConfirmAction());
    dispatch(resetRateAction());
  }, []);

  const reviewForm = {
    comment: useFormInput(''),
    rating: useFormInput('', quantityMax(5, ''))
  }

  const reloadData = () => {
    dispatch(resetListOrders());
    dispatch(listOrders(auth.user.id, 0, 30));
  }

  const handleReview = e => {
    e.preventDefault();
    if(reviewForm.rating.valid === true) {
      dispatch(rateAction(auth.user.id, orderId, reviewForm.comment.value, reviewForm.rating.value, reloadData));
    }
  }

  const handleConfirm = () => {
    dispatch(confirmAction(auth.user.id, orderId));
  }

  const close = () => {
    history.replace('/my-account/orders')
  }

  const formState = {
    loading: rate.loading || confirm.loading,
    error: rate.error || confirm.error,
    success: rate.success || confirm.success
  };

  return <ReviewModalLayout onClose={close} reviewForm={reviewForm} onReview={handleReview} onConfirm={handleConfirm} {...formState} />;
}

export default ReviewModal;