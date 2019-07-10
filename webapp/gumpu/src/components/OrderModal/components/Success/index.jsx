import React from 'react';

import successIcon from 'assets/result_indicator/ok.svg';
import Button from 'components/ui/Button';
import CardContainer from 'components/ui/CardContainer';

function Success({ children, buttonMessage, handleAccept }) {
  return (
    <CardContainer className="pt-64 pb-64 pl-64 pr-64">
      <div className="h100 w100 column center center-alt animated fadeIn">
        <img alt="" className="mb-48" src={successIcon} />
        <span className="mb-48">
          {children}
        </span>
        <Button type="button" color="green" variant="secondary" handleClick={handleAccept}>{buttonMessage}</Button>
      </div>
    </CardContainer>
  );
}

export default Success;
