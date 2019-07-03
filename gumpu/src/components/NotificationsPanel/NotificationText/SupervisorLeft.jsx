import React from 'react';
import { Trans } from 'react-i18next';

function SupervisorLeft({ notification }) {
  const title = notification.relatedPublication.description;
  return (
    <Trans i18nKey="notifications.publication_orphan">
      The organizer left
      {' '}
      <strong>{{ title }}</strong>
    </Trans>
  );
}

export default SupervisorLeft;
