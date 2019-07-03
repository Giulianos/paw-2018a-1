import React, { Suspense } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { useTranslation } from 'react-i18next';

import { createSample } from 'redux/sample/actionCreators';
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
