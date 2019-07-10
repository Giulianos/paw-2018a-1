import React, { Suspense } from 'react';
import PropTypes from 'prop-types';

import CardContainer from 'components/ui/CardContainer';
import RatingIndicator from 'components/ui/RatingIndicator';

import styles from './styles.module.scss';
import ProductCardSkeleton from './skeleton';
import { useTranslation } from 'react-i18next';
import PublicationImage from 'components/PublicationImage';

function ProductCard({ product, className, onClick }) {
  const { t } = useTranslation();

  const handleClickWithProduct = () => {
    if(onClick) {
      onClick(product);
    }
  }

  return (
    product ? (
        <CardContainer onClick={handleClickWithProduct} className={`${styles.productCard} ${className} column center-alt pt-16 pb-16 pl-24 pr-24`}>
          <PublicationImage className={`${styles.image} mb-24`} imageId={product.images[0]} alt={product.description} />
          <h3 className={`${styles.title} w100 txt-medium mb-24`}>{ product.description }</h3>
          <span className="w100 txt-medium32 txt-gray3 mb-24">
$
            { product.unitPrice }
          </span>
          <div className="row">
            <span className="indicatorDescriptor txt-small txt-gray2">
                {t('product.organizer_calification')}
            </span>
            <RatingIndicator className="pt-8" userId={product.supervisorId} />
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
