import React from 'react';

import styles from './styles.module.scss';

function ImageSelectorLayout({ onImageChange, canvasRef }) {
  return (
    <label>
      <input onChange={onImageChange} className={styles.hiddenInput} type="file" id="image" />
      <canvas width="208" height="208" className={styles.container} ref={canvasRef} />
    </label>
  );
}

export default ImageSelectorLayout;