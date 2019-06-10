import { useState } from 'react';

function useFormInput(initialValue, validator) {
  const [value, setValue] = useState(initialValue);
  const [dirty, setDirty] = useState(false);

  function handleChange(e) {
    setDirty(true);
    setValue(e.target.value);
  }

  const validatable = !!validator;
  const valid = validatable && validator(value);
  
  return {
    value,
    onChange: handleChange,
    validatable,
    valid,
    dirty
  }
}

export default useFormInput;