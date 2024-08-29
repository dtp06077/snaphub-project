import React, { useContext, useState } from 'react'
import { Modal, Button, Form, InputGroup } from 'react-bootstrap'
import defaultImage from '../assets/image/default-profile-image.png';
import { joinRequest, loginIdCheckRequest, nameCheckRequest } from '../apis';
import { EventModalContext } from '../contexts/EventModalProvider';

const JoinModal = ({ show, onHide, onJoinComplete }) => {

  const [loginId, setLoginId] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [telNumber, setTelNumber] = useState('');
  const [passwordCheck, setPasswordCheck] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [loginIdError, setLoginIdError] = useState('');
  const [nameError, setNameError] = useState('');
  const [isLoginIdChecked, setIsLoginIdChecked] = useState(false);
  const [isNameChecked, setIsNameChecked] = useState(false);
  const [isAgreed, setIsAgreed] = useState(false);

  const [profileImage, setProfileImage] = useState(defaultImage);
  const [previewImage, setPreviewImage] = useState(defaultImage);

  const { showModal } = useContext(EventModalContext);
  
  const onJoin = async (e) => {
    e.preventDefault();

    // 아이디 입력 체크
    if (!loginId) {
      showModal("아이디 미입력", "아이디를 입력해 주시길 바랍니다.")
      return;
    }

    // 아이디 중복 확인 체크
    else if (!isLoginIdChecked) {
      alert('아이디 중복 확인을 해 주시길 바랍니다.');
      return;
    }

    // 닉네임 입력 체크
    else if (!name) {
      alert('닉네임을 입력해 주시길 바랍니다.');
      return;
    }

    // 닉네임 중복 확인 체크
    else if (!isNameChecked) {
      alert('닉네임 중복 확인을 해 주시길 바랍니다.');
      return;
    }

    // 비밀번호 일치 여부 체크
    else if (!password) {
      alert(`비밀번호를 입력해 주시길 바랍니다.`);
      return;
    }

    // 비밀번호 길이 체크
    else if (password.length < 7 || password.length > 18) {
      alert(`비밀번호는 7자리 이상 18자리 이하의 길이여야 합니다.`);
      return;
    }

    // 비밀번호 확인 입력 체크
    else if (!passwordCheck) {
      alert(`비밀번호 확인을 입력해 주시길 바랍니다.`);
      return;
    }

    // 비밀번호 일치 여부 체크
    else if (password !== passwordCheck) {
      setPasswordError(`비밀번호가 일치하지 않습니다.`);
      return;
    }

    // 휴대폰 번호 입력 체크
    else if (!telNumber) {
      alert(`휴대폰 번호를 입력해 주시길 바랍니다.`);
      return;
    }

    if (!validatePhoneNumber(telNumber)) {
      alert('휴대폰 번호는 010-xxxx-xxxx 형식이어야 합니다.');
      return;
    }

    const form = e.target;
    const email = form.email.value;
    const address = form.address.value;
    const profile = profileImage;
    const agreedPersonal = isAgreed;

    const requestBody = {
      loginId, password, email, name, profile, telNumber, address, agreedPersonal
    };

    //회원가입 API 호출
    const result = await joinRequest(requestBody);
    console.log(isAgreed)

    if (result) {
      const { response, responseBody } = result;

      if (response.status === 200 && responseBody.code === 'SU') {
        console.log(`회원가입 성공!`);
        alert(`회원가입에 성공하였습니다.`);
        onJoinComplete();
        handleClose(); // 회원가입 모달 닫기
      }
      else if (response.status === 400) {
        console.log(`회원가입 실패`);
        alert(`입력 형식에 맞추어서 다시 작성해 주시기 바랍니다.`);
      }
    }

    else {
      console.log('네트워크 오류 또는 서버 응답 없음');
      alert("네트워크 또는 서버에 오류가 발생하였습니다.");
      return;
    }
  }

  //아이디 중복 확인
  const checkDuplicateLoginId = async () => {

    const response = await loginIdCheckRequest(loginId); // 로그인 ID 체크 API 호출

    if (response) {
      if (response.status === 200) {
        const message = response.data; // 응답 데이터
        handleResponseMessage(message, 'loginId');
      }
      else {
        handleError(response, 'loginId');
      }
    }
    else {
      setLoginIdError('서버에 연결할 수 없습니다.')
    }
  };

  //닉네임 중복 확인
  const checkDuplicateName = async () => {

    const response = await nameCheckRequest(name); // 닉네임 체크 API 호출

    if (response) {
      if (response.status === 200) {
        const message = response.data; // 응답 데이터
        handleResponseMessage(message, 'name');
      }
      else {
        handleError(response, 'name');
      }
    }
    else {
      setNameError('서버에 연결할 수 없습니다.')
    }
  };

  // 응답 상태에 따라 메시지 처리
  const handleResponseMessage = (message, type) => {
    if (type === 'loginId') {
      setLoginIdError('사용 가능한 아이디입니다.');
      setIsLoginIdChecked(message.code === 'SU');
    } else if (type === 'name') {
      setNameError('사용 가능한 닉네임입니다.');
      setIsNameChecked(message.code === 'SU');
    }
  };

  // 응답 상태에 따라 메시지 처리
  const handleError = (response, type) => {
    //서버가 응답한 경우
    if (response.status === 400) {
      if (type === 'loginId') {
        setLoginIdError('이미 사용 중인 아이디입니다.');
      } else if (type === 'name') {
        setNameError('이미 사용 중인 닉네임입니다.');
      }
    }
    else {
      if (type === 'loginId') {
        setLoginIdError('알 수 없는 오류가 발생했습니다.')
      } else if (type === 'name') {
        setNameError('알 수 없는 오류가 발생했습니다.')
      }
    }
  }
  //휴대폰 번호 형식 확인
  const validatePhoneNumber = (number) => {
    const regex = /^010-\d{4}-\d{4}$/; // 010-xxxx-xxxx 형식의 정규 표현식
    return regex.test(number);
  };

  //프로필 이미지 변경
  const handleImageChange = (e) => {
    const file = e.target.files[0];
    console.log(file);

    if (file) {
      setProfileImage(file);
      const filePreviewUrl = URL.createObjectURL(file);
      console.log(`preview = ${filePreviewUrl}`);

      setPreviewImage(filePreviewUrl);
    }
  };

  //프로필 이미지 삭제
  const handleRemoveImage = () => {
    setPreviewImage(defaultImage);
  };

  //정보 입력란 초기화
  const resetForm = () => {
    setLoginId('');
    setPassword('');
    setPasswordCheck('');
    setTelNumber('');
    setIsAgreed(false);
    setPasswordError('');
    setLoginIdError('');
    setIsLoginIdChecked(false);
    setName('');
    setIsNameChecked(false);
    setNameError('');
    setPreviewImage(defaultImage);
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
                <Button variant="outline-secondary" id="button-addon2" size='sm' onClick={checkDuplicateLoginId}>
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

            <Form.Group className="mb-3" controlId="formBasicName">
              <Form.Label>닉네임</Form.Label>
              <InputGroup>
                <Form.Control
                  type="text"
                  placeholder="Enter name"
                  name="name"
                  value={name}
                  onChange={(e) => setName(e.target.value)} //닉네임 상태 업데이트
                />
                <Button variant="outline-secondary" id="button-addon2" size='sm' onClick={checkDuplicateName}>
                  중복 확인
                </Button>
              </InputGroup>
              {nameError && <Form.Text className='text-danger'>{nameError}</Form.Text>}
              {!nameError && (
                <Form.Text className="text-muted">
                  닉네임 중복확인을 해주세요.
                </Form.Text>
              )}
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
              <Form.Label>비밀번호</Form.Label>
              <div className="inputbox">
                <Form.Control
                  type="password"
                  placeholder="Enter Password"
                  name="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)} // 비밀번호 상태 업데이트
                />
                <div className='icon-button' style={{ marginLeft: -40 }}>
                  <div className='icon eye-light-off-icon'></div>
                </div>
              </div>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPasswordCheck">
              <Form.Label>비밀번호 확인</Form.Label>
              <div className='inputbox'>
                <Form.Control
                  type="password"
                  placeholder="Enter Password"
                  name="passwordCheck"
                  value={passwordCheck}
                  onChange={(e) => setPasswordCheck(e.target.value)} // 비밀번호 확인 상태 업데이트
                />
                <div className='icon-button' style={{ marginLeft: -40 }}>
                  <div className='icon eye-light-off-icon'></div>
                </div>
              </div>
              {/* 에러 메시지를 입력란 아래에 위치 */}
              {passwordError && <Form.Text className='text-danger'>{passwordError}</Form.Text>}
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Label>휴대폰 번호</Form.Label>
              <Form.Control
                type="tel"
                placeholder="Enter phone number (ex. 010-xxxx-xxxx)"
                name="telNumber"
                value={telNumber}
                onChange={(e) => setTelNumber(e.target.value)} // 휴대폰 번호 상태 업데이트
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

            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Label>주소</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter address"
                name="address"
              />
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicProfileImage">
              <Form.Label>프로필 이미지</Form.Label>
              <input type="file" accept="image/*" name="profileImage" onChange={handleImageChange} />
              {previewImage && (
                <div>
                  <img src={previewImage} alt="Profile Preview" style={{ width: '100px', height: '100px', objectFit: 'cover', marginTop: '10px' }} />
                  <Button variant="outline-danger" onClick={handleRemoveImage} style={{ marginTop: '10px' }}>
                    Remove Image
                  </Button>
                </div>
              )}
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Check
                type="checkbox"
                label="개인정보 제공에 동의합니다."
                name="isAgreed"
                checked={isAgreed} // 상태에 따라 체크 여부 설정
                onChange={(e) => setIsAgreed(e.target.checked)} // 체크된 상태에 따라 true/false 설정
              />
            </Form.Group>


            <Button className='modal-button' variant="primary" type="submit">
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