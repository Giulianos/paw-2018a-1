import React from 'react';
import PropTypes from 'prop-types';

import ProductCard from 'components/ui/ProductCard';

import productSummaryMock from 'mocks/productSummary';

function ProductCarrousel({ className }) {
  return (
    <section className="column">
      <h2 className="txt-large mb-24 txt-gray3">Popular products</h2>
      <div className={`${className} row space-between`}>
        <ProductCard product={productSummaryMock} className="mr-32" />
        <ProductCard product={productSummaryMock} className="mr-32" />
        <ProductCard product={productSummaryMock} className="mr-32" />
        <ProductCard product={productSummaryMock} />
      </div>
    </section>
  );
}

ProductCarrousel.defaultProps = {
  className: '',
};

ProductCarrousel.propTypes = {
  className: PropTypes.string,
};

export default ProductCarrousel;