import React from 'react';

import failureIcon from 'assets/result_indicator/fail.svg';

function Failure() {
  return (
    <div className="h100 w100 column center center-alt">
      <img alt="" className="mb-48" src={failureIcon} />
      <span>
        <span className="txt-red txt-medium txt-bold mr-8">Oops!</span>
        <span className="txt-medium txt-gray3">We couldn't create your account</span>
      </span>
    </div>
  )
}

export default Failure;