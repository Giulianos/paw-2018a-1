import React from 'react';
import PropTypes from 'prop-types';

import ProductCard from 'components/ui/ProductCard';

import publicationMock from 'mocks/publication';

function ProductCarrousel({ title, className }) {
  return (
    <section className="column">
      <h2 className="txt-large mb-24 txt-gray3">{title}</h2>
      <div className={`${className} row space-between`}>
        <ProductCard product={publicationMock} className="mr-32" />
        <ProductCard product={publicationMock} className="mr-32" />
        <ProductCard product={publicationMock} className="mr-32" />
        <ProductCard product={publicationMock} />
      </div>
    </section>
  );
}

ProductCarrousel.defaultProps = {
  className: '',
  title: '',
};

ProductCarrousel.propTypes = {
  className: PropTypes.string,
  title: PropTypes.string,
};

export default ProductCarrousel;
