import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { retrieveImage } from 'redux/images/actionCreators';

import PublicationImageLayout from './layout';

function PublicationImage({ imageId, ...props }) {
  const dispatch = useDispatch();
  const image = useSelector(state => imageId && state.images.retrieve[imageId]);
  useEffect(() => {
    if(imageId && !image) {
      dispatch(retrieveImage(imageId));
    }
  }, [imageId])

  return <PublicationImageLayout {...image} {...props} />;
}

export default PublicationImage;