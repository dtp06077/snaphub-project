import React, { useContext, useEffect, useState, } from 'react'
import { LoginContext } from '../../contexts/LoginContextProvider'
import LoginModal from '../../modals/LoginModal'
import { Container, Nav, Navbar } from 'react-bootstrap'
import './style.css';
import { MAIN_PATH, SEARCH_PATH } from '../../constants';
import { useNavigate, useParams } from 'react-router-dom';


//component: 헤더 레이아웃
const Header = () => {

  //isLogin : 로그인 여부 - 로그인(true), 비로그인(false)
  //logout() : 로그아웃 함수 -> setLogin(false)
  const { isLogin, logout, profileImage } = useContext(LoginContext);
  const [loginModalOn, setLoginModalOn] = useState(false);

  //component: 검색 버튼 컴포넌트
  const SearchButton = () => {

    const navigate = useNavigate();

    const [word, setWord] = useState('');

    const { searchWord } = useParams();

    //event handler: 검색 아이콘 클릭 이벤트 처리 함수
    const onSearchButtonClickHandler = () => {
      if (word !== '') {
        navigate(SEARCH_PATH(word));
      }
      else {
        alert("검색어를 입력하세요.")
      }
    }

    //event handler: 검색어 키 이벤트 처리 함수
    const onwordKeyDownHandler = (e) => {
      if (e.key === 'Enter') {
        onSearchButtonClickHandler(); // 엔터 키 눌렀을 때 검색 호출
      }
    }

    //effect: 검색어 path variable 변경 될 때마다 실행될 함수
    useEffect(() => {
      if (searchWord) setWord(searchWord);
    }, [searchWord]);

    return (
      <div className='header-search-input-box'>
        <input
          className='header-search-input'
          type='text'
          placeholder='Please enter a search term.'
          value={word}
          onChange={(e) => setWord(e.target.value)}
          onKeyDown={onwordKeyDownHandler}
        />
        <div className='icon-button' onClick={onSearchButtonClickHandler}>
          <div className='icon search-light-icon'></div>
        </div>
      </div>
    );
  }

  //render: 헤더 레이아웃 렌더링
  return (
    <>
      <LoginModal
        show={loginModalOn}
        onHide={() => setLoginModalOn(false)}
        handleJoinComplete={() => setLoginModalOn(true)}
      />
      <header>
        <Navbar className='header-nav-bar' style={{ height: '100px', padding: '10px 0' }} bg="dark" data-bs-theme="dark">
          <Container className='header-container'>
            <div className='header-left-box'>
              <div className='icon-box'>
                <div className='icon logo-light-icon'></div>
              </div>
              <Navbar.Brand className="header-logo" href={MAIN_PATH()}>SnapHub</Navbar.Brand>
            </div>
            <Nav className="header-right-box">
              {!isLogin ? (
                <>
                  <Nav.Link className='custom-nav-link'
                    onClick={() => setLoginModalOn(true)}>
                    login
                  </Nav.Link>
                  <Nav.Link>
                    <SearchButton />
                  </Nav.Link>
                  {/* 프로필 사진 추가 */}
                  <img
                    src={profileImage}
                    alt="Profile"
                    className="profile-image"
                    onClick={() => {/* 프로필 클릭 시 동작 */ }}
                  />
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
                  {/* 프로필 사진 추가 */}
                  <img
                    src={profileImage}
                    alt="Profile"
                    className="profile-image"
                    onClick={() => {/* 프로필 클릭 시 동작 */ }}
                  />
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