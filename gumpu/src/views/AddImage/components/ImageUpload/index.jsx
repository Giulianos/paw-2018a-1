import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import AddImageLayout from './layout';
import { addImage, resetAddImage } from 'redux/publication/actionCreators';
import history from 'router/history';

function AddImage({ match }) {
  const publicationId = match.params.pub_id;
  const [base64Image, setBase64Image] = useState(null);
  const dispatch = useDispatch();
  const upload = useSelector(state => state.publication.addImage);

  const handleUpload = e => {
    e.preventDefault();
    dispatch(addImage(publicationId, base64Image));
  };

  const handleRetry = () => {
    dispatch(resetAddImage());
  };

  const handleOk = () => {
    history.replace('/my-account/publications');
  };

  return (
    <AddImageLayout
      handleUpload={handleUpload}
      base64State={[base64Image, setBase64Image]}
      handleRetry={handleRetry}
      handleOk={handleOk}
      {...upload}
    />
  );
}

export default AddImage;
