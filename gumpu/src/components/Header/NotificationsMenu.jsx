import React, { useRef, useState } from 'react';
import { useSelector } from 'react-redux';

import useOutsideClick from 'hooks/useOutsideClick';
import NotificationsPanel from 'components/NotificationsPanel';

import styles from './styles.module.scss';

function NotificationsMenu({ children }) {
  const wrapperRef = useRef(null);
  const [hidden, setHidden] = useState(true);
  const notifications = useSelector(state => state.notifications.retrieve);
  const thereAreNotifications = notifications.data && notifications.data.notifications.length > 0;
  useOutsideClick(wrapperRef, () => setHidden(true));
  const toggleMenu = () => setHidden(!hidden);

  return (
    <div ref={wrapperRef}>
      <button type="button" onClick={toggleMenu} className="txt-normal row center-alt flex-end">
        {children}
        <div className={thereAreNotifications ? styles.dot : styles.hiddenDot} />
      </button>
      <div className={`${styles.notificationsFloat} ${hidden ? styles.hidden : ''}`}><NotificationsPanel /></div>
    </div>
  );
}

export default NotificationsMenu;
