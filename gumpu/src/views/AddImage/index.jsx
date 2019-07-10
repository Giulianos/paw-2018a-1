import React from 'react';
import ImageUpload from './components/ImageUpload';

function AddImage({ location }) {
  return (
    <div className="view-container column center-alt">
      <ImageUpload location={location} />
    </div>
  );
}

export default AddImage;
