import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import { createSample } from 'redux/sample/actionCreators';

function Secondary({ sample }) {
  return (
    <div>
      <h1>This is the secondary screen.</h1>
      <Link to="/">Go to home...</Link>
      <button onClick={sample}>Run sample</button>
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
