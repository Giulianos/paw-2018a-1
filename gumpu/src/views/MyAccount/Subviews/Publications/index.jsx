import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { listUserPublications } from 'redux/publication/actionCreators';

import PublicationsLayout from './layout';
import useAuth from 'hooks/useAuth';

function Publications() {
  const publications = useSelector(state => state.publication.userList);
  const dispatch = useDispatch();
  const auth = useAuth();

  useEffect(() => {
    if(!publications.success) {
      dispatch(listUserPublications(auth.user.id, 0, 30));
    }
  }, [])

  return <PublicationsLayout publications={publications.publications} loading={publications.loading} />;
}

export default Publications;