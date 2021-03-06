import React from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

import CardContainer from 'components/ui/CardContainer';

import NotificationText from './NotificationText';
import styles from './styles.module.scss';
import { getNotificationLink } from './helpers';

function NotificationsPanelLayout({ data, success, onClear }) {
  const { t } = useTranslation();

  return (
    <CardContainer className={styles.container}>
      <div className="pt-16 pb-16 pl-16 pr-16 txt-bold txt-gray3 row space-between">
        {t('header.notifications')}
        { success && data.notifications.length > 0 && <button onClick={onClear} className="txt-red">{t('notifications.clear')}</button> }
      </div>
      <ul>
        { !success ?
        <li className={styles.skeletonNotification}><div /><div /></li> :
        data.notifications.map(n => (
        <Link key={n.id} to={getNotificationLink(n)}>
          <li className={styles.notification}>
            <div className={styles.details}><NotificationText notification={n} /></div>
          </li>
        </Link>)) }
      </ul>
      { success && data.notifications.length === 0 && <div className={styles.emptyState}>{t('notifications.empty_state')}</div>  }
    </CardContainer>
  );
}

export default NotificationsPanelLayout;
