import React from 'react';
import Loader from '../Loader';

import styles from './styles.module.scss';

function FullScreenLoader() {
  return <div className={styles.mask}><Loader /></div>;
}

export default FullScreenLoader;
