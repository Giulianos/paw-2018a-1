import React from 'react';
import { Link } from 'react-router-dom';

function Secondary() {
  return (
    <div>
      <h1>This is the secondary screen.</h1>
      <Link to="/">Go to home...</Link>
    </div>
  );
}

export default Secondary;
