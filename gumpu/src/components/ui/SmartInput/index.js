import React from 'react';
import PropTypes from 'prop-types';
import Input from '../Input';

function SmartInput({ label, valid, dirty, ...props }) {
  const variant = valid === undefined || !dirty ? 'default' : (valid ? 'valid' : 'invalid');

  return (
    <>
      <label className={`column txt-input-label ${variant}`}>
        { label }
        <Input variant={variant} {...props} />
      </label>
    </>
  );
}

SmartInput.propTypes = {
  valid: PropTypes.bool,
  dirty: PropTypes.bool
}

export default SmartInput;