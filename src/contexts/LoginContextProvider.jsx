import React, { createContext, useState } from 'react';
import api from '../apis/api';
import Cookies from 'js-cookie';

export const LoginContext = createContext();
LoginContext.displayName = 'LoginContextName';

/**
 * 로그인 세팅
 * 로그아웃 세팅
 */

const LoginContextProvider = ({children}) => {
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
    const [roles, setRoles] = useState({isUser : false, isAdmin : false});

    // 아이디 저장
    const [rememberUserId, setRememberUserId] = useState();

    //로그인 세팅
    // userData, accessToken (jwt)
    const loginSetting = (userData, accessToken) => {

        const{ userId, loginId, authList }= userData
        const roleList = authList.map((auth) => auth.auth)

        console.log("userId : ${userId}");
        console.log("loginId : ${loginId}");
        console.log("authList : ${authList}");
        console.log("roleList : ${roleList}");

        //axios 객체의 헤더(Authorization : 'Bearer ${accessToken}')
        api.defaults.headers.common.Authorization = 'Bearer ${accessToken}';
    
        //쿠키에 accessToken(jwt) 저장
        Cookies.set("accessToken", accessToken);

        //로그인 여부 : true
        setLogin(true);

        //유저정보
        const updateUserInfo = {userId, loginId, roleList};
        setUserInfo(updateUserInfo);

        //권한정보
        const updatedRoles = {isUser : false, isAdmin : false}

        roleList.forEach((role) => {
            if(role == 'ROLE_USER') updatedRoles.isUser = true;
            if(role == 'ROLE_ADMIN') updatedRoles.isAdmin = true;
        });

        setRoles(updatedRoles)
    }

    const logout = () => {
        setLogin(false)
    }

    return (
            <LoginContext.Provider value={{isLogin, logout}}>
                {children}
            </LoginContext.Provider>
    )
}

export default LoginContextProvider