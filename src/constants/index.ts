export const MAIN_PATH = () => `/`;
export const SEARCH_PATH = (searchWord: string) => `/search/${searchWord}`;
export const USER_PATH = (loginId: string) => `/users/${loginId}`;
export const POST_PATH = () => `/post`;
export const POST_DETAIL_PATH = (postId: string | number) => `detail/${postId}`;
export const POST_WRITE_PATH = () => 'write';
export const POST_UPDATE_PATH = (postId : string | number) => `update/${postId}`;