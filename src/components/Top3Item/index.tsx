import React from 'react'
import './style.css'

//component: Top 3 List Item 컴포넌트
export default function Top3Item() {

  //render: Top 3 List Item 컴포넌트 렌더링
  return (
    <div className='top-3-list-item'>
      <div className='top-3-list-item-main-box'>
        <div className='top-3-list-item-top'>
          <div className='top-3-list-item-profile-box'>
            <div className='top-3-list-item-profile-image' style={{backgroundImage: `url()`}}></div>  
          </div>
          <div className='top-3-list-item-write-box'>
            <div className='top-3-list-item-name'></div>
            <div className='top-3-list-item-post-datetime'></div>
          </div>
        </div>
        <div className='top-3-list-item-middle'>
          <div className='top-3-list-item-title'></div>
          <div className='top-3-list-item-content'></div>
        </div>
        <div className='top-3-list-item-bottom'>
          <div className='top-3-list-item-counts'></div>
        </div>
      </div>
    </div>
  )
}
