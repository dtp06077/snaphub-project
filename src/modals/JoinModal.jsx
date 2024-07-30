import React from 'react'
import { Modal, Button, Form } from 'react-bootstrap'

const JoinModal = ({ show, onHide }) => {
  return (
    <Modal
      show = {show}
      onHide={onHide}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          join
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
      <Form>
      <Form.Group className="mb-3" controlId="formBasicLoginId">
        <Form.Label>아이디</Form.Label>
        <Form.Control 
        type="text" 
        placeholder="Enter login ID"
        name="loginId"
        />
        <Form.Text className="text-muted">
          아이디 중복확인을 해주세요.
        </Form.Text>
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicPassword">
        <Form.Label>비밀번호</Form.Label>
        <Form.Control 
        type="password" 
        placeholder="Enter Password"
        name="password"
        />
      </Form.Group>
      
      <Form.Group className="mb-3" controlId="formBasicPasswordCheck">
        <Form.Label>비밀번호 확인</Form.Label>
        <Form.Control 
        type="password" 
        placeholder="Enter Password" 
        name="passwordCheck"
        />
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>이메일 주소</Form.Label>
        <Form.Control 
        type="email" 
        placeholder="Enter email"
        name="email"
        />
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicName">
        <Form.Label>닉네임</Form.Label>
        <Form.Control 
        type="text" 
        placeholder="Enter name"
        name="name"
        />
        <Form.Text className="text-muted">
          닉네임 중복확인을 해주세요.
        </Form.Text>
      </Form.Group>

      <Button variant="primary" type="submit">
        Submit
      </Button>
    </Form>
      </Modal.Body>
      <Modal.Footer>
      </Modal.Footer>
    </Modal>
  )
}

export default JoinModal