import React, { createContext, useEffect, useState } from 'react';
import api from '../apis/api';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';
import defaultImage from '../assets/image/default-profile-image.png';
import { loginRequest, userInfoRequest } from '../apis'
import { TOKEN_PREFIX } from '../constants';

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
    const [profileImage, setProfileImage] = useState(defaultImage);

    // 아이디 저장
    const [rememberUserId, setRememberUserId] = useState();
    /*==============*/

    // 페이지 이동
    const navigate = useNavigate();

    // 로그인
    const login = async (loginId, password, onHide, resetForm) => {

        const result = await loginRequest(loginId, password);

        if (result) {
            const { response, responseBody } = result;

            if (response.status === 200) {
                const headers = response.headers;
                const accessToken = headers.authorization; //JWT

                //쿠키에 accessToken(jwt) 저장
                Cookies.set("accessToken", accessToken.replace(TOKEN_PREFIX(), ""));

                // 로그인 체크 
                //TODO
                try {
                    loginCheck();

                    alert("로그인에 성공하였습니다.");

                    // 모달창 닫기
                    onHide();
                    resetForm();
                } catch (error) {
                    console.error(error.message);
                    return;
                }
            }
            else {
                if (responseBody.code == 'AF') {
                    console.log('인증 실패')
                    alert("아이디 또는 비밀번호가 일치하지 않습니다.")
                }
                else if (responseBody.code == 'DE') {
                    console.log('데이터베이스 에러')
                    alert("데이터베이스에 접근하는 데 문제가 발생했습니다.")
                }
                return;
            }
        }

        else {
            console.log('네트워크 오류 또는 서버 응답 없음');
            alert("네트워크 또는 서버에 오류가 발생하였습니다.");
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

        // accessToken (jwt) 이 부재
        if (!accessToken) {
            console.log(`쿠키에 jwt 부재`)
            // 에러 메시지 출력
            throw new Error("JWT 토큰이 쿠키에 존재하지 않습니다.");
        }

        // accessToken (jwt) 이 존재
        const result = await userInfoRequest(accessToken);

        if (result) {
            const { response, responseBody } = result;

            if (response) {
                //인증 성공
                console.log(`사용자 인증정보 요청 성공`);
                //로그인 세팅
                loginSetting(responseBody, accessToken);
            }
            else {
                throw new Error("존재하지 않는 사용자입니다.");
            }
        }
        else {
            throw new Error("네트워크 또는 서버에 오류가 발생하였습니다.");
        }
    }

    //로그인 세팅
    // userData, accessToken (jwt)
    const loginSetting = (userData, accessToken) => {

        const { userId, loginId, email, name, profile, telNumber, address, addressDetail, roles } = userData

        console.log(`userId : ${userId}`);
        console.log(`name : ${name}`);
        console.log(`loginId : ${loginId}`);
        console.log(`email : ${email}`);
        console.log(`roles : ${roles}`);

        //로그인 여부 : true
        setLogin(true);

        //프로필 이미지 업데이트
        //웹 서버 배포 시`http://snaphub.com/.../로 변경
        setProfileImage(`${profile}`);
        console.log(`${profile}`);


        //유저정보
        const updateUserInfo = { userId, loginId, roles };
        setUserInfo(updateUserInfo);

        //권한정보
        const updatedRoles = { isUser: false, isAdmin: false }

        roles.forEach((role) => {
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
        setProfileImage(defaultImage);

        //권한 정보 초기화
        setRoles(null);
    }

    useEffect(() => {
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