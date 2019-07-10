import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';

import usePolling from 'hooks/usePolling';
import useAuth from 'hooks/useAuth';
import { retrieveNotifications } from 'redux/notifications/actionCreators';

import NotificationsPanelLayout from './layout';

function NotificationsPanel() {
  const dispatch = useDispatch();
  const auth = useAuth();
  useEffect(() => { dispatch(retrieveNotifications(auth.user.id)); }, []);
  usePolling(() => dispatch(retrieveNotifications(auth.user.id)), 5000);
  const notifications = useSelector(state => state.notifications.retrieve);

  return <NotificationsPanelLayout {...notifications} />;
}

export default NotificationsPanel;
