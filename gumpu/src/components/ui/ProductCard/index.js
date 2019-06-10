import React from 'react';
import PropTypes from 'prop-types';

import CardContainer from 'components/ui/CardContainer';
import RatingIndicator from 'components/ui/RatingIndicator';

import styles from './styles.module.scss';

function ProductCard({ product, className }) {
  return (
    <CardContainer className={`${styles.productCard} ${className} column center-alt pt-16 pb-16 pl-24 pr-24`}>
      { product ? (
          <>
            <img className={`${styles.image} mb-24`} src={ product.image_url } alt={product.tile} />
            <h3 className="w100 txt-medium mb-24">{ product.description }</h3>
            <span className="w100 txt-medium32 txt-gray3 mb-24">$ { product.price }</span>
            <div className="row">
              <span className="indicatorDescriptor txt-small txt-gray2">
                Calificación del organizador
              </span>
              <RatingIndicator className="pt-8" value={ product.supervisor_rating } />
            </div>
          </>
        ) : (
          <>
            <div className={`${styles.imagePlaceHolder} mb-24`} />
            <div className={`${styles.titlePlaceHolder} mb-24`} />
            <div className="row w100">
              <div className={`${styles.pricePlaceHolder} mb-24`} />
            </div>
          </>
      ) }
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