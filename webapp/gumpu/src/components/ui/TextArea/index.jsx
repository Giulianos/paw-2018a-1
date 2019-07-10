import React from 'react';
import PropTypes from 'prop-types';

import styles from './styles.module.scss';

function TextArea({ variant, className, ...props }) {
  return <textarea className={`${styles.textArea} ${styles[variant]} ${className}`} {...props}></textarea>;
}

TextArea.defaultProps = {
  className: '',
  variant: 'default',
};

TextArea.propTypes = {
  variant: PropTypes.string,
  className: PropTypes.string,
};

export default TextArea;
