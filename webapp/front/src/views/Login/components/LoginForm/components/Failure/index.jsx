import React from 'react';
import PropTypes from 'prop-types';

import failureIcon from 'assets/result_indicator/fail.svg';
import Button from 'components/ui/Button';

function Failure({ handleRetry }) {
  return (
    <div className="h100 w100 column center center-alt animated fadeIn">
      <img alt="" className="mb-48" src={failureIcon} />
      <span className="mb-48">
        <span className="txt-red txt-medium txt-bold mr-8">Oops!</span>
        <span className="txt-medium txt-gray3">Invalid credentials</span>
      </span>
      <Button type="button" color="red" variant="secondary" handleClick={handleRetry}>TRY AGAIN</Button>
    </div>
  );
}

Failure.propTypes = {
  handleRetry: PropTypes.func.isRequired,
};

export default Failure;
