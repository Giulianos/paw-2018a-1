import React from 'react';
import PropTypes from 'prop-types';
import Input from '../Input';

function SmartInput({ className, label, valid, dirty, ...props }) {
  const variant = valid === undefined || !dirty ? 'default' : (valid ? 'valid' : 'invalid');

  return (
    <>
      <label className={`${className} column txt-input-label ${variant}`}>
        { label }
        <Input className="mt-8" variant={variant} {...props} />
      </label>
    </>
  );
}

SmartInput.defaultProps = {
  className: ''
}

SmartInput.propTypes = {
  valid: PropTypes.bool,
  dirty: PropTypes.bool,
  className: PropTypes.string
}

export default SmartInput;