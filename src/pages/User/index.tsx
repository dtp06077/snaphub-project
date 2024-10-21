import React, { ChangeEvent, useContext, useEffect, useRef, useState } from 'react'
import defaultProfileImage from "../../assets/image/default-profile-image.png";
import { useNavigate, useParams } from 'react-router-dom';
import './style.css';
import { PostListItem } from '../../types/interface';
import PostItem from '../../components/PostItem';
import { MAIN_PATH, POST_WRITE_PATH, USER_PATH } from '../../constants';
import { useLoginUserStore } from '../../stores';
import { getUserRequest, updateNameRequest, updateProfileImageRequest, uploadFileRequest } from '../../apis';
import { GetUserResponseDto, UpdateNameResponseDto, UpdateProfileImageResponseDto } from '../../apis/response/user';
import { ResponseDto } from '../../apis/response';
import { EventModalContext } from '../../contexts/EventModalProvider';
import { UpdateNameRequestDto, UpdateProfileImageRequestDto } from '../../apis/request/user';
import { useCookies } from 'react-cookie';

//component: 사용자 화면 컴포넌트
export default function User() {

    //state: 회원번호 파라미터 상태
    const { id } = useParams();

    //state: 로그인 유저 상태
    const { loginUser } = useLoginUserStore();
    //state: 마이페이지 여부 상태
    const [isMyPage, setMyPage] = useState<boolean>(true);
    //state: cookie 상태
    const [cookies, setCookies] = useCookies();

    //context: 이벤트 모달 창
    const eventContext = useContext(EventModalContext);

    // eventContext가 undefined일 경우
    if (!eventContext) {
        throw new Error("이벤트 모달 창이 존재하지 않습니다.");
    }

    const { showModal } = eventContext;

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
        //state: 이메일 상태
        const [email, setEmail] = useState<string | null>('');
        //state: 변경 닉네임 상태
        const [changeName, setChangeName] = useState<string>('');
        //state: 프로필 이미지 상태
        const [profileImage, setProfileImage] = useState<string | null>(null);

        // function: getUserResponse 처리 함수
        const getUserResponse = (responseBody: GetUserResponseDto | ResponseDto | null) => {
            
            if(!responseBody) return;

            const { code } = responseBody;
            
            if (code === 'NU') {
                showModal('User Error', '존재하지 않는 사용자입니다.');
                return;
            }
            if (code === 'DE') {
                showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
                return;
            }
            if (code !== 'SU') {
                navigate(MAIN_PATH());
                return;
            }

            const {loginId, email, name, profile} = responseBody as GetUserResponseDto;
            setName(name);
            setProfileImage(profile);
            setEmail(email);
            const isMyPage = (loginId === loginUser?.loginId);
            setMyPage(isMyPage);
        };

        // function: uploadFileResponse 처리 함수
        const uploadFileResponse = (profileImage: string | null) => {
            if(!profileImage) return;
            if(!cookies.accessToken) return;
            const requestBody: UpdateProfileImageRequestDto = { profileImage };
                updateProfileImageRequest(requestBody, cookies.accessToken).then(updateProfileImageResponse);
        };

        //function: updateProfileImageResponse 처리 함수
        const updateProfileImageResponse = (responseBody: UpdateProfileImageResponseDto | ResponseDto | null) => {
            
            if(!responseBody) return;
            
            const { code } = responseBody;

            if (code === 'AF') {
                showModal('Authorization Fail', '인증에 실패했습니다.');
                return;
            }
            if (code === 'NU') {
                showModal('User Error', '존재하지 않는 사용자입니다.');
                return;
            }
            if (code === 'DE') {
                showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
                return;
            }
            if (code !== 'SU') {
                return;
            }
            if(!id) return;

            getUserRequest(id).then(getUserResponse);
        }

        //function: updateNameResponse 처리 함수
        const updateNameResponse = (responseBody: UpdateNameResponseDto | ResponseDto | null) => {
            
            if(!responseBody) return;
            
            const { code } = responseBody;

            if (code === 'AF') {
                showModal('Authorization Fail', '인증에 실패했습니다.');
                return;
            }
            if (code === 'DN') {
                showModal('Dupliated Name', '중복되는 닉네임입니다.');
                return;
            }
            if (code === 'NU') {
                showModal('User Error', '존재하지 않는 사용자입니다.');
                return;
            }
            if (code === 'DE') {
                showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
                return;
            }
            if (code !== 'SU') {
                return;
            }
            if(!id) return;

            getUserRequest(id).then(getUserResponse);
            setIsNameChanged(false);
        }

        //event handler: 프로필 박스 클릭 이벤트 처리
        const onProfileBoxClickHandler = () => {
            if (!isMyPage) return;
            if (!imageInputRef.current) return;
            imageInputRef.current.click();
        };

        //event handler: 닉네임 수정 버튼 클릭 이벤트 처리
        const onNameEditButtonClickHandler = () => {
            if(!isNameChanged) {
                setChangeName(name);
                setIsNameChanged(!isNameChanged);
                return;
            }

            if(!cookies.accessToken) return;
            const requestBody: UpdateNameRequestDto = {
                name: changeName
            };
            updateNameRequest(requestBody, cookies.accessToken).then(updateNameResponse);           
        };

        //event handler: 프로필 이미지 변경 이벤트 처리
        const onProfileImageChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
            if (!event.target.files || !event.target.files?.length) return;

            const file = event.target.files[0];
            const data = new FormData();
            data.append('file', file);

            uploadFileRequest(data).then(uploadFileResponse);
        };

        //event handler: 닉네임 변경 이벤트 처리
        const onNameChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
            const { value } = event.target;
            setChangeName(value);
        };

        // useEffect: name path variable 변경 시 실행 할 함수
        useEffect(() => {
            if (!id) return;
            getUserRequest(id).then(getUserResponse);
        }, [id]);

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
                        <div className='user-top-info-email'>{email}</div>
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
