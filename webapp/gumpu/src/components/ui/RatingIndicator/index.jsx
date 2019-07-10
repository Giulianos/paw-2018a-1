import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import RatingIndicatorLayout from './layout';
import { rating as ratingAction } from 'redux/user/actionCreators';

function RatingIndicator({ userId }) {
  const dispatch = useDispatch();
  const rating = useSelector(state => userId && state.user.rating[userId]);
  useEffect(() => {
    console.log(`rating indicator for: ${userId}`)
    if(userId && !rating) {
      dispatch(ratingAction(userId));
    }
  }, [userId]);
  console.log(rating)

  return <RatingIndicatorLayout value={rating && rating.data}  />
}

export default RatingIndicator;