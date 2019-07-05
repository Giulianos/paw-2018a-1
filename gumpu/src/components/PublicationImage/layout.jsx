import React from 'react';

import styles from './styles.module.scss';

function PublicationImageLayout({loading, data, success, error, ...props}) {
  if(loading || !data) {
    return <div className={`${styles.imagePlaceholder} ${props.className}`} />;
  }
  return <img src={data.base64} {...props} />;
}

export default PublicationImageLayout;