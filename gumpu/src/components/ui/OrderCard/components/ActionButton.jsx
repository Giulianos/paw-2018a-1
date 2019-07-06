import React from 'react';

import deleteIcon from 'assets/action_icons/delete.svg';
import checkIcon from 'assets/action_icons/check.svg';
import chatIcon from 'assets/action_icons/chat.svg';
import superviseIcon from 'assets/action_icons/supervise.svg';

function ActionButton({ label, action, className, ...props }) {
  switch(action) {
    case 'delete':
      return (
        <button {...props} className={`txt-small txt-red row center-alt ${className} `}>
          <img className="mr-4" src={deleteIcon} alt={label} />
          {label}
        </button>
      );
    case 'check':
      return (
        <button {...props} className={`txt-small txt-green row center-alt ${className} `}>
          <img className="mr-4" src={checkIcon} alt={label} />
          {label}
        </button>
      );
    case 'chat':
      return (
        <button {...props} className={`txt-small txt-blue row center-alt ${className} `}>
          <img className="mr-4" src={chatIcon} alt={label} />
          {label}
        </button>
      );
    case 'supervise':
      return (
        <button {...props} className={`txt-small txt-yellow row center-alt ${className} `}>
          <img className="mr-4" src={superviseIcon} alt={label} />
          {label}
        </button>
      );
    default:
      return (
        <button {...props} className={`txt-small txt-gray3 ${className} `}>
          {label}
        </button>
      );
  }
}

export default ActionButton;