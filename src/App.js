import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import User from './pages/User';
import Main from './pages/Main';
import Search from './pages/Search';
import PostDetail from './pages/Post/Detail';
import PostUpdate from './pages/Post/Update';
import PostWrite from './pages/Post/Write';
import LoginContextProvider from './contexts/LoginContextProvider';
import Wrapper from './layouts/wrapper/Wrapper';
import { MAIN_PATH, USER_PATH, SEARCH_PATH, POST_PATH, POST_DETAIL_PATH, POST_WRITE_PATH, POST_UPDATE_PATH } from './constants'
import EventModalContextProvider from './contexts/EventModalProvider';
import ClickEventModalContextProvider from './contexts/ClickEventModalContextProvider';
import { useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { useLoginUserStore } from './stores';
import { userInfoRequest } from './apis';

//component: Application 컴포넌트
function App() {
    // state: 로그인 유저 전역 상태
    const { setLoginUser, resetLoginUser } = useLoginUserStore();
    //state: cookie 상태
    const [cookies, setCookies] = useCookies();

    const getUserResponse = ({ response, responseBody }) => {
        if (!responseBody) return;

        const { code } = responseBody;

        if (code === 'AF' || code === 'NU' || code === 'DE') {
            resetLoginUser();
            return;
        }
        const loginUser = { ...responseBody };
        setLoginUser(loginUser);
    }
    //effect: accessToken cookie 값이 변경 될 때 마다 실행할 함수
    useEffect(() => {
        if (!cookies.accessToken) {
            resetLoginUser();
            return;
        }
        userInfoRequest(cookies.accessToken).then(getUserResponse);
    }, []);

    //render: Application 렌더링
    /**
     * 메인 화면(Main): '/'
     * 로그인 + 회원가입(Modal): '/'
     * 검색 화면(Search): '/search/:searchWord'
     * 게시물 상세보기(PostDetail): '/post/detail/:postId'
     * 게시물 작성하기(PostWrite): '/post/write'
     * 게시물 수정하기(PostUpdate): '/post/update/:postId'
     * 사용자 페이지(User): '/users/:userId'
     */
    return (
        <BrowserRouter>
            <EventModalContextProvider>
                <ClickEventModalContextProvider>
                    <LoginContextProvider>
                        <Routes>
                            <Route element={<Wrapper />}>
                                <Route path={MAIN_PATH()} element={<Main />}></Route>
                                <Route path={USER_PATH(':userId')} element={<User />}></Route>
                                <Route path={SEARCH_PATH(':searchWord')} element={<Search />}></Route>
                                <Route path={POST_PATH()}>
                                    <Route path={POST_WRITE_PATH()} element={<PostWrite />}></Route>
                                    <Route path={POST_UPDATE_PATH(':postId')} element={<PostUpdate />}></Route>
                                    <Route path={POST_DETAIL_PATH(':postId')} element={<PostDetail />}></Route>
                                </Route>
                                <Route path='*' element={<h1>404 Not Found</h1>} />
                            </Route>
                        </Routes>
                    </LoginContextProvider>
                </ClickEventModalContextProvider>
            </EventModalContextProvider>
        </BrowserRouter>
    );
}

export default App;
