import React from 'react';
import PropTypes from 'prop-types';

import styles from './styles.module.scss';

function CardContainer({ children, className, ...props }) {
  return (
    <div {...props} className={`${styles.cardContainer} ${className}`}>{ children }</div>
  );
}

CardContainer.defaultProps = {
  className: '',
  children: '',
};

CardContainer.propTypes = {
  className: PropTypes.string,
  children: PropTypes.node,
};

export default CardContainer;
