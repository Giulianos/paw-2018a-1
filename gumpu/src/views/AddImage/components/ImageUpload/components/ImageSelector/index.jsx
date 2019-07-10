import React, { useRef } from 'react';

import {fitImage} from './utils';
import ImageSelectorLayout from './layout';

function ImageSelector() {
  const canvasRef = useRef(null);
  const imageChangedHandler = e => {
    e.preventDefault();
    const context = canvasRef.current.getContext('2d');
    const image = new Image();
    const reader = new FileReader();
    reader.onload = () => {
      image.onload = () => {
        fitImage(context, image, 208, 208);
      };
      image.src = reader.result;
    }
    reader.readAsDataURL(e.target.files[0]);
  }

  return <ImageSelectorLayout onImageChange={imageChangedHandler} canvasRef={canvasRef} />;
}

export default ImageSelector;