import React, { useState } from 'react'
import { Modal, Button, Form } from 'react-bootstrap'

const JoinModal = ({ show, onHide }) => {

  const [password, setPassword] = useState('');
  const [passwordCheck, setPasswordCheck] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (password !== passwordCheck) {
      setError(`비밀번호가 일치하지 않습니다.`);
    } else {
      setError(``);
    }
  }

  return (
    <Modal
      show={show}
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
        <Form onSubmit={handleSubmit}>
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
              value={password}
              onChange={(e) => setPassword(e.target.value)} //비밀번호 상태 업데이트
            />
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicPasswordCheck">
            <Form.Label>비밀번호 확인</Form.Label>
            <Form.Control
              type="password"
              placeholder="Enter Password"
              name="passwordCheck"
              value={passwordCheck}
              onChange={(e)=>setPasswordCheck(e.target.value)} //비밀번호 확인 상태 업데이트
            />
            {error && <Form.Text className='text-danger'>{error}</Form.Text>}
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