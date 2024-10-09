import React from 'react'
import './style.css'
import defaultProfileImage from "../../assets/image/default-profile-image.png";
import { PostListItem } from '../../types/interface';
import { useNavigate } from 'react-router-dom';
import { POST_DETAIL_PATH } from '../../constants';

interface Props {
  top3ListItem: PostListItem
}

//component: Top 3 List Item 컴포넌트
export default function Top3Item({top3ListItem}: Props) {

  //properties
  const { postId, title, content, postTitleImage} = top3ListItem;
  const { emotionCount, commentCount, viewCount } = top3ListItem;
  const { postDateTime, posterName, posterProfileImage } = top3ListItem;

  //function: 네비게이트 함수
  const navigate = useNavigate();

  //event handler: 게시물 아이템 클릭 이벤트 처리 함수
  const onClickHandler = () => {
    navigate(POST_DETAIL_PATH(postId));
  }

  //render: Top 3 List Item 컴포넌트 렌더링
  return (
    <div className='top-3-list-item' style={{backgroundImage: `url(${postTitleImage})`}} onClick={onClickHandler}>
      <div className='top-3-list-item-main-box'>
        <div className='top-3-list-item-top'>
          <div className='top-3-list-item-profile-box'>
            <div className='top-3-list-item-profile-image' style={{backgroundImage: `url(${posterProfileImage ? posterProfileImage : defaultProfileImage})`}}></div>  
          </div>
          <div className='top-3-list-item-write-box'>
            <div className='top-3-list-item-name'>{posterName}</div>
            <div className='top-3-list-item-post-datetime'>{postDateTime}</div>
          </div>
        </div>
        <div className='top-3-list-item-middle'>
          <div className='top-3-list-item-title'>{title}</div>
          <div className='top-3-list-item-content'>{content}</div>
        </div>
        <div className='top-3-list-item-bottom'>
          <div className='top-3-list-item-counts'>
            {`댓글 ${commentCount} 감정표현 ${emotionCount} 조회수 ${viewCount}`}
          </div>
        </div>
      </div>
    </div>
  )
}
