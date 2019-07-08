import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';

import { listPublicationOrders, resetListPublicationOrders } from 'redux/publication/actionCreators';

import MessageModalLayout from './layout';

function MessageModal({ onClose, match }) {
  const dispatch = useDispatch();
  const orders = useSelector(state => state.publication.ordersList);
  useEffect(() => {
    dispatch(listPublicationOrders(match.params.pub_id, 0, 30));
    return () => dispatch(resetListPublicationOrders());
  }, [])
  const onCloseWithPublication = () => {
    onClose(match.params.pub_id)
  }

  return <MessageModalLayout onClose={onCloseWithPublication} orders={orders} />;
}

export default MessageModal;