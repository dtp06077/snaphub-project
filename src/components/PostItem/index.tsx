import React from 'react';
import './style.css';
import { PostListItem } from '../../types/interface';
import { useNavigate } from 'react-router-dom';
import defaultProfileImage from "../../assets/image/default-profile-image.png";

interface Props {
    postListItem: PostListItem
}

//component : Post List Item 컴포넌트
export default function PostItem({ postListItem }: Props) {

    //function: 네비게이트 함수
    const navigater = useNavigate();

    //event handler: 게시물 아이템 클릭 이벤트 처리 함수
    const onClickHandler = () => {
        navigater(postId);
    }

    //properties
    const { postId, title, content, postTitleImage } = postListItem;
    const { emotionCount, commentCount, viewCount } = postListItem;
    const { postDateTime, posterName, posterProfileImage } = postListItem;

    //render : Post List Item 컴포넌트 렌더링
    return (
        <div className='post-list-item' onClick={onClickHandler}>
            <div className='post-list-item-main-box'>
                <div className='post-list-item-top'>
                    <div className='post-list-item-profile-box'>
                        <div className='post-list-item-profile-image' style={{ backgroundImage: `url(${posterProfileImage ? posterProfileImage : defaultProfileImage})` }}></div>
                    </div>
                    <div className='post-list-item-write-box'>
                        <div className='post-list-item-name'>{posterName}</div>
                        <div className='post-list-item-post-datetime'>{postDateTime}</div>
                    </div>
                </div>
                <div className='post-list-item-middle'>
                    <div className='post-list-item-title'>{title}</div>
                    <div className='post-list-item-content'>{content}</div>
                </div>
                <div className='post-list-item-bottom'>
                    <div className='post-list-item-counts'>
                        {`댓글 ${commentCount} 감정표현 ${emotionCount} 조회수 ${viewCount}`}
                    </div>
                </div>
            </div>
            {postTitleImage !== null && (
                <div className='post-list-item-image-box'>
                    <div className='post-list-item-image' style={{ backgroundImage: `url(${postTitleImage})` }}></div>
                </div>
            )}
        </div>
    )
}
