import React from 'react';
import { useLocation } from 'react-router-dom';

import Messages from 'components/Messages';

function RoutedMessages({ match, location }) {
  return <div><Messages userId={match.params.ord_id} publicationId={match.params.pub_id} title={location.state ? location.state.title : 'Mensajes'} /></div>
}

export default RoutedMessages;