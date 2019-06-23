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

function CreateAccount({
  createUser, loading, error, success, resetCreateUser,
}) {
  const form = {
    name: useFormInput('', fullNameValidator),
    email: useFormInput('', emailValidator),
    password: useFormInput('', passwordValidator),
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    createUser({
      name: form.name.value,
      email: form.email.value,
      password: form.password.value,
    });
  };

  return (
    <CreateAccountLayout
      {...form}
      handleSubmit={handleSubmit}
      loading={loading}
      error={error}
      success={success}
      resetRequest={resetCreateUser}
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
