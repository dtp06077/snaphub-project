import React, { useContext, useState } from 'react'
import { LoginContext } from '../../contexts/LoginContextProvider'
import JoinModal from '../../modals/JoinModal'
import { Button, Container, Nav, Navbar } from 'react-bootstrap'
import './Header.css';

const Header = () => {

  //isLogin : 로그인 여부 - 로그인(true), 비로그인(false)
  //logout() : 로그아웃 함수 -> setLogin(false)
  const { isLogin, login, logout } = useContext(LoginContext);
  const [JoinModalOn, setJoinModalOn] = useState(false);

  return (
    <>
      <JoinModal
        show={JoinModalOn}
        onHide={() => setJoinModalOn(false)}
      />
      <header>
        <Navbar style={{ height: '100px', padding: '10px 0' }} bg="dark" data-bs-theme="dark">
          <Container>
            <Navbar.Brand className="custom-nav-brand" href="/">SnapHub</Navbar.Brand>
            <Nav className="ml-auto">
              {!isLogin ? (
                <>
                  <Nav.Link className='custom-nav-link'>
                    로그인
                  </Nav.Link>
                  <Nav.Link className='custom-nav-link'
                    onClick={() => setJoinModalOn(true)}>
                    회원가입
                  </Nav.Link>
                  <Nav.Link className='custom-nav-link'>
                    소개
                  </Nav.Link>
                </>
              ) : (
                <>
                  <Nav.Link className='custom-nav-link'>
                    마이페이지
                  </Nav.Link>
                  <Nav.Link className='custom-nav-link'
                    onClick={() => logout()}
                  >
                    로그아웃
                  </Nav.Link>
                </>
              )}
            </Nav>
          </Container>
        </Navbar>

      </header>
    </>
  )
}

export default Header