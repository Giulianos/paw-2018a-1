import React from 'react';
import PropTypes from 'prop-types';

import styles from './styles.module.scss';

function Button({ type, children, color, variant, className, handleClick }) {
  return <button type={type} onClick={handleClick} className={`${styles.button} ${styles[color]} ${styles[variant]} ${className}`}>{ children }</button>
}

Button.defaultProps = {
  color: '',
  className: '',
  variant: ''
}

Button.propTypes = {
  color: PropTypes.string,
  className: PropTypes.string,
  variant: PropTypes.string,
  type: PropTypes.string,
  handleClick: PropTypes.func.isRequired
}

export default Button;