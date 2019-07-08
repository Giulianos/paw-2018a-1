import React from 'react';
import PropTypes from 'prop-types';

import failureIcon from 'assets/result_indicator/fail.svg';
import Button from 'components/ui/Button';
import CardContainer from 'components/ui/CardContainer';

function Failure({ buttonMessage, handleRetry, children }) {
  return (
    <CardContainer className="pt-64 pb-64 pl-64 pr-64">
      <div className="h100 w100 column center center-alt animated fadeIn">
        <img alt="" className="mb-48" src={failureIcon} />
        <span className="mb-48">
          {children}
        </span>
        <Button type="button" color="red" variant="secondary" handleClick={handleRetry}>{buttonMessage}</Button>
      </div>
    </CardContainer>
  );
}

Failure.propTypes = {
  handleRetry: PropTypes.func.isRequired,
};

export default Failure;
