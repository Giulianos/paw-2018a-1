import { getNotificationLink } from './helpers';

import {
  supervisorLeftPublication,
  orderFulfilledOrderer,
  orderFulfilledSupervisor,
  orderPurchased,
  newMessage
} from 'mocks/notifications';

describe('NotificationsPanel helpers', () => {
  it('should return the correct link for supervisorLeftPublication', () => {
    const expectedLink = '/orders/1';

    expect(getNotificationLink(supervisorLeftPublication)).toEqual(expectedLink);
  });

  it('should return the correct link for orderFulfilledOrderer', () => {
    const expectedLink = '/orders/1';

    expect(getNotificationLink(orderFulfilledOrderer)).toEqual(expectedLink);
  });

  it('should return the correct link for orderFulfilledSupervisor', () => {
    const expectedLink = '/publications/1';

    expect(getNotificationLink(orderFulfilledSupervisor)).toEqual(expectedLink);
  });

  it('should return the correct link for orderPurchased', () => {
    const expectedLink = '/orders/1';

    expect(getNotificationLink(orderPurchased)).toEqual(expectedLink);
  });

  it('should return the correct link for newMessage', () => {
    const expectedLink = '/publications/1/messages/3';

    expect(getNotificationLink(newMessage)).toEqual(expectedLink);
  });
});
