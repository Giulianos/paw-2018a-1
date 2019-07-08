import React from 'react';

import PublicationCardLayout from './layout';
import Skeleton from './skeleton';

function PublicationCard({ className, publication }) {
  if(!publication) {
    return <Skeleton />;
  }
  return <PublicationCardLayout className={className} publication={publication} />;
}

export default PublicationCard;