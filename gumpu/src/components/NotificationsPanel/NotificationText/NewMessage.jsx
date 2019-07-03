import React from 'react';
import { Trans } from 'react-i18next';

function NewMessage({ notification }) {
  const { name } = notification.relatedMessage.from;
  return (
    <Trans i18nKey="notifications.new_message">
      New message from <strong>{{ name }}</strong>
    </Trans>
  );
}

export default NewMessage;
