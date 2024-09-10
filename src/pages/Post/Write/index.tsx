import React, { ChangeEvent, useEffect, useRef, useState } from 'react'
import { usePostStore } from '../../../stores';
import './style.css'

//component: 게시물 작성 화면 컴포넌트
export default function PostWrite() {

    //state : 제목 영역 참조 상태
    const titleRef = useRef<HTMLTextAreaElement | null>(null);
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

    //event handler: 제목 변경 이벤트 처리
    const onTitleChangeHandler = (event: ChangeEvent<HTMLTextAreaElement>) => {
        const { value } = event.target;
        setTitle(value);

        if (!titleRef.current) return;
        titleRef.current.style.height = 'auto';
        titleRef.current.style.height = `${titleRef.current.scrollHeight}px`;
    }

    //event handler: 내용 변경 이벤트 처리
    const onContentChangeHandler = (event: ChangeEvent<HTMLTextAreaElement>) => {
        const { value } = event.target;
        setContent(value);

        if (!contentRef.current) return;
        contentRef.current.style.height = 'auto';
        contentRef.current.style.height = `${contentRef.current.scrollHeight}px`;
    }

    //event handler: 이미지 변경 이벤트 처리
    const onImageChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files || !event.target.files.length) return;
        const file = event.target.files[0];
        const imageUrl = URL.createObjectURL(file);
        const newImageUrls = imageUrls.map(item => item);
        newImageUrls.push(imageUrl);

        setImageUrls(newImageUrls);

        const newPostImageFileList = postImageFileList.map(item => item);
        newPostImageFileList.push(file);
        setPostImageFileList(newPostImageFileList);

        if(!imageInputRef.current) return;
        imageInputRef.current.value = '';
    }

    //event handler: 이미지 업로드 버튼 클릭 이벤트 처리
    const onImageUploadButtonClickHandler = () => {
        if (!imageInputRef.current) return;
        imageInputRef.current.click();
    }

    //event handler: 이미지 닫기 버튼 클릭 이벤트 처리
    const onImageCloseButtonClickHandler = (deleteIndex: number) => {
        if (!imageInputRef.current) return;
        imageInputRef.current.value = '';

        const newImageUrls = imageUrls.filter((url, index) => index !== deleteIndex);
        setImageUrls(newImageUrls);

        const newPostImageFileList = postImageFileList.filter((file, index) => index !== deleteIndex);
        setPostImageFileList(newPostImageFileList);
    }

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
                        <textarea ref={titleRef} className='post-write-title-textarea' rows={1} placeholder='제목을 작성해 주세요.' value={title} onChange={onTitleChangeHandler} />
                    </div>
                    <div className='divider'></div>
                    <div className='post-write-content-box'>
                        <textarea ref={contentRef} className='post-write-content-textarea' placeholder='본문을 작성해 주세요.' value={content} onChange={onContentChangeHandler} />
                        <div className='icon-button' onClick={onImageUploadButtonClickHandler}>
                            <div className='icon image-box-light-icon'></div>
                        </div>
                        <input ref={imageInputRef} type='file' accept='image/*' style={{ display: 'none' }} onChange={onImageChangeHandler} />
                    </div>
                    <div className='post-write-images-box'>
                        {imageUrls.map((imageUrl, index) =>
                            <div className='post-write-image-box'>
                                <img className='post-write-image' src={imageUrl}/>
                                <div className='icon-button image-close' onClick={() => onImageCloseButtonClickHandler(index)}>
                                    <div className='icon close-icon'></div>
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}
