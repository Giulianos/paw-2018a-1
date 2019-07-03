import React from 'react';
import { Trans } from 'react-i18next';

const NewMessageNotification = ({ notification }) => {
  const name = notification.relatedMessage.from.name;
  return (
    <Trans i18nKey="notifications.new_message">
      New message from <strong>{{name}}</strong>
    </Trans>
  );
}

function NotificationText({ notification }) {
  switch(notification.type) {
    case 'NEW_MESSAGES': return <NewMessageNotification notification={notification} />;
    default: return <div></div>;
  }
}

export default NotificationText;