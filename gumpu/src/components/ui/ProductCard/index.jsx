import React, { Suspense } from 'react';
import PropTypes from 'prop-types';

import CardContainer from 'components/ui/CardContainer';
import RatingIndicator from 'components/ui/RatingIndicator';

import styles from './styles.module.scss';
import ProductCardSkeleton from './skeleton';
import { useTranslation } from 'react-i18next';

function ProductCard({ product, className }) {
  const { t } = useTranslation();
  return (
    product ? (
        <CardContainer className={`${styles.productCard} ${className} column center-alt pt-16 pb-16 pl-24 pr-24`}>
          <img className={`${styles.image} mb-24`} src={product.image_url} alt={product.tile} />
          <h3 className="w100 txt-medium mb-24">{ product.description }</h3>
          <span className="w100 txt-medium32 txt-gray3 mb-24">
$
            { product.price }
          </span>
          <div className="row">
            <span className="indicatorDescriptor txt-small txt-gray2">
                {t('product.organizer_calification')}
            </span>
            <RatingIndicator className="pt-8" value={product.supervisor_rating} />
          </div>
        </CardContainer>
      ) : (
        <ProductCardSkeleton className={className} />
      )
  );
}

ProductCard.defaultProps = {
  className: '',
};

ProductCard.propTypes = {
  className: PropTypes.string,
  product: PropTypes.object,
};

export default props => <Suspense fallback={<ProductCardSkeleton className={props.className} />} ><ProductCard {...props} /></Suspense>;
