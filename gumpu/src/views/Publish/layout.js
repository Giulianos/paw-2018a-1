import React, { Suspense } from 'react';
import { useTranslation } from 'react-i18next';

import Loader from 'components/ui/Loader';

function PublishLayout() {
  const { t } = useTranslation();

  return (
    <div style={{ height: '1000px' }}>
      <h1>{t('publish.example')}</h1>
    </div>
  );
}

export default props => (
  <Suspense fallback={<Loader />}><PublishLayout {...props} /></Suspense>
);
