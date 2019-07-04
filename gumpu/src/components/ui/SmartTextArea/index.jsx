import React, { useState } from 'react';
import PropTypes from 'prop-types';
import uniqueId from 'lodash/uniqueId';
import TextArea from '../TextArea';

function SmartTextArea({
  className, maxLength, label, valid, validatable, dirty, ...props
}) {
  const [counter, setCounter] = useState(0);
  const [id] = useState(uniqueId('field_'));
  let variant = 'default';

  if (validatable && dirty) {
    variant = valid === true ? 'valid' : 'invalid';
  }

  const updateCounter = e => {
    setCounter(e.target.value.length)
  }

  if(maxLength !== undefined) {
    return (
      <div className={`${className} relative`}>
        { /* eslint-disable-next-line jsx-a11y/label-has-for */ }
        <label htmlFor={id} className={`column w100 txt-input-label ${variant}`}>
          { label }
          <TextArea maxlength={`${maxLength}`} onChange={updateCounter} id={id} className="mt-8" variant={variant} {...props} />
        </label>
        <span className="absolute txt-tiny txt-gray2 w100 txt-right pt-4">{ maxLength-counter }</span>
        { (variant === 'invalid') && <span className="absolute txt-tiny txt-red w100 txt-right pt-4">{ valid }</span> }
      </div>
    );
  }

  return (
    <div className={`${className} relative`}>
      { /* eslint-disable-next-line jsx-a11y/label-has-for */ }
      <label htmlFor={id} className={`column w100 txt-input-label ${variant}`}>
        { label }
        <TextArea id={id} className="mt-8" variant={variant} {...props} />
      </label>
      { (variant === 'invalid') && <span className="absolute txt-tiny txt-red w100 txt-right pt-4">{ valid }</span> }
    </div>
  );
}

SmartTextArea.defaultProps = {
  className: '',
  valid: undefined,
  label: '',
  maxLength: undefined
};

SmartTextArea.propTypes = {
  valid: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
  validatable: PropTypes.bool.isRequired,
  dirty: PropTypes.bool.isRequired,
  className: PropTypes.string,
  label: PropTypes.string,
  maxLength: PropTypes.number
};

export default SmartTextArea;
