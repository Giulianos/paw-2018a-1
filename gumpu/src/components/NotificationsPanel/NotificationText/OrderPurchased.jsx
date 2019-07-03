import React from 'react';
import { Trans } from 'react-i18next';

function OrderPurchased({ notification }) {
  const title = notification.relatedPublication.description;
  return (
    <Trans i18nKey="notifications.order_purchased">
      <strong>{{ title }}</strong>
      {' '}
was purchased!
    </Trans>
  );
}

export default OrderPurchased;
