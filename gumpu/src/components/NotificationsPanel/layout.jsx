import React from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

import CardContainer from 'components/ui/CardContainer';

import NotificationText from './NotificationText';
import styles from './styles.module.scss';
import { getNotificationLink } from './helpers';

const sampleImage = 'https://www.officedepot.com.mx/medias/84266.jpg-515ftw?context=bWFzdGVyfHJvb3R8NzUxNDh8aW1hZ2UvanBlZ3xoOTIvaDA3Lzk1NzAyMzY5MjM5MzQuanBnfDAyMzQxNTRlMTdjMzczYmRhZThlN2I0MDNhOGIzZjgyY2RlMWRlZjY4ZjVkZTNjYzQ1MzM3YWZkMzhlY2YyZDc';

function NotificationsPanelLayout({ data, success }) {
  const { t } = useTranslation();

  return (
    <CardContainer className={styles.container}>
      <div className="pt-16 pb-16 pl-16 txt-bold txt-gray3">{t('header.notifications')}</div>
      <ul>
        { !success ?
        <li className={styles.skeletonNotification}><div /><div /></li> :
        data.notifications.map(n => (
        <Link key={n.id} to={getNotificationLink(n)}>
          <li className={styles.notification}>
            <img alt="" src={sampleImage} className={styles.image} />
            <div className={styles.details}><NotificationText notification={n} /></div>
          </li>
        </Link>)) }
      </ul>
      { success && data.notifications.length === 0 && <div className={styles.emptyState}>{t('notifications.empty_state')}</div>  }
    </CardContainer>
  );
}

export default NotificationsPanelLayout;
