import api from "./api"


//로그인
export const login = (loginId, password) => api.post(`/login?loginId=${loginId}&password=${password}`)

//사용자 정보
export const info = () => api.get(`/users/info`) 

// 회원 가입
export const join = (data) => {
    const formData = new FormData();
    
    // data 객체의 각 속성을 FormData에 추가
    for (const key in data) {
        formData.append(key, data[key]);
    }

    return api.post(`/users`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
};

//아이디 중복 확인
export const checkLoginId = (loginId) => api.get(`/users/check-loginId?loginId=${loginId}`)

//닉네임 중복 확인
export const checkName = (name) => api.get(`/users/check-name?name=${name}`)

//회원 정보 수정
export const update = (data) => api.put(`/users`, data)

//회원 탈퇴
export const remove = (userId) => api.delete(`/users/${userId}`)