import React from 'react';
import PropTypes from 'prop-types';
import Input from '../Input';

function SmartInput({ className, label, valid, validatable, dirty, ...props }) {
  const variant = !validatable || !dirty ? 'default' : (valid === true ? 'valid' : 'invalid');

  return (
    <div className={`${className} relative`}>
      <label className={`column w100 txt-input-label ${variant}`}>
        { label }
        <Input className="mt-8" variant={variant} {...props} />
      </label>
      { (variant === 'invalid') && <span className="absolute txt-tiny txt-red w100 txt-right pt-4">{ valid }</span> }
    </div>
  );
}

SmartInput.defaultProps = {
  className: ''
}

SmartInput.propTypes = {
  valid: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
  validatable: PropTypes.bool,
  dirty: PropTypes.bool,
  className: PropTypes.string
}

export default SmartInput;