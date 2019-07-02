import React from 'react';
import LoginForm from './components/LoginForm';

function Login({location}) {
  return (
    <div className="view-container column center-alt">
      <LoginForm location={location} />
    </div>
  );
}

export default Login;