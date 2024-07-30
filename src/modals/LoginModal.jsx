import React, { useContext, useState } from 'react'
import { Modal, Button, Form } from 'react-bootstrap'
import { LoginContext } from '../contexts/LoginContextProvider'
import JoinModal from './JoinModal';

const LoginModal = ({ show, onHide }) => {

    const { login } = useContext(LoginContext);
    const [JoinModalOn, setJoinModalOn] = useState(false);

    const onLogin = (e) => {
        //데이터 셋팅

        e.preventDefault();

        const form = e.target;
        const loginId = form.loginId.value;
        const password = form.password.value;

        // 로그인 함수 호출 후 성공 여부를 확인
        login(loginId, password, onHide);

    }

    return (
        <>
            <JoinModal
                show={JoinModalOn}
                onHide={() => setJoinModalOn(false)}
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
                    <Form className='login-form' onSubmit={(e) => onLogin(e)}>
                        <Form.Group className="mb-3" controlId="formBasicLoginId">
                            <Form.Label>Login ID</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter login ID"
                                name="loginId"
                                required
                                autoComplete="loginId"
                            // defaultValue={} // 필요시 추가
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Enter password"
                                name="password"
                                required
                                autoComplete="password"
                            // defaultValue={} // 필요시 추가
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit">
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
                    <Button onClick={() => {
                        onHide();
                        setJoinModalOn(true);
                        }}>Join</Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}

export default LoginModal