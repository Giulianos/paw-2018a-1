import React from 'react';
import PropTypes from 'prop-types';
import Input from '../Input';

function SmartInput({ valid, dirty, ...props }) {
  const variant = valid === undefined || !dirty ? 'default' : (valid ? 'valid' : 'invalid');

  return <Input variant={variant} {...props} />;
}

SmartInput.propTypes = {
  valid: PropTypes.bool,
  dirty: PropTypes.bool
}

export default SmartInput;