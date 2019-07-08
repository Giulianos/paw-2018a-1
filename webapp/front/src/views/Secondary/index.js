import React from 'react';

import Messages from 'components/Messages';
function Secondary() {

  return (
    <div className="view-container">
      <Messages publicationId={9} userId={1} />
    </div>
  );
}

export default Secondary;