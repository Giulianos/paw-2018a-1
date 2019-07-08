import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';

import { listPublicationOrders, resetListPublicationOrders } from 'redux/publication/actionCreators';

import MessageModalLayout from './layout';

function MessageModal({ setModal, publication}) {
  const dispatch = useDispatch();
  const orders = useSelector(state => state.publication.ordersList);
  useEffect(() => {
    dispatch(listPublicationOrders(publication.id, 0, 30));
    return () => dispatch(resetListPublicationOrders());
  }, [])

  const closeModal = () => {
    setModal(null);
  }

  return <MessageModalLayout onClose={closeModal} publication={publication} orders={orders} />;
}

export default MessageModal;