import React, { createContext, useEffect, useState } from 'react';
import api from '../apis/api';
import Cookies from 'js-cookie';
import * as auth from '../apis/auth';
import { useNavigate } from 'react-router-dom';

export const LoginContext = createContext();
LoginContext.displayName = 'LoginContextName';

/**
 * 로그인 세팅
 * 로그아웃 세팅
 */

const LoginContextProvider = ({ children }) => {
    /*
    상태
    -로그인 여부
    -유저 정보
    -권한 정보
    -쿠키 아이디 저장 여부
    */
    /*=====state=====*/
    // 로그인 여부
    const [isLogin, setLogin] = useState(false);

    // 유저 정보
    const [userInfo, setUserInfo] = useState({});

    // 권한 정보
    const [roles, setRoles] = useState({ isUser: false, isAdmin: false });

    //유저 프로필 이미지
    const [profileImage, setProfileImage] = useState('https://reactjs.org/logo-og.png');

    // 아이디 저장
    const [rememberUserId, setRememberUserId] = useState();
    /*==============*/

    // 페이지 이동
    const navigate = useNavigate();

    // 로그인
    const login = async (loginId, password, onHide, resetForm) => {

        console.log(`loginId : ${loginId}`);
        console.log(`password : ${password}`);

        try {
            const response = await auth.login(loginId, password);
            const data = response.data;
            const status = response.status;
            const headers = response.headers;
            const authorization = headers.authorization;
            const accessToken = authorization.replace("Bearer ", ""); //JWT

            console.log(`data : ${data}`);
            console.log(`headers : ${headers}`);
            console.log(`status : ${status}`);
            console.log(`jwt : ${accessToken}`);

            // 로그인 성공
            if (status === 200) {
                //쿠키에 accessToken(jwt) 저장
                Cookies.set("accessToken", accessToken);

                // 로그인 체크 ( /users/{userId} <--- userData)
                loginCheck();

                alert("로그인 성공");

                // 모달창 닫기
                onHide();
                resetForm();
            }
        } catch (error) {
            // 로그인 실패
            alert("아이디 또는 비밀번호가 일치하지 않습니다.");
            return;
        }
    }

    /*
    로그인 체크
    - 쿠키에 jwt가 있는지 확인
    - jwt 로 사용자 정보를 요청
    */
    const loginCheck = async () => {

        // 쿠키에서 jwt 토큰 가져오기
        const accessToken = Cookies.get("accessToken");
        console.log(`accessToken : ${accessToken}`);

        // accessToken (jwt) 이 부재
        if (!accessToken) {
            console.log(`쿠키에 jwt가 없음`)
            // 로그아웃 세팅
            logoutSetting();
            return
        }

        // accessToken (jwt) 이 존재

        // header에 jwt 담기
        api.defaults.headers.common.Authorization = `Bearer ${accessToken}`;

        // 사용자 정보 요청
        let response
        let data

        try {
            response = await auth.info();
        } catch (error) {
            console.log(`error : ${error}`);
            console.log(`status : ${response.status}`);
            return;
        }

        data = response.data;
        console.log(`data : ${data}`);

        //인증 실패
        if (data == 'UNAUTHRIZED' || response.status == 401) {
            console.log('사용자 인증정보 요청 실패');
            return;
        }

        //인증 성공
        console.log(`사용자 인증정보 요청 성공`);
        //로그인 세팅
        loginSetting(data, accessToken);
    }

    //로그인 세팅
    // userData, accessToken (jwt)
    const loginSetting = (userData, accessToken) => {

        const { userId, loginId, email, profile, auths } = userData

        console.log(`userId : ${userId}`);
        console.log(`loginId : ${loginId}`);
        console.log(`email : ${email}`);
        console.log(`auths : ${auths}`);


        //axios 객체의 헤더(Authorization : 'Bearer ${accessToken}')
        api.defaults.headers.common.Authorization = `Bearer ${accessToken}`;

        //로그인 여부 : true
        setLogin(true);

        //프로필 이미지 업데이트
        //웹 서버 배포 시`http://snaphub.com/.../로 변경
        setProfileImage(`${profile}`);
        console.log(`${profile}`);
    

        //유저정보
        const updateUserInfo = { userId, loginId, auths };
        setUserInfo(updateUserInfo);

        //권한정보
        const updatedRoles = { isUser: false, isAdmin: false }

        auths.forEach((role) => {
            if (role == 'ROLE_USER') updatedRoles.isUser = true;
            if (role == 'ROLE_ADMIN') updatedRoles.isAdmin = true;
        });

        setRoles(updatedRoles)
    }

    //로그아웃
    const logout = () => {

        const check = window.confirm('로그아웃 하시겠습니까?');

        if (check) {
            //로그아웃 세팅
            logoutSetting();

            //메인 페이지로 이동
            navigate("/")
        }
    }

    //로그아웃 세팅
    const logoutSetting = () => {
        //axios 헤더 초기화
        api.defaults.headers.common.Authorization = undefined;

        //쿠키 초기화
        Cookies.remove("accessToken");

        //로그인 여부 : false
        setLogin(false);

        //유저 정보 초기화
        setUserInfo(null);

        //프로필 사진 초기화
        setProfileImage('https://reactjs.org/logo-og.png');

        //권한 정보 초기화
        setRoles(null);
    }

    useEffect( () => {
        // 로그인 체크
        loginCheck()
    }, [])
    return (
        <LoginContext.Provider value={{ profileImage, isLogin, userInfo, roles, login, logout }}>
            {children}
        </LoginContext.Provider>
    )
}

export default LoginContextProvider