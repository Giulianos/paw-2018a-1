import React from 'react';
import { Link } from 'react-router-dom';

import CardContainer from 'components/ui/CardContainer';
import {
  supervisorLeftPublication,
  orderFulfilledOrderer,
  orderFulfilledSupervisor,
  orderPurchased,
  newMessage
} from 'mocks/notifications';

import NotificationText from './NotificationText';
import styles from './styles.module.scss';
import { getNotificationLink } from './helpers';

const sampleImage = 'https://www.officedepot.com.mx/medias/84266.jpg-515ftw?context=bWFzdGVyfHJvb3R8NzUxNDh8aW1hZ2UvanBlZ3xoOTIvaDA3Lzk1NzAyMzY5MjM5MzQuanBnfDAyMzQxNTRlMTdjMzczYmRhZThlN2I0MDNhOGIzZjgyY2RlMWRlZjY4ZjVkZTNjYzQ1MzM3YWZkMzhlY2YyZDc';
const mockNotifications = [
  supervisorLeftPublication,
  orderFulfilledOrderer,
  orderFulfilledSupervisor,
  orderPurchased,
  newMessage
];

function NotificationsPanel() {

  const notifications = mockNotifications.map(n => (
    <Link to={getNotificationLink(n)}>
      <li className={styles.notification}>
        <img alt="" src={sampleImage} className={styles.image} />
        <div className={styles.details}><NotificationText notification={n} /></div>
      </li>
    </Link>
  ));

  return (
    <CardContainer className={styles.container}>
      <div className={`pt-16 pb-16 pl-16 txt-bold txt-gray3`}>Notifications</div>
      <ul>
        {notifications}
      </ul>
    </CardContainer>
  );
}

export default NotificationsPanel;