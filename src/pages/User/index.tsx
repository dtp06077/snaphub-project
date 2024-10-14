import React, { useEffect, useRef, useState } from 'react'
import defaultProfileImage from "../../assets/image/default-profile-image.png";
import { useParams } from 'react-router-dom';
import './style.css';

//component: 사용자 화면 컴포넌트
export default function User() {

    //state: 닉네임 파라미터 상태
    const { userId } = useParams();

    //component: 사용자 화면 상단 컴포넌트
    const UserTop = () => {

        //state: 이미지 파일 인풋 참조 상태
        const imageInputRef = useRef<HTMLInputElement | null>(null);
        //state: 마이페이지 여부 상태
        const [isMyPage, setMyPage] = useState<boolean>(false);
        //state: 닉네임 변경 여부 상태
        const [isNameChanged, setIsNameChanged] = useState<boolean>(false);
        //state: 닉네임 상태
        const [name, setName] = useState<string>('');
        //state: 변경 닉네임 상태
        const [changeName, setChangeName] = useState<string>('');
        //state: 프로필 이미지 상태
        const [profileImage, setProfileImage] = useState<string | null>(null);

        // useEffect: name path variable 변경 시 실행 할 함수
        useEffect(() => {
            if (!userId) return;
            setName("huiseong")
            setProfileImage(null);
        }, [userId]);

        //render: 사용자 화면 상단 컴포넌트 렌더링
        return (
            <div id="user-top-wrapper">
                <div className='user-top-container'>
                    {isMyPage ?
                        <div className='user-top-my-profile-image-box'>
                            {profileImage !== null ?
                                <div className='user-top-my-profile-image' style={{ backgroundImage: `url(${profileImage})` }}></div> :
                                <div className='user-top-my-profile-image-nothing-box'>
                                    <div className='icon-box-large'>
                                        <div className='icon image-box-white-icon'></div>
                                    </div>
                                </div>
                            }
                            <input ref={imageInputRef} type='file' accept='image/*' style={{ display: 'none' }} />
                        </div> :
                        <div className='user-top-profile-image-box' style={{ backgroundImage: `url(${profileImage ? profileImage : defaultProfileImage})` }}></div>
                    }
                    <div className='user-top-info-box'>
                        <div className='user-top-info-name-box'>
                            {isMyPage ?
                                <>
                                    {isNameChanged ?
                                        <input className='user-top-info-name-input' type='text' size={changeName.length + 1} value={changeName} /> :
                                        <div className='user-top-info-name'>{name}</div>
                                    }
                                    <div className='icon-button'>
                                        <div className='icon edit-icon'></div>
                                    </div>
                                </> :
                                <div className='user-top-info-name'>{name}</div>
                            }
                        </div>
                        <div className='user-top-info-email'>{name}</div>
                    </div>
                </div>
            </div>
        );
    };

    //component: 사용자 화면 하단 컴포넌트
    const UserBottom = () => {

        //render: 사용자 화면 하단 컴포넌트 렌더링
        return (
            <div></div>
        );
    };

    //render: 사용자 화면 컴포넌트 렌더링
    return (
        <>
            <UserTop />
            <UserBottom />
        </>
    )
}
