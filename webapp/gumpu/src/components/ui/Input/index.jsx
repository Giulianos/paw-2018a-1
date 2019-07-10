import React from 'react';
import PropTypes from 'prop-types';

import styles from './styles.module.scss';

function Input({ variant, className, ...props }) {
  return <input className={`${styles.input} ${styles[variant]} ${className}`} {...props} />;
}

Input.defaultProps = {
  className: '',
  variant: 'default',
};

Input.propTypes = {
  variant: PropTypes.string,
  className: PropTypes.string,
};

export default Input;
