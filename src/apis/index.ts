import { JoinRequestDto } from "./request/auth";
import { LoginResponseDto } from "./response/auth";
import { ResponseDto } from "./response";
import api from "./api";
import { UserInfoResponseDto } from "./response/user";

const DOMAIN = 'http://localhost:4000';

const API_DOMAIN = `${DOMAIN}`;

const LOGIN_URL = () => `${API_DOMAIN}/auth/login`;
const JOIN_URL = () => `${API_DOMAIN}/auth/join`;
const USER_INFO_URL = () => `${API_DOMAIN}/users/info`;

//로그인 리퀘스트
export const loginRequest = async (loginId: string, password: string) => {
    const result = await api.post(`${LOGIN_URL()}?loginId=${loginId}&password=${password}`)
        .then(response => {
            const responseBody: LoginResponseDto = response.data;
            return {response, responseBody};
        })
        .catch(error => {
            if (!error.response.data) return null;
            const responseBody: ResponseDto = error.response.data;
            return {response: error.response, responseBody};
        })
    return result;
}

export const joinRequest = async (requestBody: JoinRequestDto) => {
    const result = await api.get(`${USER_INFO_URL()}`)
        .then(response => {
            const responseBody: UserInfoResponseDto = response.data;
            return responseBody;
        })
}

export const userInfoRequest = async (accessToken: string) => {

}