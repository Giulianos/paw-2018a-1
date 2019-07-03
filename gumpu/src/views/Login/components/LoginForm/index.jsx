import React, { Suspense } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import useFormInput from 'hooks/useFormInput';

import emailValidator from 'validators/email';

import {
  login as loginAction,
  resetLogin as resetLoginAction,
} from 'redux/auth/actionCreators';

import LoginFormLayout from './layout';
import { useTranslation } from 'react-i18next';
import Loader from 'components/ui/Loader';

function LoginForm({
  login, loading, error, success, resetLogin, location,
}) {
  const { t } = useTranslation();

  const form = {
    email: useFormInput('', emailValidator(t('validations.email'))),
    password: useFormInput(''),
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    login({
      email: form.email.value,
      password: form.password.value,
    }, location.state && location.state.ref);
  };

  return (
    <LoginFormLayout
      {...form}
      handleSubmit={handleSubmit}
      loading={loading}
      error={error}
      success={success}
      resetRequest={resetLogin}
    />
  );
}

LoginForm.propTypes = {
  resetLogin: PropTypes.func.isRequired,
  login: PropTypes.func.isRequired,
  loading: PropTypes.bool.isRequired,
  error: PropTypes.bool.isRequired,
};

const mapStateToPros = state => ({
  success: state.auth.login.success,
  loading: state.auth.login.loading,
  error: state.auth.login.error,
});

const mapDispatchToProps = dispatch => ({
  login: (credentials, redirect) => dispatch(loginAction(credentials, redirect)),
  resetLogin: () => dispatch(resetLoginAction()),
});

export default connect(
  mapStateToPros,
  mapDispatchToProps,
)(props => <Suspense fallback={<Loader />}><LoginForm {...props} /></Suspense>);
