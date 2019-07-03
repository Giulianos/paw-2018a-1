import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import { createSample } from 'redux/sample/actionCreators';
import Loader from 'components/ui/Loader';
import NotificationsPanel from 'components/NotificationsPanel';

function Secondary({ sample }) {
  return (
    <div style={{ height: '1000px' }}>
      <h1>This is the secondary screens.</h1>
      <NotificationsPanel />
    </div>
  );
}

const mapDispatchToProps = dispatch => ({
  sample: () => dispatch(createSample())
});

Secondary.propTypes = {
  sample: PropTypes.func.isRequired
}

export default connect(null, mapDispatchToProps)(Secondary);
