import React from 'react';
import Message from './Message';

import styles from './styles.module.scss';
import CardContainer from 'components/ui/CardContainer';
import Button from 'components/ui/Button';
import Input from 'components/ui/Input';

import msgSendIcon from 'assets/action_icons/msg-send.svg';

function MessagesLayout() {
  return (
    <CardContainer className={styles.messagesContainer}>
      <div className={styles.chat}>
        <ul className={styles.messageList}>
          <Message message={{message: 'Hola', date: '2019-07-05T20:50:03.403-03:00'}} sent />
          <Message message={{message: 'Hola!', date: '2019-07-05T20:50:03.403-03:00'}} />
          <Message message={{message: 'Como andas?', date: '2019-07-05T20:50:03.403-03:00'}} />
          <Message message={{message: 'Todo bien! Vos?', date: '2019-07-05T20:50:03.403-03:00'}} sent />
          <Message message={{message: 'Bien bien!', date: '2019-07-05T20:50:03.403-03:00'}} />
        </ul>
        <span className="row w100 center mt-24 mb-24 txt-normal txt-blue">Mensajes nuevos</span>
        <ul className={styles.messageList}>
          <Message message={{message: 'Hola', date: '2019-07-05T20:50:03.403-03:00'}} sent />
          <Message message={{message: 'Hola!', date: '2019-07-05T20:50:03.403-03:00'}} />
        </ul>
      </div>
      <div className="row mt-16">
        <Input className="flex-grow mr-8" />
        <Button className={styles.sendButton}><img src={msgSendIcon} alt="send" /></Button>
      </div>
    </CardContainer>
  );
}

export default MessagesLayout;