import React from 'react';
import PropTypes from 'prop-types';

import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';

function ProductCard({ product, className }) {
  return (
    <CardContainer className={`${styles.productCard} ${className}`}>
      here goes the product
    </CardContainer>
  );
}

ProductCard.defaultProps = {
  className: ''
}

ProductCard.propTypes = {
  className: PropTypes.string,
  product: PropTypes.object
}

export default ProductCard;