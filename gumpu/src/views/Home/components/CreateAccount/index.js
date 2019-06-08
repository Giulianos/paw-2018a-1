import React from 'react';

import useFormInput from 'hooks/useFormInput';

import fullNameValidator from 'validators/fullName';
import emailValidator from 'validators/email';
import passwordValidator from 'validators/password';

import CreateAccountLayout from './layout';

function CreateAccount() {

  const form = {
    name: useFormInput('', fullNameValidator),
    email: useFormInput('', emailValidator),
    password: useFormInput('', passwordValidator)
  }

  return <CreateAccountLayout {...form} />;
}

export default CreateAccount;