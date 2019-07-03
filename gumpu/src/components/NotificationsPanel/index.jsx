import React from 'react';
import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';
import sample from 'services/sample';

const sampleImage = 'https://www.officedepot.com.mx/medias/84266.jpg-515ftw?context=bWFzdGVyfHJvb3R8NzUxNDh8aW1hZ2UvanBlZ3xoOTIvaDA3Lzk1NzAyMzY5MjM5MzQuanBnfDAyMzQxNTRlMTdjMzczYmRhZThlN2I0MDNhOGIzZjgyY2RlMWRlZjY4ZjVkZTNjYzQ1MzM3YWZkMzhlY2YyZDc';

function NotificationsPanel() {
  return (
    <CardContainer className={styles.container}>
      <div className={`pt-16 pb-16 pl-16 txt-bold txt-gray3`}>Notifications</div>
      <ul>
        <li className={styles.notification}>
          <img src={sampleImage} className={styles.image} />
          <div className={styles.details}>Notification 1</div>
        </li>
        <li className={styles.notification}>
          <img src={sampleImage} className={styles.image} />
          <div className={styles.details}>Notification 1</div>
        </li>
        <li className={styles.notification}>
          <img src={sampleImage} className={styles.image} />
          <div className={styles.details}>Notification 1</div>
        </li>
      </ul>
    </CardContainer>
  );
}

export default NotificationsPanel;