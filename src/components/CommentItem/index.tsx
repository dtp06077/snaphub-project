import React from 'react'
import './style.css'
import { CommentListItem } from '../../types/interface'
import defaultProfileImage from "../../assets/image/default-profile-image.png";

interface Props {
    commentListItem: CommentListItem;
}

//component: Comment List Item 컴포넌트
export default function CommentItem({ commentListItem }: Props) {

    //properties
    const { name, profileImage, commentDateTime, content } = commentListItem;

    //render: Comment List Item 렌더링
    return (
        <div className='comment-list-item'>
            <div className='comment-list-item-top'>
                <div className='comment-list-item-profile-box'>
                    <div className='comment-list-item-profile-image' style={{ backgroundImage: `url(${profileImage ? profileImage : defaultProfileImage})` }}></div>
                </div>
                <div className='comment-list-item-name'>{name}</div>
                <div className='comment-list-item-divider'>{`\|`}</div>
                <div className='comment-list-item-time'>{commentDateTime}</div>
            </div>
            <div className='comment-list-item-main'>
                <div className='comment-list-item-content'>{content}</div>
            </div>
        </div>
    )
}
