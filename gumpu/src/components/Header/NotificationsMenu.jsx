import React, { useRef, useState } from 'react';

import useOutsideClick from 'hooks/useOutsideClick';
import NotificationsPanel from 'components/NotificationsPanel';

import styles from './styles.module.scss';

function NotificationsMenu({children}) {
  const wrapperRef = useRef(null);
  const [hidden, setHidden] = useState(true);
  useOutsideClick(wrapperRef, () => setHidden(true) );
  const toggleMenu = () => setHidden(!hidden);

  return (
    <div ref={wrapperRef}>
      <button onClick={toggleMenu} className="txt-normal">{children}</button>
      { !hidden && <div className={styles.notificationsFloat}><NotificationsPanel /></div> }
    </div>
  );
}

export default NotificationsMenu;