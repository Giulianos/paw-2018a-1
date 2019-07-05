import React, { useState } from 'react';
import PropTypes from 'prop-types';

import ProductCard from 'components/ui/ProductCard';
import OrderModal from 'components/OrderModal';

import publicationMock from 'mocks/publication';
import publicationMock2 from 'mocks/publication2';

function ProductCarrousel({ title, className }) {
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
        <ProductCard onClick={handleOpen} product={publicationMock} className="mr-32" />
        <ProductCard onClick={handleOpen} product={publicationMock2} className="mr-32" />
        <ProductCard onClick={handleOpen} product={publicationMock} className="mr-32" />
        <ProductCard onClick={handleOpen} product={publicationMock} />
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
