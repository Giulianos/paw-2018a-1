import { useState } from 'react';

function useFormInput(initialValue, validator) {
  const [value, setValue] = useState(initialValue);
  const [dirty, setDirty] = useState(false);

  function handleChange(e) {
    setDirty(true);
    setValue(e.target.value);
  }

  const valid = validator && validator(value);
  
  return {
    value,
    onChange: handleChange,
    valid,
    dirty
  }
}

export default useFormInput;