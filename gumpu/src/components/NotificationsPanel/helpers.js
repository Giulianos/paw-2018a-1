export const getNotificationLink = notification => {
  switch(notification.type) {
    case 'NEW_MESSAGES':
      const ordererId = notification.relatedOrder.ordererId;
      const receiverId = notification.relatedMessage.to.id;
      const publicationId = notification.relatedOrder.publicationId;
      if(ordererId === receiverId) {
        return `/orders/${publicationId}/messages`;
      } else {
        return `/publications/${publicationId}/messages/${ordererId}`;
      }
    case 'ORDER_PURCHASED':
      return `/orders/${notification.relatedOrder.publicationId}`;
    case 'PUBLICATION_FULFILLED':
      if(notification.relatedOrder) {
        return `/orders/${notification.relatedPublication.id}`;
      } else {
        return `/publications/${notification.relatedPublication.id}`;
      }
    case 'PUBLICATION_ORPHAN':
      return `/orders/${notification.relatedOrder.publicationId}`
    default: return '';
  }
}