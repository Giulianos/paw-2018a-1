import React from 'react';
import PropTypes from 'prop-types';

import styles from './styles.module.scss';

function Button({ children, color, variant, className, handleClick }) {
  return <button onClick={handleClick} className={`${styles.button} ${styles[color]} ${styles[variant]} ${className}`}>{ children }</button>
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
  handleClick: PropTypes.func.isRequired
}

export default Button;