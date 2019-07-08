import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import usePolling from 'hooks/usePolling';
import useAuth from 'hooks/useAuth';
import {
  listOrderMessages,
  listUnseenOrderMessages,
  listPublicationMessages,
  listUnseenPublicationMessages,
  sendMessageToOrder,
  sendMessageToPublication,
} from 'redux/messages/actionCreators';

import MessagesLayout from './layout';
import useFormInput from 'hooks/useFormInput';

function Messages({ publicationId, userId }) {
  const auth = useAuth();
  const dispatch = useDispatch();
  const messages = useSelector(state => state.messages.list);

  /** action creation  */
  const loadMore = userId ? listUnseenPublicationMessages(publicationId, userId) : listUnseenOrderMessages(auth.user.id, publicationId);
  const loadInitial = userId ? listPublicationMessages(publicationId, userId) : listOrderMessages(auth.user.id, publicationId);
  const sendMessage = userId ? sendMessageToPublication(publicationId, userId) : sendMessageToOrder(auth.user.id, publicationId);

  /** form */
  const form = {
    message: useFormInput('')
  }
  const sendHandler = e => {
    e.preventDefault();
    dispatch(sendMessage(form.message.value));
  }
  useEffect(() => { dispatch(loadInitial) }, []);
  usePolling(() => {
    dispatch(loadMore);
  }, 2000);

  return <MessagesLayout title="Giuliano" messages={messages.messages} scroll={10} currentUserId={auth.user.id} {...form} onSubmit={sendHandler} />;
}

export default Messages;