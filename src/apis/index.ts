import { JoinRequestDto } from "./request/auth";
import { JoinResponseDto, LoginResponseDto } from "./response/auth";
import { ResponseDto } from "./response";
import api from "./api";
import { UserInfoResponseDto } from "./response/user";
import { UploadPostRequestDto, WriteCommentRequestDto } from "./request/post";
import { UploadPostResponseDto, GetPostResponseDto, IncreaseViewCountResponseDto, GetEmotionListResponseDto, GetCommentListResponseDto, PutEmotionResponseDto, WriteCommentResponsetDto, DeletePostResponseDto } from "./response/post";

const DOMAIN = 'http://localhost:4000';

const API_DOMAIN = `${DOMAIN}`;
const FILE_DOMAIN = `${DOMAIN}/file`;

//header에 accessToken 담기
const authorization = (accessToken: string) => {
    return { headers: { Authorization: `Bearer ${accessToken}` } };
};

const LOGIN_URL = () => `${API_DOMAIN}/auth/login`;
const JOIN_URL = () => `${API_DOMAIN}/auth/join`;
const USER_INFO_URL = () => `${API_DOMAIN}/users/info`;
const UPLOAD_FILE_URL = () => `${FILE_DOMAIN}/upload`;
const UPLOAD_POST_URL = () => `${API_DOMAIN}/post`;
const GET_POST_URL = (postId: number | string) => `${API_DOMAIN}/post/${postId}`;
const INCREASE_VIEW_COUNT_URL = (postId: number | string) => `${API_DOMAIN}/post/${postId}/increase-view-count`;
const GET_EMOTION_LIST_URL = (postId: number | string) => `${API_DOMAIN}/post/${postId}/emotion-list`;
const GET_COMMENT_LIST_URL = (postId: number | string) => `${API_DOMAIN}/post/${postId}/comment-list`;
const PUT_EMOTION_URL = (postId: number | string) => `${API_DOMAIN}/post/${postId}/emotion`;
const WRITE_COMMENT_URL = (postId: number | string) => `${API_DOMAIN}/post/${postId}/comment`;
const DELETE_POST_URL = (postId: number | string) => `${API_DOMAIN}/post/${postId}`;

const multipartFormData = { headers: { 'Content-Type': 'multipart/form-data' } };

//로그인 리퀘스트
export const loginRequest = async (loginId: string, password: string) => {
    const result = await api.post(LOGIN_URL() + `?loginId=${loginId}&password=${password}`)
        .then(response => {
            const responseBody: LoginResponseDto = response.data;
            return { response, responseBody };
        })
        .catch(error => {
            if (!error.response) return null;
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
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return { response: error.response, responseBody };
        })
    return result;
}

//로그인 아이디 중복 확인 리퀘스트
export const checkLoginIdRequest = async (loginId: string) => {
    const result = await api.get(JOIN_URL() + `/check-loginId?loginId=${loginId}`)
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
export const checkNameRequest = async (name: string) => {
    const result = await api.get(JOIN_URL() + `/check-name?name=${name}`)
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
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return { response: error.response, responseBody };
        })
    return result;
}

//게시물 업로드 리퀘스트
export const uploadPostRequest = async (requestBody: UploadPostRequestDto, accessToken: string) => {
    const result = await api.post(UPLOAD_POST_URL(), requestBody, authorization(accessToken))
        .then(response => {
            const responseBody: UploadPostResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody
        })
    return result;
}

//게시물 삭제 리퀘스트
export const deletePostRequest = async (postId: number | string, accessToken: string) => {
    const result = await api.delete(DELETE_POST_URL(postId), authorization(accessToken))
        .then(response => {
            const responseBody: DeletePostResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody
        })
    return result;
}

//게시물 불러오기 리퀘스트
export const getPostRequest = async (postId: number | string) => {
    const result = await api.get(GET_POST_URL(postId))
        .then(response => {
            const responseBody: GetPostResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody
        })
    return result;
}

//게시물 조회수 증가 리퀘스트
export const increaseViewCountRequest = async (postId: number | string) => {
    const result = await api.get(INCREASE_VIEW_COUNT_URL(postId))
        .then(response => {
            const responseBody: IncreaseViewCountResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody
        })
    return result;
}

//게시물 감정표현 리스트 불러오기 리퀘스트
export const getEmotionListRequest = async (postId: number | string) => {
    const result = await api.get(GET_EMOTION_LIST_URL(postId))
        .then(response => {
            const responseBody: GetEmotionListResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody
        })
    return result;
}

//게시물 댓글 리스트 불러오기 리퀘스트
export const getCommentListRequest = async (postId: number | string) => {
    const result = await api.get(GET_COMMENT_LIST_URL(postId))
        .then(response => {
            const responseBody: GetCommentListResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        })
    return result;
}

//감정표현 등록 리퀘스트
export const putEmotionRequest = async (postId: number | string, status: string, accessToken: string) => {
    const result = await api.put(PUT_EMOTION_URL(postId) + `?status=${status}`, {}, authorization(accessToken))
        .then(response => {
            const responseBody: PutEmotionResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        })
    return result;
}

//댓글 등록 리퀘스트
export const WriteCommentRequest = async (postId: number | string, requestBody: WriteCommentRequestDto, accessToken: string) => {
    const result = await api.post(WRITE_COMMENT_URL(postId), requestBody, authorization(accessToken))
        .then(response => {
            const responseBody: WriteCommentResponsetDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        })
    return result;
}
//파일 업로드 리퀘스트
export const uploadFileRequest = async (data: FormData) => {
    const result = await api.post(UPLOAD_FILE_URL(), data, multipartFormData)
        .then(response => {
            const responseBody: string = response.data;
            return responseBody;
        })
        .catch(error => {
            return null;
        })
    return result;
}