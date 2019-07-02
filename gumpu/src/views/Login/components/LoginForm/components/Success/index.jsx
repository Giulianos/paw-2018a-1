import React from 'react';

import successIcon from 'assets/result_indicator/ok.svg';

function Success() {
  return (
    <div className="h100 w100 column center center-alt animated fadeIn">
      <img alt="" className="mb-48" src={successIcon} />
      <span>
        <span className="txt-green txt-medium txt-bold mr-8">Yay!</span>
        <span className="txt-medium txt-gray3">Logged in successfully</span>
      </span>
    </div>
  );
}

export default Success;
