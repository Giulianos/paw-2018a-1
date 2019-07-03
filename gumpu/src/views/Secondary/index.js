import React, { Suspense } from 'react';
import { useTranslation } from 'react-i18next';

import NotificationsPanel from 'components/NotificationsPanel';
import Loader from 'components/ui/Loader';

function Secondary() {
  const { t } = useTranslation();

  return (
    <div style={{ height: '1000px' }}>
      <h1>{t('secondary.example')}</h1>
      <NotificationsPanel />
    </div>
  );
}

export default () => (
  <Suspense fallback={Loader}><Secondary /></Suspense>
);
