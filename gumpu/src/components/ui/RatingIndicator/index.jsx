import React from 'react';
import PropTypes from 'prop-types';

import styles from './styles.module.scss';

function RatingIndicator({ className, value }) {
  return (
    <div className={`row ${className}`}>
      <div className={`${value > 0 ? styles.filled : styles.empty} mr-4`} />
      <div className={`${value > 1 ? styles.filled : styles.empty} mr-4`} />
      <div className={`${value > 2 ? styles.filled : styles.empty} mr-4`} />
      <div className={`${value > 3 ? styles.filled : styles.empty} mr-4`} />
      <div className={`${value > 4 ? styles.filled : styles.empty}`} />
    </div>
  );
}

RatingIndicator.defaultProps = {
  className: '',
  value: 0,
};

RatingIndicator.propTypes = {
  className: PropTypes.string,
  value: PropTypes.number,
};

export default RatingIndicator;
