import React from 'react';

import successIcon from 'assets/result_indicator/ok.svg';
import Button from 'components/ui/Button';
import CardContainer from 'components/ui/CardContainer';

function Success({ children, acceptMessage, addImageMessage, handleAccept, handleAddImage }) {
  return (
    <CardContainer className="pt-64 pb-64 pl-64 pr-64">
      <div className="h100 w100 column center center-alt animated fadeIn">
        <img alt="" className="mb-48" src={successIcon} />
        <span className="mb-48">
          {children}
        </span>
        <div className="row w100">
          <Button className="mr-8" type="button" color="green" variant="secondary" handleClick={handleAccept}>{acceptMessage}</Button>
          <Button type="button" color="green" handleClick={handleAddImage}>{addImageMessage}</Button>
        </div>
      </div>
    </CardContainer>
  );
}

export default Success;
