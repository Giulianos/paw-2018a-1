import React, { useRef } from 'react';

import {fitImage} from './utils';
import ImageSelectorLayout from './layout';

function ImageSelector({ b64Field }) {
  const [b64Image, setB64Image ] = b64Field;
  const canvasRef = useRef(null);
  const imageChangedHandler = e => {
    e.preventDefault();
    const context = canvasRef.current.getContext('2d');
    const image = new Image();
    const reader = new FileReader();
    reader.onload = () => {
      image.onload = () => {
        fitImage(context, image, 208, 208);
        setB64Image(canvasRef.current.toDataURL("image/png"));
      };
      image.src = reader.result;
    }
    reader.readAsDataURL(e.target.files[0]);
  }

  return <ImageSelectorLayout onImageChange={imageChangedHandler} canvasRef={canvasRef} />;
}

export default ImageSelector;