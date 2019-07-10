import React from 'react';

import PublicationCardLayout from './layout';
import Skeleton from './skeleton';

function PublicationCard({ className, publication, onMessage, onDelete, onMarkPurchased }) {
  if(!publication) {
    return <Skeleton />;
  }

  const messageHandler = () => {
    if(onMessage) {
      onMessage(publication);
    }
  }

  return (
    <PublicationCardLayout
      className={className}
      publication={publication}
      onMessage={messageHandler}
      onDelete={onDelete}
      onMarkPurchased={onMarkPurchased}
    />
  );
}

export default PublicationCard;