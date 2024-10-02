import React, { ChangeEvent, useContext, useEffect, useRef, useState } from 'react'
import { useLoginUserStore, usePostStore } from '../../../stores';
import './style.css'
import { useNavigate, useParams } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import { MAIN_PATH } from '../../../constants';
import { getPostRequest } from '../../../apis';
import { GetPostResponseDto } from '../../../apis/response/post';
import { ResponseDto } from '../../../apis/response';
import { EventModalContext } from '../../../contexts/EventModalProvider';

//component: 게시물 수정 화면 컴포넌트
export default function PostUpdate() {

    //state : 제목 영역 참조 상태
    const titleRef = useRef<HTMLTextAreaElement | null>(null);
    //state : 본문 영역 참조 상태
    const contentRef = useRef<HTMLTextAreaElement | null>(null);
    //state : 이미지 입력 요소 참조 상태
    const imageInputRef = useRef<HTMLInputElement | null>(null);

    //zustand: 게시물 상태
    const { title, setTitle } = usePostStore();
    const { content, setContent } = usePostStore();
    const { postImageFileList, setPostImageFileList } = usePostStore();

    //context: 이벤트 모달 창
    const eventContext = useContext(EventModalContext);

    // eventContext가 undefined일 경우
    if (!eventContext) {
        throw new Error("이벤트 모달 창이 존재하지 않습니다.");
    }

    const { showModal } = eventContext;

    //state: 게시물 이미지 미리보기 URL 상태
    const [imageUrls, setImageUrls] = useState<string[]>([]);

    //state: 게시물 번호 path variable 상태
    const { postId } = useParams();

    //state: 로그인 유저 상태
    const { loginUser } = useLoginUserStore();

    //state: cookie 상태
    const [cookies] = useCookies();

    //function: 네비게이트 함수
    const navigate = useNavigate();

    //function: getPostResponse 처리 함수
    const getPostResponse = (responseBody: GetPostResponseDto | ResponseDto | null) => {
        if (!responseBody) return;
        const { code } = responseBody;
        if (code === 'NP') {
            showModal('Post Error', '존재하지 않는 게시물입니다.');
            navigate(MAIN_PATH());
            return;
        }
        if (code === 'DE') {
            showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
            navigate(MAIN_PATH());
            return;
        }
        if (code !== 'SU') {
            navigate(MAIN_PATH());
            return;
        }

        const {title, content, imageList, posterId} = responseBody as GetPostResponseDto; 
        setTitle(title);
        setContent(content);
        setImageUrls(imageList);
        
        if(!loginUser || loginUser.loginId !== posterId) {
            showModal("Authorization Error", "권한이 없습니다.");
            navigate(MAIN_PATH());
            return;
        }
    }

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

        if (!imageInputRef.current) return;
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

    //effect: 마운트 시 실행 함수
    useEffect(() => {
        const accessToken = cookies.accessToken;
        if (!accessToken) {
            navigate(MAIN_PATH());
            return;
        }
        if(!postId) return;
        getPostRequest(postId).then(getPostResponse);
    }, [postId]);

    //render: 게시물 수정 화면 컴포넌트 렌더링
    return (
        <div id='post-update-wrapper'>
            <div className='post-update-container'>
                <div className='post-update-box'>
                    <div className='post-update-title-box'>
                        <textarea ref={titleRef} className='post-update-title-textarea' rows={1} placeholder='제목을 작성해 주세요.' value={title} onChange={onTitleChangeHandler} />
                    </div>
                    <div className='divider'></div>
                    <div className='post-update-content-box'>
                        <textarea ref={contentRef} className='post-update-content-textarea' placeholder='본문을 작성해 주세요.' value={content} onChange={onContentChangeHandler} />
                        <div className='icon-button' onClick={onImageUploadButtonClickHandler}>
                            <div className='icon image-box-light-icon'></div>
                        </div>
                        <input ref={imageInputRef} type='file' accept='image/*' style={{ display: 'none' }} onChange={onImageChangeHandler} />
                    </div>
                    <div className='post-update-images-box'>
                        {imageUrls.map((imageUrl, index) =>
                            <div className='post-update-image-box'>
                                <img className='post-update-image' src={imageUrl} />
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

