import React from 'react';

import useFormInput from 'hooks/useFormInput';

import CreateAccountLayout from './layout';

function CreateAccount() {

  const form = {
    name: useFormInput(''),
    email: useFormInput(''),
    password: useFormInput('')
  }

  return <CreateAccountLayout {...form} />;
}

export default CreateAccount;