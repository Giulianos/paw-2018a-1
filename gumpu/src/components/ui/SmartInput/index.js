import React from 'react';
import PropTypes from 'prop-types';
import Input from '../Input';

function SmartInput({ valid, ...props }) {
  const variant = valid === undefined ? 'default' : (valid ? 'valid' : 'invalid');

  return <Input variant={variant} {...props} />;
}

SmartInput.propTypes = {
  valid: PropTypes.bool
}

export default SmartInput;