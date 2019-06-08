import { useState } from 'react';

function useFormInput(initialValue, validator) {
  const [value, setValue] = useState(initialValue);

  function handleChange(e) {
    setValue(e.target.value);
  }

  const valid = validator && validator(value);
  
  return {
    value,
    onChange: handleChange,
    valid
  }
}

export default useFormInput;