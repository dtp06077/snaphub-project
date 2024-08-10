import React from 'react';
import './style.css';

//component : Post List Item 컴포넌트
export default function PostListItem() {

    //render : Post List Item 컴포넌트 렌더링
  return (
    <div className='post-list-item'>
        <div className='post-list-item-main-box'>
            <div className='post-list-item-top'>
                <div className='post-list-item-profile-box'>
                    <div className='post-list-item-profile-image'></div>
                </div>
                <div className='post-list-item-write-box'>
                    <div className='post-list-item-name'></div>
                    <div className='post-list-item-write-datetime'></div>
                </div>
            </div>
            <div className='post-list-item-middle'>
                <div className='post-list-item-title'></div>
                <div className='post-list-item-content'></div>
            </div>
            <div className='post-list-item-bottom'>
                <div className='post-list-item-counts'></div>
            </div>
        </div>
        <div className='post-list-item-image-box'>
            <div className='post-list-item-image'></div>
        </div>
    </div>
  )
}
