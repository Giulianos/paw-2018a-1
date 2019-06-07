import React from 'react';
import { Link } from 'react-router-dom';

function Home() {
  return (
    <div>
      <h1>This is the Home screen.</h1>
      <Link to="/secondary">Go to secondary...</Link>
    </div>
  );
}

export default Home;