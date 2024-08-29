import React from 'react'

//component: 게시물 작성 화면 컴포넌트
export default function PostWrite() {

    //render: 게시물 작성 화면 컴포넌트 렌더링
    return (
        <div id ='post-write-wrapper'>
            <div className='post-write-container'>
                <div className='post-write-box'>
                    <div className='post-write-title-box'>
                        <input className='post-write-title-input' type="text" />
                    </div>
                    <div className='divider'></div>
                    <div className='post-write-content-box'>
                        <textarea className='post-write-content-textarea' />
                        <div className='icon-button'>
                            <div className='icon image-box-light-icon'></div>
                        </div>
                        <input />
                    </div>
                    <div className='post-write-images-box'>
                        <div className='post-write-image-box'>
                            <img className='post-write-image' />
                            <div className='icon-button image-close'>
                                <div className='icon close-icon'></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
