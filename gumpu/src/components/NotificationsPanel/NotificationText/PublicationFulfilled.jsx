import React from 'react';
import { Trans } from 'react-i18next';

function PublicationFulfilled({ notification }) {
  const title = notification.relatedPublication.description;
  return (
    <Trans i18nKey="notifications.publication_fulfilled">
      <strong>{{ title }}</strong>
      {' '}
is fulfilled!
    </Trans>
  );
}

export default PublicationFulfilled;
