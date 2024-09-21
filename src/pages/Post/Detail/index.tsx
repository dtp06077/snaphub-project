import React, { useEffect, useState } from 'react'
import EmotionItem from '../../../components/EmotionItem';
import { CommentListItem, EmotionListItem, Post } from '../../../types/interface';
import { commentListMock, emotionListMock } from '../../../mocks';
import CommentItem from '../../../components/CommentItem';
import Pagination from '../../../components/Pagination';
import './style.css';
import { useLoginUserStore } from '../../../stores';
import { useNavigate, useParams } from 'react-router-dom';
import { POST_PATH, POST_UPDATE_PATH, USER_PATH } from '../../../constants';
import defaultImage from '../assets/image/default-profile-image.png';

//component: 게시물 상세 화면 컴포넌트
export default function PostDetail() {

    //state: 게시물 번호 path variable 상태
    const { postId } = useParams();
    //state: 로그인 유저 상태
    const { loginUser } = useLoginUserStore();

    //function: 네비게이트 함수
    const navigator = useNavigate();

    //component: 게시물 상세 상단 컴포넌트
    const PostDetailTop = () => {

        //state: post 상태
        const [post, setPost] = useState<Post | null>(null);

        //state: more 버튼 상태
        const [showMore, setShowMore] = useState<boolean>(false);

        //event handler: 닉네임 클릭 이벤트 처리
        const onNicknameClickHandler = () => {
            if(!post) return;
            navigator(USER_PATH(post.posterId));
        }
        
        //event handler: more 버튼 클릭 이벤트 처리
        const onMoreButtonClickHandler = () => {
            setShowMore(!showMore);
        }

        //event handler: 수정 버튼 클릭 이벤트 처리
        const onUpdateButtonClickHandler = () => {
            if(!post || !loginUser) return;
            if(loginUser.loginId !== post.posterId) return;
            navigator(POST_PATH() + '/' + POST_UPDATE_PATH(post.postId));
        }

        //render: 게시물 상세 상단 컴포넌트 렌더링
        if(!post) return <></>;
        return(
            <div id = 'post-detail-top'>
                <div className='post-detail-top-header'>
                    <div className='post-detail-title'>{post.title}</div>
                    <div className='post-detail-top-sub-box'>
                        <div className='post-detail-write-info-box'>
                            <div className='post-detail-writer-profile-image' style={{backgroundImage: `url(${post.posterProfileImage ? post.posterProfileImage : defaultImage})`}}></div>
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
                            <div className='post-detail-delete-button'>{`삭제`}</div>
                        </div> }
                    </div>
                </div>
                <div className='divider'></div>
                <div className='post-detail-top-main'>
                    <div className='post-detail-main-text'>{post.content}</div>
                    {post.imageList.map(image => <img className='post-detail-main-image' src={image}/>)}
                </div>
            </div>
        );
    };

    //component: 게시물 상세 하단 컴포넌트
    const PostDetailBottom = () => {

        const [emotionList, setEmotionList] = useState<EmotionListItem[]>([]);
        const [commentList, setCommentList] = useState<CommentListItem[]>([]);

        useEffect(() => {
            setEmotionList(emotionListMock);
            setCommentList(commentListMock);
        }, []);

        //render: 게시물 상세 하단 컴포넌트 렌더링
        return(
            <div id='post-detail-bottom'>
                <div className='post-detail-bottom-button-box'>
                    <div className='post-detail-bottom-button-group'>
                        <div className='icon-button'>
                            <div className='icon emotion-fill-icon'></div>
                        </div>
                        <div className='post-detail-bottom-button-text'>{`좋아요 ${1}`}</div>
                        <div className='icon-button'>
                            <div className='icon up-light-icon'></div>
                        </div>
                    </div>
                    <div className='post-detail-bottom-button-group'>
                        <div className='icon-button'>
                            <div className='icon comment-fill-icon'></div>
                        </div>
                        <div className='post-detail-bottom-button-text'>{`댓글 ${1}`}</div>
                        <div className='icon-button'>
                            <div className='icon up-light-icon'></div>
                        </div>
                    </div>
                </div>
                <div className='post-detail-bottom-emotion-box'>
                    <div className='post-detail-bottom-emotion-container'>
                        <div className='post-detail-bottom-emotion-title'>{`좋아요`}<span className='emphasis'>{1}</span></div>
                        <div className='post-detail-bottom-emotion-contents'>
                            {emotionList.map(item => <EmotionItem emotionListItem={item}/>)}
                        </div>
                    </div>
                </div>
                <div className='post-detail-bottom-comment-box'>
                    <div className='post-detail-bottom-comment-container'>
                        <div className='post-detail-bottom-comment-title'>{`댓글`}<span className='emphasis'>{1}</span></div>
                        <div className='post-detail-bottom-comment-list-container'>
                            {commentList.map(item => <CommentItem commentListItem={item}/>)}
                        </div>
                    </div>
                    <div className='divider'></div>
                    <div className='post-detail-bottom-comment-pagination-box'>
                        <Pagination/>
                    </div>
                    <div className='post-detail-bottom-comment-input-box'>
                        <div className='post-detail-bottom-comment-input-container'>
                            <textarea className="post-detail-bottom-comment-textarea" placeholder='댓글을 작성해주세요.'></textarea>
                            <div className='post-detail-bottom-comment-button-box'>
                                <div className='disable-button'>{`댓글달기`}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    };
    //render: 게시물 상세 화면 컴포넌트 렌더링
    return (
        <div id = 'post-detail-wrapper'>
            <div className='post-detail-container'>
                <PostDetailTop/>
                <PostDetailBottom/>
            </div>
        </div>
    )
}
