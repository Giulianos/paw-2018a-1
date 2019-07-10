import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import AddImageLayout from './layout';
import { addImage } from 'redux/publication/actionCreators';

function AddImage({ match }) {
  const publicationId = match.params.pub_id;
  const [base64Image, setBase64Image] = useState(null);
  const dispatch = useDispatch();
  const upload = useSelector(state => state.publication.addImage);

  const handleUpload = e => {
    e.preventDefault();
    dispatch(addImage(publicationId, base64Image));
  };

  return (
    <AddImageLayout
      handleUpload={handleUpload}
      base64State={[base64Image, setBase64Image]}
      {...upload}
    />
  );
}

export default AddImage;
