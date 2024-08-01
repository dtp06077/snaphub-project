import React, { useState } from 'react'
import { Modal, Button, Form, InputGroup } from 'react-bootstrap'
import { checkLoginId, join } from '../apis/auth';

const JoinModal = ({ show, onHide }) => {

  const [loginId, setLoginId] = useState('');
  const [password, setPassword] = useState('');
  const [passwordCheck, setPasswordCheck] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [loginIdError, setLoginIdError] = useState('');
  const [isLoginIdChecked, setIsLoginIdChecked] = useState(false);

  const onJoin = async (e) => {
    e.preventDefault();

    // 아이디 입력 체크
    if (!loginId) {
      alert('아이디를 입력하세요.');
      return;
    }

     // 아이디 중복 확인 체크
     else if (!isLoginIdChecked) {
      alert('아이디 중복 확인을 해주세요.');
      return;
    }

    // 비밀번호 일치 여부 체크
    else if (!password) {
      alert(`비밀번호를 입력하세요.`);
      return;
    }

    // 비밀번호 확인 입력 체크
    else if (!passwordCheck) {
      alert(`비밀번호 확인을 입력하세요.`);
      return;
    }

    // 비밀번호 일치 여부 체크
    else if (password !== passwordCheck) {
      setPasswordError(`비밀번호가 일치하지 않습니다.`);
      return;
    }
  
    const form = e.target;
    const name = form.name.value;
    const email = form.email.value;

    console.log(name, email, loginId, password);

    let response;
    let data;

    try {
      response = await join({ name, email, loginId, password });
    } catch (error) {
      console.error(`${error}`);
      console.error(`회원가입 요청 중 에러가 발생하였습니다.`);
      return
    }

    data = response.data;
    const status = response.status;
    console.log(`data : ${data}`);
    console.log(`status : ${status}`);

    if (status === 200) {
      console.log(`회원가입 성공!`);
      alert(`회원가입에 성공하였습니다.`);
       handleClose(); // 회원가입 모달 닫기
    }
    else {
      console.log(`회원가입 실패`);
      alert(`회원가입에 실패하였습니다.`);
    }
  }

  const idCheck = async () => {
    
    let response;
    let message;
    try {
      response = await checkLoginId(loginId);
        message = await response.data;
        setLoginIdError(message);
        setIsLoginIdChecked(message === '사용 가능한 아이디입니다.'); // 중복 확인 성공 여부 설정
    } catch (error) {
      if (error.response) {
        // 서버가 응답한 경우
        message = error.response.data;
        setLoginIdError(message);
    } else if (error.request) {
        // 요청이 이루어졌으나 응답이 없는 경우
        setLoginIdError('서버에 연결할 수 없습니다.');
    } else {
        // 다른 오류
        setLoginIdError('알 수 없는 오류가 발생했습니다.');
    
      }
    }
  }

  const resetForm = () => {
    setLoginId('');
    setPassword('');
    setPasswordCheck('');
    setPasswordError('');
    setLoginIdError('');
    setIsLoginIdChecked(false);
  };

  const handleClose = () => {
    resetForm(); // 모달 닫힐 때 상태 초기화
    onHide(); // 모달 숨기기
  };

  return (
    <Modal
      show={show}
      onHide={handleClose}
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
        <Form onSubmit={(e) => onJoin(e)}>
          <Form.Group className="mb-3" controlId="formBasicLoginId">
            <Form.Label>아이디</Form.Label>
            <InputGroup>
              <Form.Control
                type="text"
                placeholder="Enter login ID"
                name="loginId"
                value={loginId}
                onChange={(e) => setLoginId(e.target.value)} // 아이디 상태 업데이트
              />
              <Button variant="outline-secondary" id="button-addon2" size='sm' onClick={idCheck}>
                중복 확인
              </Button>
            </InputGroup>
            {loginIdError && <Form.Text className='text-danger'>{loginIdError}</Form.Text>}
            {!loginIdError && (
              <Form.Text className="text-muted">
                아이디 중복확인을 해주세요.
              </Form.Text>
            )}
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
              onChange={(e) => setPasswordCheck(e.target.value)} //비밀번호 확인 상태 업데이트
            />
            {passwordError && <Form.Text className='text-danger'>{passwordError}</Form.Text>}
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