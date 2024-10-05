import React, { useContext, useEffect, useState, } from 'react'
import { LoginContext } from '../../contexts/LoginContextProvider'
import LoginModal from '../../modals/LoginModal'
import { Container, Nav, Navbar } from 'react-bootstrap'
import './style.css';
import { MAIN_PATH, SEARCH_PATH, USER_PATH, POST_WRITE_PATH, POST_DETAIL_PATH, POST_UPDATE_PATH } from '../../constants';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { usePostStore } from '../../stores';
import { useCookies } from 'react-cookie';
import { updatePostRequest, uploadFileRequest, uploadPostRequest } from '../../apis';
import { EventModalContext } from '../../contexts/EventModalProvider';


//component: 헤더 레이아웃
const Header = () => {

  //isLogin : 로그인 여부 - 로그인(true), 비로그인(false)
  const { isLogin, logout, profileImage, userInfo } = useContext(LoginContext);
  //logout() : 로그아웃 함수 -> setLogin(false)
  const [loginModalOn, setLoginModalOn] = useState(false);
  //state: 게시물 작성 창 상태
  const [uploadOn, setUploadOn] = useState(false);
  //state: 쿠키 상태
  const [cookies] = useCookies();
  //state: path 상태
  const { pathname } = useLocation();

  //context: 이벤트 모달 창
  const { showModal } = useContext(EventModalContext);

  //function: 네비게이트 함수
  const navigate = useNavigate();


  //component: 검색 버튼 컴포넌트
  const SearchButton = () => {

    //state: 검색어 상태
    const [word, setWord] = useState('');

    //state: 검색창 상태
    const [searchOn, setSearchOn] = useState(false);

    const { searchWord } = useParams();

    //event handler: 검색 아이콘 클릭 이벤트 처리 함수
    const onSearchButtonClickHandler = () => {
      if (word !== '') {
        navigate(SEARCH_PATH(word));
      }
      else {
        setSearchOn(false);
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
      if (searchWord) {
        setSearchOn(true);
        setWord(searchWord);
      }
    }, [searchWord]);

    if (searchOn) {
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
    else {
      return (
        <div className='header-nav-link' onClick={() => { setSearchOn(true) }}>
          search
        </div>
      )
    }
  }

  //component: 업로드 버튼 컴포넌트
  const UploadButton = () => {

    //state: 게시물 번호 path variable 상태
    const { postId } = useParams();
    //state: 게시물 상태
    const { title, content, postImageFileList, resetPost } = usePostStore();

    //function: uploadPostResponse 처리 함수
    const uploadPostResponse = (responseBody) => {
      if (!responseBody) return;

      const { code } = responseBody;
      if (code === 'AF' || code === "NU") {
        showModal('AUTHORIZATION Fail', '인증되지 않은 사용자 입니다.');
        navigate(MAIN_PATH());
        return;
      }

      if (code === 'VF') {
        showModal('Validation Fail', '검증되지 않은 게시물 입니다.');
        return;
      }

      if (code === 'DE') {
        showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
        return;
      }
      if(code !== 'SU') return;
      resetPost();
      if (!isLogin) return;
      const { loginId } = userInfo;

      showModal('Upload Success', '게시물 작성이 완료되었습니다.');
      setUploadOn(false);
      navigate(USER_PATH(loginId));
    }

    //function: UpdatePostresponse 처리 함수
    const updatePostResponse = (responseBody) => {
      if (!responseBody) return;

      const { code } = responseBody;
      if (code === 'AF' || code === "NU") {
        showModal('AUTHORIZATION Fail', '인증되지 않은 사용자 입니다.');
        navigate(MAIN_PATH());
        return;
      }

      if (code === 'VF' || code === 'NP') {
        showModal('Validation Fail', '검증되지 않은 게시물 입니다.');
        navigate(MAIN_PATH());
        return;
      }

      if (code === 'DE') {
        showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
        return;
      }
      if(code !== 'SU') return;
      if(!postId) return;
      navigate(POST_DETAIL_PATH(postId));
    }


    //event handler: 업로드 버튼 클릭 이벤트 처리
    const onUploadButtonClickHandler = async () => {
      const accessToken = cookies.accessToken;

      if (!accessToken) return;

      if (!title || !content) {
        showModal('Content Error', '제목과 내용은 필수입니다.');
        return;
      }
      const postImageList = [];

      for (const file of postImageFileList) {
        const data = new FormData();
        data.append('file', file);

        const url = await uploadFileRequest(data);
        if (url) postImageList.push(url);
      }

      const isWriterPage = (pathname === POST_WRITE_PATH());
      const requestBody = {
        title, content, postImageList
      }

      if (isWriterPage) {
        uploadPostRequest(requestBody, accessToken).then(uploadPostResponse);
      }
      else {
        if (!postId) return;
        updatePostRequest(postId, requestBody, accessToken).then(updatePostResponse);
      }
    }

    //event handler: 쓰기 버튼 클릭 이벤트 처리 함수
    const onWriteButtonClickHandler = () => {
      setUploadOn(true);
      navigate(POST_WRITE_PATH());
    }

    if (uploadOn === true || pathname===POST_UPDATE_PATH(postId)) {
      return <div className='header-nav-link' onClick={onUploadButtonClickHandler}>
        upload
      </div>
    }
    else {
      return <div className='header-nav-link' onClick={onWriteButtonClickHandler}>
      write
    </div>
    }
  }

  //component: 프로필 버튼 컴포넌트
  const ProfileButton = () => {

    const onMyPageButtonClickHandler = () => {
      if (isLogin) {
        const { loginId } = userInfo;
        navigate(USER_PATH(loginId));
        return;
      }
      return;
    };

    return <img
      src={profileImage}
      alt="Profile"
      className="header-profile-image"
      onClick={() => { onMyPageButtonClickHandler() }}
    />
  }
  //component: 로그인 버튼 컴포넌트
  const LoginButton = () => {
    return <div className='header-nav-link'
      onClick={() => setLoginModalOn(true)}>
      login
    </div>
  }

  //component: 로그아웃 버튼 컴포넌트
  const LogoutButton = () => {
    return <div className='header-nav-link'
      onClick={() => logout()}>
      logout
    </div>
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
        <Navbar className='header-nav-bar' bg="dark" data-bs-theme="dark">
          <Container className='header-container'>
            <div className='header-left-box'>
              <div className='icon-box'>
                <div className='icon logo-light-icon'></div>
              </div>
              <Navbar.Brand className="header-logo" href={MAIN_PATH()}>SnapHub</Navbar.Brand>
            </div>
            <Nav className="header-right-box">
              <Nav.Link>
                <SearchButton />
              </Nav.Link>
              {!isLogin ? (
                <Nav.Link>
                  <LoginButton />
                </Nav.Link>
              ) : (
                <>
                  <Nav.Link>
                    <UploadButton />
                  </Nav.Link>
                  <Nav.Link>
                    <LogoutButton />
                  </Nav.Link>
                </>
              )}
              <Nav.Link>
                <ProfileButton />
              </Nav.Link>
            </Nav>
          </Container>
        </Navbar>

      </header>
    </>
  )
}

export default Header