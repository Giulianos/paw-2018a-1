import React, { Suspense } from 'react';
import Moment from 'react-moment';

import styles from './styles.module.scss';
import { useTranslation } from 'react-i18next';

function MessageSuspense({ sent, message, className }) {
  const { i18n } = useTranslation();

  const messageClass = `${styles.message} ${sent ? styles.sent : ''} ${className}`

  console.log(i18n.language);

  return <div className={messageClass}>
    <div className={styles.body}>
      { message.message }
    </div>
    <Moment className={styles.date} locale={i18n.language} fromNow>{ message.date }</Moment>
  </div>;
}

function Message(props) {
  return (
    <Suspense fallback={<div />}>
      <MessageSuspense {...props} />
    </Suspense>
  );
}

export default Message;