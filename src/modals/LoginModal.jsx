import React, { useContext, useState } from 'react'
import { Modal, Button, Form } from 'react-bootstrap'
import { LoginContext } from '../contexts/LoginContextProvider'
import JoinModal from './JoinModal';
import './style.css';

const LoginModal = ({ show, onHide, handleJoinComplete }) => {

    const { login } = useContext(LoginContext);
    const [JoinModalOn, setJoinModalOn] = useState(false);
    const [loginId, setLoginId] = useState('');
    const [password, setPassword] = useState('');

    const onLogin = (e) => {
        //데이터 셋팅

        e.preventDefault();

        // 로그인 함수 호출 후 성공 여부를 확인
        login(loginId, password, onHide, resetForm);

    }

    const resetForm = () => {
        setLoginId('');
        setPassword('');
    }


    return (
        <>
            <JoinModal
                show={JoinModalOn}
                onHide={() => setJoinModalOn(false)}
                onJoinComplete={handleJoinComplete}
            />
            <Modal
                show={show}
                onHide={onHide}
                size="lg"
                aria-labelledby="contained-modal-title-vcenter"
                centered
            >
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        Login
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={(e) => onLogin(e)}>
                        <Form.Group className="mb-3" controlId="formBasicLoginId">
                            <Form.Label>아이디</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter your login ID"
                                name="loginId"
                                value={loginId}
                                onChange={(e) => setLoginId(e.target.value)} // onChange 핸들러 추가
                                required
                                autoComplete="loginId"
                            // defaultValue={} // 필요시 추가
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>비밀번호</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Enter your password"
                                name="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)} // onChange 핸들러 추가
                                required
                                autoComplete="password"
                            // defaultValue={} // 필요시 추가
                            />
                        </Form.Group>
                        <Button className='custom-button' variant="primary" type="submit">
                            Login
                        </Button>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <div style={{
                        textAlign: "center", textDecoration: "underline",
                        marginRight: "10px", fontWeight: "bold"
                    }}>
                        아직 회원이 아니십니까?
                    </div>
                    <Button className='custom-button' onClick={() => {
                        onHide();
                        resetForm();
                        setJoinModalOn(true);
                        }}>sign up</Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}

export default LoginModal