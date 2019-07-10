import React from 'react';
import ImageUpload from './components/ImageUpload';

function AddImage({ match }) {
  return (
    <div className="view-container column center-alt">
      <ImageUpload match={match} />
    </div>
  );
}

export default AddImage;
