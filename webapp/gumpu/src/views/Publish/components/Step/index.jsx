import React from 'react';

import styles from './styles.module.scss';

function Step({number, text, moreInfo}) {
  return (
    <div className="row center-alt">
      <div className={styles.numberCircle}>{number}</div>
      <div className="ml-64">
        <h2 className={`txt-xlarge-thin txt-gray3 ${styles.titleLimit}`}>{text}</h2>
        <span>{moreInfo}</span>
      </div>
    </div>
  );
}

export default Step;