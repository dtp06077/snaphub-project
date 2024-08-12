import React from 'react'
import { EmotionListItem } from '../../types/interface'
import defaultProfileImage from "../../assets/image/default-profile-image.png";


interface Props {
  emotionListItem: EmotionListItem;
}

//component: Emotion List Item 컴포넌트
export default function EmotionItem({ emotionListItem }: Props) {

  //properties
  const { profileImage, name } = emotionListItem;

  //render: Emotion List Item 렌더링
  return (
    <div className='emotion-list-item'>
      <div className='emotion-list-item-profile-box'>
        <div className='emotion-list-item-profile-image' style={{ backgroundImage: `url(${profileImage ? profileImage : defaultProfileImage})` }}></div>
      </div>
      <div className='emotion-list-item-name'>{name}</div>
    </div>
  )
}
