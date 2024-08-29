import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

const EventModal = ({show, onHide, title, message}) => {
  
  return (
      <Modal
        show={show}
        onHide={onHide}
        backdrop="static"
        keyboard={false}
        title={title}
        message={message}
      >
        <Modal.Header closeButton>
          <Modal.Title>{title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {message}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={()=>onHide()}>
            Close
          </Button>
          <Button variant="primary">Understood</Button>
        </Modal.Footer>
      </Modal>
  );
}

export default EventModal;