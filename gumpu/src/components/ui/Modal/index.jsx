import React, { useRef } from 'react';

import useOutsideClick from 'hooks/useOutsideClick';
import CardContainer from 'components/ui/CardContainer';

import styles from './styles.module.scss';

function Modal({ children, onClose }) {
  const wrapperRef = useRef(null);
  useOutsideClick(wrapperRef, onClose);

  return (
    <div className={`${styles.modalContainer} animated fadeIn`}>
      <div ref={wrapperRef}>
        <CardContainer className="animated slideInDown">{children}</CardContainer>
      </div>
    </div>
  );
}

export default Modal;