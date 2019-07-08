import React, { Suspense, useRef, useEffect } from 'react';
import { Trans, useTranslation } from 'react-i18next';

import SearchBar from 'components/SearchBar';
import ProductCarrousel from 'components/ProductCarrousel';
import styles from './styles.module.scss';
import CreateAccount from './components/CreateAccount';

function Home({ location }) {
  const registerRef = useRef(null)
  const { t } = useTranslation();
  const scrollToRegister = () => {
    if(location.pathname === '/create-account')
      registerRef.current.scrollIntoView({ behavior: "smooth" })
  }

  useEffect(scrollToRegister, [location]);

  return (
    <div className="view-container column center-alt">
      <div className="w100 row mb-96">
        <SearchBar />
      </div>
      <div className="w100 row pl-64 pr-64 mb-96">
        <ProductCarrousel title={t('home.latest_products')} />
      </div>
      <div ref={registerRef} className="w100 row space-between pl-64 pr-64">
        <h2 className={`${styles.registerMessage} txt-xlarge mt-32`}>
          <Trans i18nKey="home.register_now">
            <span className="txt-gray3">New to Gumpu?</span>
            <br />
            <span className="txt-gray2">Create an account</span>
            <br />
            <span className="txt-green">now!</span>
          </Trans>
        </h2>
        <CreateAccount />
      </div>
    </div>
  );
}

function Loader() {
  return <div />;
}

export default props => <Suspense fallback={<Loader />}><Home {...props} /></Suspense>;
