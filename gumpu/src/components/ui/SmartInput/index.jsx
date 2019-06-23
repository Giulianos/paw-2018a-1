import React, { useState } from 'react';
import PropTypes from 'prop-types';
import uniqueId from 'lodash/uniq';
import Input from '../Input';

function SmartInput({
  className, label, valid, validatable, dirty, ...props
}) {
  const [id] = useState(uniqueId('form-field-id'));
  let variant = 'default';

  if (validatable && dirty) {
    variant = valid === true ? 'valid' : 'invalid';
  }
  return (
    <div className={`${className} relative`}>
      { /* eslint-disable-next-line jsx-a11y/label-has-for */ }
      <label htmlFor={id} className={`column w100 txt-input-label ${variant}`}>
        { label }
        <Input id={id} className="mt-8" variant={variant} {...props} />
      </label>
      { (variant === 'invalid') && <span className="absolute txt-tiny txt-red w100 txt-right pt-4">{ valid }</span> }
    </div>
  );
}

SmartInput.defaultProps = {
  className: '',
  valid: undefined,
  label: '',
};

SmartInput.propTypes = {
  valid: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
  validatable: PropTypes.bool.isRequired,
  dirty: PropTypes.bool.isRequired,
  className: PropTypes.string,
  label: PropTypes.string,
};

export default SmartInput;
