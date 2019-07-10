import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { listOrders, resetListOrders, deleteOrder } from 'redux/orders/actionCreators';

import OrdersLayout from './layout';
import useAuth from 'hooks/useAuth';

function Orders() {
  const orders = useSelector(state => state.orders.list);
  const dispatch = useDispatch();
  const auth = useAuth();
  const [messageModal, setMessageModal] = useState(null);
  const deleteHandler = (orderId) => () => {
    dispatch(deleteOrder(auth.user.id, orderId));
  }

  useEffect(() => {
    if(!orders.success) {
      dispatch(listOrders(auth.user.id, 0, 30));
    }
  }, [])

  return <OrdersLayout orders={orders.orders} loading={orders.loading} messageModal={messageModal} setMessageModal={setMessageModal} onDelete={deleteHandler}/>;
}

export default Orders;