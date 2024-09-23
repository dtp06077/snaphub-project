import React, { ChangeEvent, useContext, useEffect, useRef, useState } from 'react'
import EmotionItem from '../../../components/EmotionItem';
import { CommentListItem, EmotionListItem, Post } from '../../../types/interface';
import { commentListMock, emotionListMock } from '../../../mocks';
import CommentItem from '../../../components/CommentItem';
import Pagination from '../../../components/Pagination';
import './style.css';
import { useLoginUserStore } from '../../../stores';
import { useNavigate, useParams } from 'react-router-dom';
import { MAIN_PATH, POST_PATH, POST_UPDATE_PATH, USER_PATH } from '../../../constants';
import defaultImage from '../../../assets/image/default-profile-image.png';
import { GetPostRequest } from '../../../apis';
import GetPostResponseDto from '../../../apis/response/post/get-post.response.dto';
import { ResponseDto } from '../../../apis/response';
import { EventModalContext } from '../../../contexts/EventModalProvider';

//component: 게시물 상세 화면 컴포넌트
export default function PostDetail() {

    //state: 게시물 번호 path variable 상태
    const { postId } = useParams();
    //state: 로그인 유저 상태
    const { loginUser } = useLoginUserStore();

    //context: 이벤트 모달 창
    const eventContext = useContext(EventModalContext);

    // eventContext가 undefined일 경우
    if (!eventContext) {
        throw new Error("이벤트 모달 창이 존재하지 않습니다.");
    }

    const { showModal } = eventContext;

    //function: 네비게이트 함수
    const navigator = useNavigate();

    //component: 게시물 상세 상단 컴포넌트
    const PostDetailTop = () => {

        //state: post 상태
        const [post, setPost] = useState<Post | null>(null);

        //state: more 버튼 상태
        const [showMore, setShowMore] = useState<boolean>(false);


        //function: getPostResponse 처리 함수
        const getPostResponse = (responseBody: GetPostResponseDto | ResponseDto | null) => {
            if (!responseBody) return;
            const { code } = responseBody;
            if(code === 'NP') showModal('Post Error','존재하지 않는 게시물입니다.');
            if(code === 'DE') showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
            if(code !== 'SU') {
                navigator(MAIN_PATH());
                return;
            }

            const post: Post = {...responseBody as GetPostResponseDto};
            setPost(post);
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
            if(!postId) {
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
                            <div className='post-detail-write-date'>{post.postDateTime}</div>
                        </div>
                        <div className='icon-button' onClick={onMoreButtonClickHandler}>
                            <div className='icon more-icon'></div>
                        </div>
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
        //state: 댓글 리스트 상태
        const [commentList, setCommentList] = useState<CommentListItem[]>([]);
        //state: 감정표현 상태
        const [isEmotion, setEmotion] = useState<boolean>(false);
        //state: 댓글 상태
        const [comment, setComment] = useState<string>('');
        //state: 감정표현 리스트 보기 상태
        const [showEmotion, setShowEmotion] = useState<boolean>(false);
        //state: 댓글 리스트 보기 상태
        const [showComment, setShowComment] = useState<boolean>(false);


        //event handler: 감정표현 버튼 클릭 이벤트 처리
        const onEmotionClickHandler = () => {
            setEmotion(!isEmotion);
        }

        //event handler: 감정표현 리스트 보기 클릭 이벤트 처리
        const onShowEmotionClickHandler = () => {
            setShowEmotion(!showEmotion);
        }

        //event handler: 댓글 리스트 보기 클릭 이벤트 처리
        const onShowCommentClickHandler = () => {
            setShowComment(!showComment);
        }

        //event handler: 댓글 작성 버튼 클릭 이벤트 처리
        const onCommentSubmitButtonClickHandler = () => {
            if (!comment) return;
        }

        //event handler: 댓글 변경 이벤트 처리
        const onCommentChangeHandler = (event: ChangeEvent<HTMLTextAreaElement>) => {
            const { value } = event.target;
            setComment(value);
            if(!commentRef.current) return;
            commentRef.current.style.height = 'auto';
            commentRef.current.style.height = `${commentRef.current.scrollHeight}px`;
        }

        //effect: 게시물 번호 path variable이 바뀔 때 마다 좋아요 및 댓글 리스트 불러오기
        useEffect(() => {
            setEmotionList(emotionListMock);
            setCommentList(commentListMock);
        }, [postId]);

        //render: 게시물 상세 하단 컴포넌트 렌더링
        return (
            <div id='post-detail-bottom'>
                <div className='post-detail-bottom-button-box'>
                    <div className='post-detail-bottom-button-group'>
                        <div className='icon-button' onClick={onEmotionClickHandler}>
                            {isEmotion ?
                                <div className='icon emotion-fill-icon'></div> :
                                <div className='icon emotion-light-icon'></div>
                            }
                        </div>
                        <div className='post-detail-bottom-button-text'>{`좋아요 `}<span className='emphasis'>{emotionList.length}</span></div>
                        <div className='icon-button' onClick={onShowEmotionClickHandler}>
                            {showEmotion ?
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
                {showEmotion &&
                    <div className='post-detail-bottom-emotion-box'>
                        <div className='post-detail-bottom-emotion-container'>
                            <div className='post-detail-bottom-emotion-title'>{`좋아요`}<span className='emphasis'>{emotionList.length}</span></div>
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
                        <div className='post-detail-bottom-comment-input-box'>
                            <div className='post-detail-bottom-comment-input-container'>
                                <textarea ref={commentRef} className="post-detail-bottom-comment-textarea" placeholder='댓글을 작성해주세요.' value={comment} onChange={onCommentChangeHandler}></textarea>
                                <div className='post-detail-bottom-comment-button-box'>
                                    <div className={comment === '' ? 'disable-button' : 'black-button'} onClick={onCommentSubmitButtonClickHandler}>{`댓글달기`}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                }
            </div>
        );
    };
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
