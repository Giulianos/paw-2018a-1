import React from 'react';

import MessagesLayout from './layout';

import mockMessages from 'mocks/messages';

function Messages() {
  return <MessagesLayout title="Giuliano" messages={mockMessages} scroll={10} />;
}

export default Messages;