import React, { useEffect, useState } from 'react'
import EmotionItem from '../../../components/EmotionItem';
import { CommentListItem, EmotionListItem } from '../../../types/interface';
import { commentListMock, emotionListMock } from '../../../mocks';
import CommentItem from '../../../components/CommentItem';

//component: 게시물 상세 화면 컴포넌트
export default function PostDetail() {

    //component: 게시물 상세 상단 컴포넌트
    const PostDetailTop = () => {

        //render: 게시물 상세 상단 컴포넌트 렌더링
        return(
            <div id = 'post-detail-top'>
                <div className='post-detail-top-header'>
                    <div className='post-detail-title'></div>
                    <div className='post-detail-top-sub-box'>
                        <div className='post-detail-write-info-box'>
                            <div className='post-detail-writer-profile-image'></div>
                            <div className='post-detail-writer-nickname'></div>
                            <div className='post-detail-info-divider'></div>
                            <div className='post-detail-write-date'></div>
                        </div>
                        <div className='icon-button'>
                            <div className='icon more-icon'></div>
                        </div>
                        <div className='post-detail-more-box'>
                            <div className='post-detail-update-button'></div>
                            <div className='divider'></div>
                            <div className='post-detail-delete-button'></div>
                        </div>
                    </div>
                </div>
                <div className='divider'></div>
                <div className='post-detail-top-main'>
                    <div className='post-detail-main-text'></div>
                    <div className='post-detail-main-image'></div>
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
                    <div className='post-detail-bottom-comment-pagination-box'></div>
                    <div className='post-detail-bottom-comment-input-container'>
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
