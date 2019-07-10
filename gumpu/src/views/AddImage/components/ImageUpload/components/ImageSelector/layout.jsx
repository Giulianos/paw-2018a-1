import React, { useRef } from 'react';

import {fitImage} from './utils';

import styles from './styles.module.scss';

function ImageSelectorLayout() {
  const canvasRef = useRef(null);
  const imageChanged = e => {
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

  return (
    <label>
      <input onChange={imageChanged} className={styles.hiddenInput} type="file" id="image" />
      <canvas width="208" height="208" className={styles.container} ref={canvasRef} />
    </label>
  );
}

export default ImageSelectorLayout;