import React, { useState } from 'react';
import PropTypes from 'prop-types';

import ProductCard from 'components/ui/ProductCard';
import OrderModal from 'components/OrderModal';

function ProductCarrousel({ title, className, products }) {
  const [ selectedPublication, setSelectedPublication ] = useState();
  const [ modalVisibility, setModalVisibility ] = useState(false);

  const handleOpen = pub => {
    setSelectedPublication(pub);
    setModalVisibility(true);
  }

  const handleClose = () => {
    setModalVisibility(false);
    setSelectedPublication(undefined);
  }

  return (
    <section className="column">
      <h2 className="txt-large mb-24 txt-gray3">{title}</h2>
      <div className={`${className} row space-between`}>
        <ProductCard onClick={products && handleOpen} product={products && products[0]} className="mr-32" />
        <ProductCard onClick={products && handleOpen} product={products && products[1]} className="mr-32" />
        <ProductCard onClick={products && handleOpen} product={products && products[2]} className="mr-32" />
        <ProductCard onClick={products && handleOpen} product={products && products[3]} />
      </div>
      <OrderModal shown={modalVisibility} onClose={handleClose} selectedPublication={selectedPublication} />
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
