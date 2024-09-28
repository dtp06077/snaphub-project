import React from 'react'
import './style.css';

//component: 페이지네이션 컴포넌트
export default function Pagination() {

    //render: 페이지네이션 컴포넌트 렌더링
  return (
    <div id = 'pagination-wrapper'>
      <div className='pagination-change-link-box'>
        <div className='icon-box-small'>
          <div className='icon left-icon'></div>
        </div>
        <div className='pagination-change-link-text'>{'이전'}</div>
      </div>
      <div className='pagination-divider'>{'\|'}</div>

      <div className='pagination-text-active'>{1}</div>
      <div className='pagination-text'>{2}</div>

      <div className='pagination-divider'>{'\|'}</div>
      <div className='pagination-change-link-box'>
        <div className='pagination-change-link-text'>{'다음'}</div>
        <div className='icon-box-small'>
          <div className='icon right-icon'></div>
        </div>
      </div>
    </div>
  )
}
