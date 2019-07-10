import React from 'react';
import ReviewModalLayout from './layout';
import useFormInput from 'hooks/useFormInput';

function ReviewModal() {

  const reviewForm = {
    comment: useFormInput(''),
    rating: useFormInput('')
  }

  const handleReview = e => {
    e.preventDefault();
    console.log(`Comment: ${reviewForm.comment.value}`)
    console.log(`Rating: ${reviewForm.rating.value}`)
  }

  return <ReviewModalLayout reviewForm={reviewForm} onReview={handleReview} />;
}

export default ReviewModal;