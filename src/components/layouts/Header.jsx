import React, { useContext, useState } from 'react'
import { LoginContext } from '../../contexts/LoginContextProvider'
import LoginModal from '../../modals/LoginModal'
import { Button, Container, Nav, Navbar } from 'react-bootstrap'
import './Header.css';

const Header = () => {

  //isLogin : 로그인 여부 - 로그인(true), 비로그인(false)
  //logout() : 로그아웃 함수 -> setLogin(false)
  const { isLogin, logout } = useContext(LoginContext);
  const [LoginModalOn, setLoginModalOn] = useState(false);

  return (
    <>
      <LoginModal
        show={LoginModalOn}
        onHide={() => setLoginModalOn(false)}
        handleJoinComplete={() => setLoginModalOn(true)}
      />
      <header>
        <Navbar style={{ height: '100px', padding: '10px 0' }} bg="dark" data-bs-theme="dark">
          <Container>
            <Navbar.Brand className="custom-nav-brand" href="/">SnapHub</Navbar.Brand>
            <Nav className="ml-auto">
              {!isLogin ? (
                <>
                  <Nav.Link className='custom-nav-link'
                    onClick={() => setLoginModalOn(true)}>
                    login
                  </Nav.Link>
                  <Nav.Link className='custom-nav-link'>
                    search
                  </Nav.Link>
                </>
              ) : (
                <>
                  <Nav.Link className='custom-nav-link'>
                    mypage
                  </Nav.Link>
                  <Nav.Link className='custom-nav-link'>
                    search
                  </Nav.Link>
                  <Nav.Link className='custom-nav-link'
                    onClick={() => logout()}
                  >
                    logout
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