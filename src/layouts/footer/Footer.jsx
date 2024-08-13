import React from 'react'
import { Container, Row, Col } from 'react-bootstrap'
import './Footer.css'

const Footer = () => {
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
              <div className='icon-button'>
                <div className='icon insta-icon'></div>
              </div>
              <div className='icon-button'>
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