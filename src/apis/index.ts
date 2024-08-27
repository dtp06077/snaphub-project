import { JoinRequestDto } from "./request/auth";
import { JoinResponseDto, LoginResponseDto } from "./response/auth";
import { ResponseDto } from "./response";
import api from "./api";
import { UserInfoResponseDto } from "./response/user";

const DOMAIN = 'http://localhost:4000';

const API_DOMAIN = `${DOMAIN}`;

//header에 accessToken 담기
const authorization = (accessToken: string) => {
    return { headers: { Authorization: `Bearer ${accessToken}` } };
};

const LOGIN_URL = () => `${API_DOMAIN}/auth/login`;
const JOIN_URL = () => `${API_DOMAIN}/auth/join`;
const USER_INFO_URL = () => `${API_DOMAIN}/users/info`;

//로그인 리퀘스트
export const loginRequest = async (loginId: string, password: string) => {
    const result = await api.post(LOGIN_URL()+`?loginId=${loginId}&password=${password}`)
        .then(response => {
            const responseBody: LoginResponseDto = response.data;
            return { response, responseBody };
        })
        .catch(error => {
            if (!error.response.data) return null;
            const responseBody: ResponseDto = error.response.data;
            return { response: error.response, responseBody };
        })
    return result;
}

//회원가입 리퀘스트
export const joinRequest = async (requestBody: JoinRequestDto) => {
    const result = await api.post(JOIN_URL(), requestBody)
        .then(response => {
            const responseBody: JoinResponseDto = response.data;
            return { response, responseBody };
        })
        .catch(error => {
            if (!error.response.data) return null;
            const responseBody: ResponseDto = error.response.data;
            return { response: error.response, responseBody };
        })
    return result;
}

//로그인 아이디 중복 확인 리퀘스트
export const loginIdCheckRequest = async (loginId: string) => {
    const result = await api.get(JOIN_URL()+`/check-loginId?loginId=${loginId}`)
        .then(response => {
            return response;
        })
        .catch(error => {
            if (!error.response) return null;
            return error.response;
        })
    return result;
}

//닉네임 중복 확인 리퀘스트
export const nameCheckRequest = async (name: string) => {
    const result = await api.get(JOIN_URL()+`/check-name?name=${name}`)
        .then(response => {
            return response;
        })
        .catch(error => {
            if (!error.response) return null;
            return error.response;
        })
    return result;
}

//사용자 정보 리퀘스트
export const userInfoRequest = async (accessToken: string) => {
    
    const result = await api.get(USER_INFO_URL(), authorization(accessToken))
        .then(response => {
            const responseBody: UserInfoResponseDto = response.data;
            return { response, responseBody };
        })
        .catch(error => {
            if (!error.response.data) return null;
            const responseBody: ResponseDto = error.response.data;
            return { response: error.response, responseBody };
        })
    return result;
}