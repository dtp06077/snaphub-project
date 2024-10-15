import React, { ChangeEvent, useEffect, useRef, useState } from 'react'
import defaultProfileImage from "../../assets/image/default-profile-image.png";
import { useNavigate, useParams } from 'react-router-dom';
import './style.css';
import { PostListItem } from '../../types/interface';
import PostItem from '../../components/PostItem';
import { POST_WRITE_PATH, USER_PATH } from '../../constants';
import { useLoginUserStore } from '../../stores';

//component: 사용자 화면 컴포넌트
export default function User() {

    //state: 닉네임 파라미터 상태
    const { userId } = useParams();
    //state: 로그인 유저 상태
    const { loginUser } = useLoginUserStore();
    //state: 마이페이지 여부 상태
    const [isMyPage, setMyPage] = useState<boolean>(true);

    //function: 네비게이트 함수
    const navigate = useNavigate();

    //component: 사용자 화면 상단 컴포넌트
    const UserTop = () => {

        //state: 이미지 파일 인풋 참조 상태
        const imageInputRef = useRef<HTMLInputElement | null>(null);
        //state: 닉네임 변경 여부 상태
        const [isNameChanged, setIsNameChanged] = useState<boolean>(false);
        //state: 닉네임 상태
        const [name, setName] = useState<string>('');
        //state: 변경 닉네임 상태
        const [changeName, setChangeName] = useState<string>('');
        //state: 프로필 이미지 상태
        const [profileImage, setProfileImage] = useState<string | null>(null);

        //event handler: 프로필 박스 클릭 이벤트 처리
        const onProfileBoxClickHandler = () => {
            if (!isMyPage) return;
            if (!imageInputRef.current) return;
            imageInputRef.current.click();
        };

        //event handler: 닉네임 수정 버튼 클릭 이벤트 처리
        const onNameEditButtonClickHandler = () => {
            setChangeName(name);
            setIsNameChanged(!isNameChanged);
        };

        //event handler: 프로필 이미지 변경 이벤트 처리
        const onProfileImageChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
            if (!event.target.files || !event.target.files?.length) return;

            const file = event.target.files[0];
            const data = new FormData();
            data.append('file', file);
        };

        //event handler: 닉네임 변경 이벤트 처리
        const onNameChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
            const { value } = event.target;
            setChangeName(value);
        };

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
                        <div className='user-top-my-profile-image-box' onClick={onProfileBoxClickHandler}>
                            {profileImage !== null ?
                                <div className='user-top-my-profile-image' style={{ backgroundImage: `url(${profileImage})` }}></div> :

                                <div className='icon-box-large'>
                                    <div className='icon image-box-white-icon'></div>
                                </div>

                            }
                            <input ref={imageInputRef} type='file' accept='image/*' style={{ display: 'none' }} onChange={onProfileImageChangeHandler} />
                        </div> :
                        <div className='user-top-profile-image-box' style={{ backgroundImage: `url(${profileImage ? profileImage : defaultProfileImage})` }}></div>
                    }
                    <div className='user-top-info-box'>
                        <div className='user-top-info-name-box'>
                            {isMyPage ?
                                <>
                                    {isNameChanged ?
                                        <input className='user-top-info-name-input' type='text' size={changeName.length + 1} value={changeName} onChange={onNameChangeHandler} /> :
                                        <div className='user-top-info-name'>{name}</div>
                                    }
                                    <div className='icon-button' onClick={onNameEditButtonClickHandler}>
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

        //state: 게시물 개수 상태
        const [count, setCount] = useState<number>(0);
        //state: 게시물 리스트 상태
        const [userPostList, setUserPostList] = useState<PostListItem[]>([]);

        //event handler: 사이드 카드 클릭 이벤트 처리
        const onSideCardClickHandler = () => {
            if (isMyPage) navigate(POST_WRITE_PATH());
            else if(loginUser) navigate(USER_PATH(loginUser.userId));
        }

        //effect: useId path variable이 변경될 때 마다 실행될 함수
        useEffect(() => {

        }, [userId]);
        //render: 사용자 화면 하단 컴포넌트 렌더링
        return (
            <div id='user-bottom-wrapper'>
                <div className='user-bottom-container'>
                    <div className='user-bottom-title'>{isMyPage ? '내 게시물' : '게시물'}<span className='emphasis'>{count}</span></div>
                    <div className='user-bottom-contents-box'>
                        {count === 0 ?
                            <div className='user-bottom-contents-nothing'>{'게시물이 없습니다.'}</div> :
                            <div className='user-bottom-contents'>
                                {userPostList.map(postListItem => <PostItem postListItem={postListItem} />)}
                            </div>
                        }
                        <div className='user-bottom-side-box'>
                            <div className='user-bottom-side-card' onClick={onSideCardClickHandler}>
                                <div className='user-bottom-side-container'>
                                    {isMyPage ?
                                        <>
                                            <div className='icon-box'>
                                                <div className='icon edit-icon'></div>
                                            </div>
                                            <div className='user-bottom-side-text'>{'글쓰기'}</div>
                                        </> :
                                        <>
                                            <div className='user-bottom-side-text'>{'내 게시물로 가기'}</div>
                                            <div className='icon-box'>
                                                <div className='icon arrow-right-icon'></div>
                                            </div>
                                        </>
                                    }
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className='user-bottom-pagination-box'></div>
                </div>
            </div>
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
