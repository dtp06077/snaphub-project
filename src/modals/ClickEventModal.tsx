import React from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

interface ClickEventModalProps {
  show: boolean;
  onHide: () => void;
  title: string;
  message: string;
  onConfirm: () => void; // 예 버튼 클릭 시 호출되는 함수
  onCancel: () => void;  // 아니오 버튼 클릭 시 호출되는 함수
}

const ClickEventModal: React.FC<ClickEventModalProps> = ({
  show,
  onHide,
  title,
  message,
  onConfirm,
  onCancel,
}) => {
  return (
    <Modal
      show={show}
      onHide={onHide}
      backdrop="static"
      keyboard={false}
    >
      <Modal.Header closeButton>
        <Modal.Title>{title}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {message}
      </Modal.Body>
      <Modal.Footer>
        <Button className="modal-button" variant="primary" onClick={onCancel}>
          Cancel
        </Button>
        <Button className="modal-button" variant="primary" onClick={onConfirm}>
          OK
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default ClickEventModal;