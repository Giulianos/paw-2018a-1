import React from 'react';
import PropTypes from 'prop-types';

import styles from './styles.module.scss';

function CardContainer({ children, className }) {
  return (
    <div className={`${styles.cardContainer} ${className}`}>{ children }</div>
  );
}

CardContainer.defaultProps = {
  className: ''
}

CardContainer.propTypes = {
  className: PropTypes.string
}

export default CardContainer;