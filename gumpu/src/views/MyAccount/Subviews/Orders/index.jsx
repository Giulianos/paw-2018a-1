import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { listOrders } from 'redux/orders/actionCreators';

import OrdersLayout from './layout';
import useAuth from 'hooks/useAuth';

function Orders() {
  const orders = useSelector(state => state.orders.list);
  const dispatch = useDispatch();
  const auth = useAuth();

  useEffect(() => {
    dispatch(listOrders(auth.user.id, 0, 10));
  }, [])

  return <OrdersLayout orders={orders.orders} loading={orders.loading} />;
}

export default Orders;