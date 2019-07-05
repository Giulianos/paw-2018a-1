import React, { useRef } from 'react';

import useOutsideClick from 'hooks/useOutsideClick';

import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';

function Modal({ children, handleClose }) {
  const wrapperRef = useRef(null);
  useOutsideClick(wrapperRef, handleClose);

  return (
    <div className={styles.modalContainer}>
      <CardContainer>{children}</CardContainer>
    </div>
  );
}

export default Modal;