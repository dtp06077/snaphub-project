import React, { ChangeEvent, useContext, useEffect, useRef, useState } from 'react'
import EmotionItem from '../../../components/EmotionItem';
import { CommentListItem, EmotionListItem, Post } from '../../../types/interface';
import CommentItem from '../../../components/CommentItem';
import Pagination from '../../../components/Pagination';
import './style.css';
import { useLoginUserStore } from '../../../stores';
import { useNavigate, useParams } from 'react-router-dom';
import { MAIN_PATH, POST_PATH, POST_UPDATE_PATH, USER_PATH } from '../../../constants';
import defaultImage from '../../../assets/image/default-profile-image.png';
import { getCommentListRequest, getEmotionListRequest, GetPostRequest, IncreaseViewCountRequest, putEmotionRequest, WriteCommentRequest } from '../../../apis';
import GetPostResponseDto from '../../../apis/response/post/get-post.response.dto';
import { ResponseDto } from '../../../apis/response';
import { EventModalContext } from '../../../contexts/EventModalProvider';
import { GetCommentListResponseDto, GetEmotionListResponseDto, IncreaseViewCountResponseDto, PutEmotionResponseDto, WriteCommentResponsetDto } from '../../../apis/response/post';
import dayjs from 'dayjs';
import { useCookies } from 'react-cookie';
import { WriteCommentRequestDto } from '../../../apis/request/post';

