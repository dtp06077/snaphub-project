import React, { useEffect, useRef, useState } from 'react'
import { usePostStore } from '../../../stores';
import './style.css'

//component: 게시물 작성 화면 컴포넌트
export default function PostWrite() {

    //state : 본문 영역 참조 상태
    const contentRef = useRef<HTMLTextAreaElement | null>(null);
    //state : 이미지 입력 요소 참조 상태
    const imageInputRef = useRef<HTMLInputElement | null>(null);

    //state: 게시물 상태
    const { title, setTitle } = usePostStore();
    const { content, setContent } = usePostStore();
    const { postImageFileList, setPostImageFileList } = usePostStore();
    const { resetPost } = usePostStore();

    //state: 게시물 이미지 미리보기 URL 상태
    const [imageUrls, setImageUrls] = useState<string[]>([]);

    //effect: 마운트 실행 함수
    useEffect(() => {
        resetPost();
    }, []);

    //render: 게시물 작성 화면 컴포넌트 렌더링
    return (
        <div id='post-write-wrapper'>
            <div className='post-write-container'>
                <div className='post-write-box'>
                    <div className='post-write-title-box'>
                        <input className='post-write-title-input' type="text" placeholder='제목을 작성해 주세요.' value={title} />
                    </div>
                    <div className='divider'></div>
                    <div className='post-write-content-box'>
                        <textarea ref={contentRef} className='post-write-content-textarea' placeholder='본문을 작성해 주세요.' value={content} />
                        <div className='icon-button'>
                            <div className='icon image-box-light-icon'></div>
                        </div>
                        <input ref={imageInputRef} type='file' accept='image/*' style={{ display: 'none' }} />
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
