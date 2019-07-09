import React, { useRef, useEffect } from 'react';
import Message from './Message';

import styles from './styles.module.scss';
import CardContainer from 'components/ui/CardContainer';
import Button from 'components/ui/Button';
import Input from 'components/ui/Input';

import msgSendIcon from 'assets/action_icons/msg-send.svg';

function MessagesLayout({ messages, title, currentUserId, onSubmit, message }) {
  const bottomRef = useRef(null);
  const scrollToBottom = () => {
    bottomRef.current.scrollIntoView({ behavior: "smooth" })
  }
  useEffect(scrollToBottom, [messages.length]);

  const unseen = messages.filter(m => !m.seen).map(m => <Message key={m.id} message={m} sent={m.from.id === currentUserId} />)
  const seen = messages.filter(m => m.seen).map(m => <Message key={m.id} message={m} sent={m.from.id === currentUserId} />)

  const { valid, validatable, ...messageProps } = message;

  return (
    <CardContainer className={styles.messagesContainer}>
      <h1 className="txt-medium txt-black mb-32">{title}</h1>
      <div className={styles.chat}>
        <ul className={styles.messageList}>
          {seen}
        </ul>
        <ul className={styles.messageList}>
          {unseen}
        </ul>
        <div ref={bottomRef} />
      </div>
      <form onSubmit={onSubmit} className="row mt-16">
        <Input {...messageProps} className="flex-grow mr-8" />
        <Button className={styles.sendButton}><img src={msgSendIcon} alt="send" /></Button>
      </form>
    </CardContainer>
  );
}

export default MessagesLayout;