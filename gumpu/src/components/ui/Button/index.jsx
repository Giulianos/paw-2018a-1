import React from 'react';
import PropTypes from 'prop-types';

import styles from './styles.module.scss';

function Button({
  type, children, color, variant, className, handleClick,
}) {
  // eslint-disable-next-line react/button-has-type
  return <button type={type} onClick={handleClick} className={`${styles.button} ${styles[color]} ${styles[variant]} ${className}`}>{ children }</button>;
}

Button.defaultProps = {
  children: '',
  color: '',
  className: '',
  variant: '',
  type: 'submit',
};

Button.propTypes = {
  children: PropTypes.node,
  color: PropTypes.string,
  className: PropTypes.string,
  variant: PropTypes.string,
  type: PropTypes.string,
  handleClick: PropTypes.func,
};

export default Button;
