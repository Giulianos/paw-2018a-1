import React from 'react';

import styles from './styles.module.scss';
import CreateAccount from './components/CreateAccount';

function Home() {
  
  return (
    <div className="view-container column center-alt">
      <div className="w100 row space-between pl-64 pr-64">
        <h2 className={`${styles.registerMessage} txt-xlarge`}>Create an account now!</h2>
        <CreateAccount/>
      </div>
    </div>
  );
}

export default Home;