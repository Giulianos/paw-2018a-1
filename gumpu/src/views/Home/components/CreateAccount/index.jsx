import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import useFormInput from 'hooks/useFormInput';

import fullNameValidator from 'validators/fullName';
import emailValidator from 'validators/email';
import passwordValidator from 'validators/password';

import {
  createUser as createUserAction,
  resetCreateUser as resetCreateUserAction,
} from 'redux/user/actionCreators';

import CreateAccountLayout from './layout';
import { useTranslation } from 'react-i18next';
import history from 'router/history';

function CreateAccount({
  createUser, loading, error, success, resetCreateUser,
}) {
  const { t } = useTranslation();

  const form = {
    name: useFormInput('', fullNameValidator(3, 30, t('validations.full_name', { min: 3, max: 30 }))),
    email: useFormInput('', emailValidator(t('validations.email'))),
    password: useFormInput('', passwordValidator(8, t('validations.password', { min: 8 }))),
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if(form.name.valid === true && form.email.valid === true && form.password.valid === true) {
      createUser({
        name: form.name.value,
        email: form.email.value,
        password: form.password.value,
      });
    }
  };

  const login = () => {
    history.push('/login');
  }

  return (
    <CreateAccountLayout
      {...form}
      handleSubmit={handleSubmit}
      loading={loading}
      error={error}
      success={success}
      resetRequest={resetCreateUser}
      handleOk={login}
    />
  );
}

CreateAccount.propTypes = {
  resetCreateUser: PropTypes.func.isRequired,
  createUser: PropTypes.func.isRequired,
  loading: PropTypes.bool.isRequired,
  error: PropTypes.bool.isRequired,
};

const mapStateToPros = state => ({
  success: state.user.create.success,
  loading: state.user.create.loading,
  error: state.user.create.error,
});

const mapDispatchToProps = dispatch => ({
  createUser: newUser => dispatch(createUserAction(newUser)),
  resetCreateUser: () => dispatch(resetCreateUserAction()),
});

export default connect(
  mapStateToPros,
  mapDispatchToProps,
)(CreateAccount);