//component: 게시물 상세 화면 컴포넌트
export default function PostDetail() {

    //state: 게시물 id path variable 상태
    const { postId } = useParams();
    //state: 로그인 유저 상태
    const { loginUser } = useLoginUserStore();
    //state: 쿠키 상태
    const [cookies, setCookies] = useCookies();

    //context: 이벤트 모달 창
    const eventContext = useContext(EventModalContext);

    // eventContext가 undefined일 경우
    if (!eventContext) {
        throw new Error("이벤트 모달 창이 존재하지 않습니다.");
    }

    const { showModal } = eventContext;

    //function: 네비게이트 함수
    const navigator = useNavigate();

    //function: increaseViewCountResponse 처리 함수
    const increaseViewCountResponse = (responseBody: IncreaseViewCountResponseDto | ResponseDto | null) => {
        if (!responseBody) return;
        const { code } = responseBody;
        if (code === 'NP') {
            showModal('Post Error', '존재하지 않는 게시물입니다.');
            return;
        }
        if (code === 'DE') {
            showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
            return;
        }
    }

    //component: 게시물 상세 상단 컴포넌트
    const PostDetailTop = () => {

        //state: 작성자 여부 상태
        const [isWriter, setWriter] = useState<boolean>(false);

        //state: post 상태
        const [post, setPost] = useState<Post | null>(null);

        //state: more 버튼 상태
        const [showMore, setShowMore] = useState<boolean>(false);


        //function: 작성일 포맷 변경 처리 함수
        const getPostDateTimeFormat = () => {
            if (!post) return '';
            const date = dayjs(post.postDateTime);
            return date.format('YYYY. MM. DD. HH:mm');
        }

        //function: getPostResponse 처리 함수
        const getPostResponse = (responseBody: GetPostResponseDto | ResponseDto | null) => {
            if (!responseBody) return;
            const { code } = responseBody;
            if (code === 'NP') {
                showModal('Post Error', '존재하지 않는 게시물입니다.');
                return;
            }
            if (code === 'DE') {
                showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
                return;
            }
            if (code !== 'SU') {
                navigator(MAIN_PATH());
                return;
            }

            const post: Post = { ...responseBody as GetPostResponseDto };
            setPost(post);

            if (!loginUser) {
                setWriter(false);
                return;
            }
            const isWriter = loginUser.loginId === post.posterId;
            setWriter(isWriter);
        }

        //event handler: 닉네임 클릭 이벤트 처리
        const onNicknameClickHandler = () => {
            if (!post) return;
            navigator(USER_PATH(post.posterId));
        }

        //event handler: more 버튼 클릭 이벤트 처리
        const onMoreButtonClickHandler = () => {
            setShowMore(!showMore);
        }

        //event handler: 수정 버튼 클릭 이벤트 처리
        const onUpdateButtonClickHandler = () => {
            if (!post || !loginUser) return;
            if (loginUser.loginId !== post.posterId) return;
            navigator(POST_PATH() + '/' + POST_UPDATE_PATH(post.postId));
        }

        //event handler: 삭제 버튼 클릭 이벤트 처리
        const onDeleteButtonClickHandler = () => {
            if (!post || !loginUser) return;
            if (loginUser.loginId !== post.posterId) return;
            //TODO: Delete Request
            navigator(MAIN_PATH());
        }

        //effect: 게시물 번호 path variable 바뀔 때 마다 게시물 불러오기
        useEffect(() => {
            if (!postId) {
                navigator(MAIN_PATH());
                return;
            }
            GetPostRequest(postId).then(getPostResponse);
        }, [postId]);

        //render: 게시물 상세 상단 컴포넌트 렌더링
        if (!post) return <></>;
        return (
            <div id='post-detail-top'>
                <div className='post-detail-top-header'>
                    <div className='post-detail-title'>{post.title}</div>
                    <div className='post-detail-top-sub-box'>
                        <div className='post-detail-write-info-box'>
                            <div className='post-detail-writer-profile-image' style={{ backgroundImage: `url(${post.posterProfileImage ? post.posterProfileImage : defaultImage})` }}></div>
                            <div className='post-detail-writer-nickname' onClick={onNicknameClickHandler}>{post.posterName}</div>
                            <div className='post-detail-info-divider'>{`\|`}</div>
                            <div className='post-detail-write-date'>{getPostDateTimeFormat()}</div>
                        </div>
                        {isWriter &&
                            <div className='icon-button' onClick={onMoreButtonClickHandler}>
                                <div className='icon more-icon'></div>
                            </div>
                        }
                        {showMore &&
                            <div className='post-detail-more-box'>
                                <div className='post-detail-update-button' onClick={onUpdateButtonClickHandler}>{`수정`}</div>
                                <div className='divider'></div>
                                <div className='post-detail-delete-button' onClick={onDeleteButtonClickHandler}>{`삭제`}</div>
                            </div>}
                    </div>
                </div>
                <div className='divider'></div>
                <div className='post-detail-top-main'>
                    <div className='post-detail-main-text'>{post.content}</div>
                    {post.imageList.map(image => <img className='post-detail-main-image' src={image} />)}
                </div>
            </div>
        );
    };

    //component: 게시물 상세 하단 컴포넌트
    const PostDetailBottom = () => {

        //state: 댓글 textarea 참조 상태
        const commentRef = useRef<HTMLTextAreaElement | null>(null);

        //state: 감정표현 리스트 상태
        const [emotionList, setEmotionList] = useState<EmotionListItem[]>([]);

        //state: 감정표현 리스트 보기 상태
        const [showEmotionList, setShowEmotionList] = useState<boolean>(false);

        //state: 선택된 감정표현 상태
        const [selectedEmotion, setSelectedEmotion] = useState<string | null>(null);
        //state: 감정표현 고르기 창 상태
        const [showEmotionPicker, setShowEmotionPicker] = useState<boolean>(false);

        //state: 댓글 상태
        const [comment, setComment] = useState<string>('');
        //state: 댓글 리스트 상태
        const [commentList, setCommentList] = useState<CommentListItem[]>([]);
        //state: 댓글 리스트 보기 상태
        const [showComment, setShowComment] = useState<boolean>(false);

        //function: getEmotionListResponse 처리 함수
        const getEmotionListResponse = (responseBody: GetEmotionListResponseDto | ResponseDto | null) => {
            if (!responseBody) return;

            const { code } = responseBody;
            if (code === 'NP') {
                showModal('Post Error', '존재하지 않는 게시물입니다.');
                return;
            }
            if (code === 'DE') {
                showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
                return;
            }
            if (code !== 'SU') return;

            const { emotionList } = responseBody as GetEmotionListResponseDto;
            setEmotionList(emotionList);

            if (!loginUser) {
                setSelectedEmotion(null);
                return;
            }

            const checkEmotion = emotionList.find(emotion => emotion.loginId === loginUser.loginId);

            if (checkEmotion) {
                setSelectedEmotion(checkEmotion.status); //status를 설정
            }
        }

        //function: getCommentListResponse 처리 함수
        const getCommentListResponse = (responseBody: GetCommentListResponseDto | ResponseDto | null) => {
            if (!responseBody) return;

            const { code } = responseBody;
            if (code === 'NP') {
                showModal('Post Error', '존재하지 않는 게시물입니다.');
                return;
            }
            if (code === 'DE') {
                showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
                return;
            }
            if (code !== 'SU') return;

            const { commentList } = responseBody as GetCommentListResponseDto;
            setCommentList(commentList);
        }

        //function: putEmotionResponse 처리 함수
        const putEmotionResponse = (responseBody: PutEmotionResponseDto | ResponseDto | null) => {
            if (!responseBody) return;

            const { code } = responseBody;
            if (code === 'NU') {
                showModal('User Error', '존재하지 않는 사용자입니다.');
                return;
            }
            if (code === 'NP') {
                showModal('Post Error', '존재하지 않는 게시물입니다.');
                return;
            }
            if (code === 'DE') {
                showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
                return;
            }
            if (code !== 'SU') return;

            if (!postId) return;
            getEmotionListRequest(postId).then(getEmotionListResponse);
        }

        //function: writeCommentResponse 처리 함수
        const writeCommentResponse = (responseBody: WriteCommentResponsetDto | ResponseDto | null) => {
            if (!responseBody) return;

            const { code } = responseBody;
            if (code === 'NU') {
                showModal('User Error', '존재하지 않는 사용자입니다.');
                return;
            }
            if (code === 'NP') {
                showModal('Post Error', '존재하지 않는 게시물입니다.');
                return;
            }
            if (code === 'DE') {
                showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
                return;
            }
            if (code !== 'SU') return;

            setComment('');

            if (!postId) return;
            getCommentListRequest(postId).then(getCommentListResponse);
        }

        //event handler: 감정표현 버튼 클릭 이벤트 처리
        const onEmotionClickHandler = () => {
            if (!postId || !loginUser) {
                // 에러 메시지 출력
                showModal("Login", "로그인이 필요합니다.");
                return;
            }

            if (selectedEmotion) {
                putEmotionRequest(postId, selectedEmotion, cookies.accessToken).then(putEmotionResponse);
                setSelectedEmotion(null);
                return;
            }

            setShowEmotionPicker(!showEmotionPicker);
        }

        //event handler: 감정표현 선택 이벤트 처리
        const handleEmotionSelect = (emotionStatus: string) => {
            if (!postId || !cookies.accessToken) {
                // 에러 메시지 출력
                showModal("Cookie Error", "엑세스 토큰이 존재하지 않습니다.");
                return;
            }

            putEmotionRequest(postId, emotionStatus, cookies.accessToken).then(putEmotionResponse);

            setSelectedEmotion(emotionStatus);
            setShowEmotionPicker(!showEmotionPicker);
        }

        //event handler: 감정표현 리스트 보기 클릭 이벤트 처리
        const onShowEmotionClickHandler = () => {
            setShowEmotionList(!showEmotionList);
        }

        //event handler: 댓글 리스트 보기 클릭 이벤트 처리
        const onShowCommentClickHandler = () => {
            setShowComment(!showComment);
        }

        //event handler: 댓글 작성 버튼 클릭 이벤트 처리
        const onCommentSubmitButtonClickHandler = () => {
            if (!comment) {
                showModal('comment error', "댓글을 입력해주세요.");
                return;
            }
            if (!postId || !loginUser || !cookies.accessToken) {
                showModal("login error", "로그인이 필요합니다.");
                return;
            }
            const requestBody: WriteCommentRequestDto = { content: comment };
            WriteCommentRequest(postId, requestBody, cookies.accessToken).then(writeCommentResponse);
        }

        //component: 게시물 상세 하단 컴포넌트
        const EmotionIcon = () => {
            if (!selectedEmotion) return null; // 감정이 없으면 null 반환
            switch (selectedEmotion) {
                case 'ANGRY':
                    return <div className='icon angry-emotion-icon'></div>;
                case 'HAPPY':
                    return <div className='icon happy-emotion-icon'></div>;
                case 'SAD':
                    return <div className='icon sad-emotion-icon'></div>;
                default:
                    return null; // 기본값
            }
        };

        //event handler: 댓글 변경 이벤트 처리
        const onCommentChangeHandler = (event: ChangeEvent<HTMLTextAreaElement>) => {
            const { value } = event.target;
            setComment(value);
            if (!commentRef.current) return;
            commentRef.current.style.height = 'auto';
            commentRef.current.style.height = `${commentRef.current.scrollHeight}px`;
        }

        //effect: 게시물 번호 path variable이 바뀔 때 마다 좋아요 및 댓글 리스트 불러오기
        useEffect(() => {
            if (!postId) return;
            getEmotionListRequest(postId).then(getEmotionListResponse);
            getCommentListRequest(postId).then(getCommentListResponse);
        }, [postId]);

        //render: 게시물 상세 하단 컴포넌트 렌더링
        return (
            <div id='post-detail-bottom'>
                <div className='post-detail-bottom-button-box'>
                    <div className='post-detail-bottom-button-group'>
                        <div className='icon-button' onClick={onEmotionClickHandler}>
                            {selectedEmotion ? <EmotionIcon /> : <div className='icon emotion-light-icon'></div>}
                        </div>
                        {showEmotionPicker && (
                            <div className='post-detail-bottom-emotion-pick-box'>
                                <div className='emotion-button'>
                                    <div onClick={() => handleEmotionSelect('ANGRY')} className='emotion angry-emotion-icon'></div>
                                    <div onClick={() => handleEmotionSelect('SAD')} className='emotion sad-emotion-icon'></div>
                                    <div onClick={() => handleEmotionSelect('HAPPY')} className='emotion happy-emotion-icon'></div>
                                </div>
                            </div>
                        )}
                        <div className='post-detail-bottom-button-text'>{`감정표현 `}<span className='emphasis'>{emotionList.length}</span></div>
                        <div className='icon-button' onClick={onShowEmotionClickHandler}>
                            {showEmotionList ?
                                <div className='icon up-light-icon'></div> :
                                <div className='icon down-light-icon'></div>
                            }
                        </div>
                    </div>
                    <div className='post-detail-bottom-button-group'>
                        <div className='icon-button'>
                            <div className='icon comment-icon'></div>
                        </div>
                        <div className='post-detail-bottom-button-text'>{`댓글 `}<span className='emphasis'>{commentList.length}</span></div>
                        <div className='icon-button' onClick={onShowCommentClickHandler}>
                            {showComment ?
                                <div className='icon up-light-icon'></div> :
                                <div className='icon down-light-icon'></div>
                            }
                        </div>
                    </div>
                </div>
                {showEmotionList &&
                    <div className='post-detail-bottom-emotion-box'>
                        <div className='post-detail-bottom-emotion-container'>
                            <div className='post-detail-bottom-emotion-title'>{`감정표현`}<span className='emphasis'>{emotionList.length}</span></div>
                            <div className='post-detail-bottom-emotion-contents'>
                                {emotionList.map(item => <EmotionItem emotionListItem={item} />)}
                            </div>
                        </div>
                    </div>
                }
                {showComment &&
                    <div className='post-detail-bottom-comment-box'>
                        <div className='post-detail-bottom-comment-container'>
                            <div className='post-detail-bottom-comment-title'>{`댓글`}<span className='emphasis'>{commentList.length}</span></div>
                            <div className='post-detail-bottom-comment-list-container'>
                                {commentList.map(item => <CommentItem commentListItem={item} />)}
                            </div>
                        </div>
                        <div className='divider'></div>
                        <div className='post-detail-bottom-comment-pagination-box'>
                            <Pagination />
                        </div>
                        {loginUser !== null &&
                            <div className='post-detail-bottom-comment-input-box'>
                                <div className='post-detail-bottom-comment-input-container'>
                                    <textarea ref={commentRef} className="post-detail-bottom-comment-textarea" placeholder='댓글을 작성해주세요.' value={comment} onChange={onCommentChangeHandler}></textarea>
                                    <div className='post-detail-bottom-comment-button-box'>
                                        <div className={comment === '' ? 'disable-button' : 'black-button'} onClick={onCommentSubmitButtonClickHandler}>{`댓글달기`}</div>
                                    </div>
                                </div>
                            </div>
                        }
                    </div>
                }
            </div>
        );
    };


    //effect: 게시물 id path variable이 바뀔 때 마다 게시물 조회 수 증가
    let effectFlag = true;
    useEffect(() => {
        if (!postId) return;
        if (effectFlag) {
            effectFlag = false;
            return;
        }

        IncreaseViewCountRequest(postId).then(increaseViewCountResponse);
    }, [postId])


    //render: 게시물 상세 화면 컴포넌트 렌더링
    return (
        <div id='post-detail-wrapper'>
            <div className='post-detail-container'>
                <PostDetailTop />
                <PostDetailBottom />
            </div>
        </div>
    )
}
