import React from 'react';
import ReviewModalLayout from './layout';
import useFormInput from 'hooks/useFormInput';
import quantityMax from 'validators/quantityMax';

function ReviewModal() {

  const reviewForm = {
    comment: useFormInput(''),
    rating: useFormInput('', quantityMax(5, ''))
  }

  const handleReview = e => {
    e.preventDefault();
    if(reviewForm.rating.valid === true) {
      console.log(`Comment: ${reviewForm.comment.value}`)
      console.log(`Rating: ${reviewForm.rating.value}`)
    }
  }

  return <ReviewModalLayout reviewForm={reviewForm} onReview={handleReview} />;
}

export default ReviewModal;