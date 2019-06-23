import React from 'react';

import ProductCarrousel from 'components/ProductCarrousel';
import styles from './styles.module.scss';
import CreateAccount from './components/CreateAccount';

function Home() {
  return (
    <div className="view-container column center-alt">
      <div className="w100 row space-between pl-64 pr-64 mb-96">
        <h2 className={`${styles.registerMessage} txt-xlarge mt-32`}>
          <span className="txt-gray3">New to Gumpu?</span>
          <br />
          <span className="txt-gray2">Create an account</span>
          <br />
          <span className="txt-green">now!</span>
        </h2>
        <CreateAccount />
      </div>
      <div className="w100 row pl-64 pr-64">
        <ProductCarrousel />
      </div>
    </div>
  );
}

export default Home;