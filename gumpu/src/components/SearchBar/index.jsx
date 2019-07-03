import React from 'react';

import useFormInput from 'hooks/useFormInput';
import history from 'router/history';

import SearchBarLayout from './layout';

function SearchBar() {
  const form = {
    term: useFormInput(''),
  };

  const search = (e) => {
    e.preventDefault();
    if (form.term.value !== '') {
      history.push(`/search/${form.term.value}`);
    }
  };

  return <SearchBarLayout handleSubmit={search} {...form} />;
}

export default SearchBar;
