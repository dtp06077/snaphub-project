import React, { createContext, useState } from 'react';
import api from '../apis/api';
import Cookies from 'js-cookie';
import * as auth from '../apis/auth';

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

    // 로그인 여부
    const [isLogin, setLogin] = useState(false);

    // 유저 정보
    const [userInfo, setUserInfo] = useState({});

    // 권한 정보
    const [roles, setRoles] = useState({ isUser: false, isAdmin: false });

    // 아이디 저장
    const [rememberUserId, setRememberUserId] = useState();

    /*
    로그인 체크
    - 쿠키에 jwt가 있는지 확인
    - jwt 로 사용자 정보를 요청
    */
    const loginCheck = async () => {

        //
        const accessToken = Cookies.get("accessToken");
        console.log("accessToken : ${accessToken}");

        let response
        let data

        response = await auth.info();

        data = response.data;
        console.log('data : ${data}');

        //인증 성공
        //로그인 세팅
        loginSetting(data, accessToken)
    }


    // 로그인
    const login = async (loginId, password) => {

        console.log("loginId : ${loginId}");
        console.log("password : ${password}");

        const response = await auth.login(loginId, password);
        const data = response.data;
        const status = response.status;
        const headers = response.headers;
        const authroization = headers.authroization;
        const accessToken = authroization.replace("Bearer ", ""); //JWT

        console.log("data : ${data}");
        console.log("headers : ${headers}");
        console.log("status : ${status}");
        console.log("jwt : ${jwt}");

        // 로그인 성공
        if (status == 200) {
            //쿠키에 accessToken(jwt) 저장
            Cookies.set("accessToken", accessToken);

            // 로그인 체크 ( /users/{userId} <--- userData)
            loginCheck();

            alert("로그인 성공")
        }
    }

    //로그인 세팅
    // userData, accessToken (jwt)
    const loginSetting = (userData, accessToken) => {

        const { userId, loginId, authList } = userData
        const roleList = authList.map((auth) => auth.auth)

        console.log("userId : ${userId}");
        console.log("loginId : ${loginId}");
        console.log("authList : ${authList}");
        console.log("roleList : ${roleList}");

        //axios 객체의 헤더(Authorization : 'Bearer ${accessToken}')
        api.defaults.headers.common.Authorization = 'Bearer ${accessToken}';

        //로그인 여부 : true
        setLogin(true);

        //유저정보
        const updateUserInfo = { userId, loginId, roleList };
        setUserInfo(updateUserInfo);

        //권한정보
        const updatedRoles = { isUser: false, isAdmin: false }

        roleList.forEach((role) => {
            if (role == 'ROLE_USER') updatedRoles.isUser = true;
            if (role == 'ROLE_ADMIN') updatedRoles.isAdmin = true;
        });

        setRoles(updatedRoles)
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

        //권한 정보 초기화
        setRoles(null);
    }

    const logout = () => {
        setLogin(false)
    }

    return (
        <LoginContext.Provider value={{ isLogin, logout }}>
            {children}
        </LoginContext.Provider>
    )
}

export default LoginContextProvider