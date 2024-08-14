import React from 'react'
import { Container, Row, Col } from 'react-bootstrap'
import './style.css'

//component: footer 컴포넌트
const Footer = () => {
  
  //인스타 아이콘 버튼 클릭 이벤트 처리
  const onInstaIconClickHandler = () => {
    window.open('http://www.instagram.com');
  };

  //네이버 블라고 아이콘 버튼 클릭 이벤트 처리
  const onNaverIconClickHandler = () => {
    window.open('http://blog.naver.com');
  }

  //render: footer 레이아웃 렌더링
  return (
    <footer>
        <Container className='footer-container'>
          <div className='footer-top'>
          <Row>
          <Col className='footer-logo-box'>
              <div className='icon-box'>
              <div className='icon logo-light-icon'></div>
              </div>
              <div className='footer-logo-text'>{'SnapHub'}</div>
            </Col>
            <Col className='footer-link-box'>
              <div className='footer-email-link'>{'dtp06077@gmail.com'}</div>
              <div className='icon-button' onClick={onInstaIconClickHandler}>
                <div className='icon insta-icon'></div>
              </div>
              <div className='icon-button' onClick={onNaverIconClickHandler}>
                <div className='icon naver-blog-icon'></div>
              </div>
            </Col>
            </Row>
          </div>
            <div className='footer-bottom'>
              <div className='footer-copyright'>&copy; 2024 SnapHub. All Rights Reserved.</div>
            </div>
        </Container>
    </footer>
  );
};

export default Footer