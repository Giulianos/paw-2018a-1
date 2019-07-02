import React from 'react';

function Search({ match }) {
  const searchTerm = match.params.term;

  return <div className="view-container">Search results for: {searchTerm}</div>;
}

export default Search;