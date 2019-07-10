export const getNotificationLink = (notification) => {
  switch (notification.type) {
    case 'NEW_MESSAGES':
      const ordererId = notification.relatedOrder.orderer.id;
      const receiverId = notification.relatedMessage.to.id;
      const publicationId = notification.relatedOrder.publication.id;
      if (ordererId === receiverId) {
        return `/my-account/orders`;
      }
      return `/my-account/publications/${publicationId}/messages/${ordererId}`;

    case 'ORDER_PURCHASED':
      return `/my-account/orders`;
    case 'PUBLICATION_FULFILLED':
      if (notification.relatedOrder) {
        return `/my-account/orders`;
      }
      return `/my-account/publications`;

    case 'PUBLICATION_ORPHAN':
      return `/my-account/orders`;
    default: return '';
  }
};
