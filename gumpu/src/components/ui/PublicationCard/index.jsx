import React from 'react';

import PublicationCardLayout from './layout';
import Skeleton from './skeleton';

function PublicationCard({ className, publication, onMessage }) {
  if(!publication) {
    return <Skeleton />;
  }

  const messageHandler = () => {
    if(onMessage) {
      onMessage(publication);
    }
  }

  return <PublicationCardLayout className={className} publication={publication} onMessage={messageHandler} />;
}

export default PublicationCard;