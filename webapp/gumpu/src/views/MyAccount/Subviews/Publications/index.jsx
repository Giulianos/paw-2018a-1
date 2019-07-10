import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import history from 'router/history';

import { listUserPublications, deletePublication, markPurchased } from 'redux/publication/actionCreators';

import PublicationsLayout from './layout';
import useAuth from 'hooks/useAuth';

function Publications() {
  const publications = useSelector(state => state.publication.userList);
  const dispatch = useDispatch();
  const auth = useAuth();
  const [messageModal, setMessageModal] = useState(null);

  useEffect(() => {
    if(!publications.success) {
      dispatch(listUserPublications(auth.user.id, 0, 30));
    }
  }, [])

  const openMessages = publication => {
    setMessageModal(publication);
    history.push(`/my-account/publications/${publication.id}/messages`);
  }

  const closeMessages = publicationId => {
    setMessageModal(null);
    history.push(`/my-account/publications/${publicationId}`);
  }

  const deleteHandler = publicationId => () => {
    dispatch(deletePublication(publicationId));
  }

  const markPurchasedHandler = publicationId => () => {
    dispatch(markPurchased(publicationId));
  }

  return (
  <PublicationsLayout
    publications={publications.publications}
    loading={publications.loading}
    messageModal={messageModal}
    closeMessages={closeMessages}
    openMessages={openMessages}
    onDelete={deleteHandler}
    onMarkPurchased={markPurchasedHandler}
  />);
}

export default Publications;