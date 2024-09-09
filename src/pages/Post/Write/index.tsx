import React from 'react'

//component: 게시물 작성 화면 컴포넌트
export default function PostWrite() {

    //state: 본문 영역 ref 상태

    //render: 게시물 작성 화면 컴포넌트 렌더링
    return (
        <div id ='post-write-wrapper'>
            <div className='post-write-container'>
                <div className='post-write-box'>
                    <div className='post-write-title-box'>
                        <input className='post-write-title-input' type="text" placeholder='제목을 작성해 주세요.'/>
                    </div>
                    <div className='divider'></div>
                    <div className='post-write-content-box'>
                        <textarea className='post-write-content-textarea' placeholder='본문을 작성해 주세요.' />
                        <div className='icon-button'>
                            <div className='icon image-box-light-icon'></div>
                        </div>
                        <input type='file' accept='image/*' style={{display : 'none'}}/>
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
