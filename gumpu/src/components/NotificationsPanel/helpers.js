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
    default: return '';
  }
}