import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import User from './pages/User';
import Search from './pages/Search';
import PostDetail from './pages/Post/Detail';
import PostUpdate from './pages/Post/Update';
import PostWrite from './pages/Post/Write';
import LoginContextProvider from './contexts/LoginContextProvider';
import Wrapper from './layouts/wrapper/Wrapper';
import { MAIN_PATH, USER_PATH, SEARCH_PATH, POST_PATH, POST_DETAIL_PATH, POST_WRITE_PATH, POST_UPDATE_PATH } from './constants'
import EventModalContextProvider from './contexts/EventModalProvider';
import { useEffect } from 'react';
import { useCookies } from 'react-cookie';

//component: Application 컴포넌트
function App() {
    //state: cookie 상태
    const [cookies, setCookie] = useCookies();

    //effect: accessToken cookie 값이 변경 될 때 마다 실행할 함수
    useEffect(()=> {
        if (!cookies.accessToken) {
            
        }
    }, []);

    //render: Application 렌더링
    /**
     * 메인 화면(Main): '/'
     * 로그인 + 회원가입(Modal): '/'
     * 검색 화면(Search): '/search/:searchWord'
     * 게시물 상세보기(PostDetail): '/post/detail/:postId'
     * 게시물 작성하기(PostWrite): '/post/write'
     * 게시물 수정하기(PostUpdate): '/post/update/:postId'
     * 사용자 페이지(User): '/users/:loginId'
     */
    return (
        <BrowserRouter>
        <EventModalContextProvider>
        <LoginContextProvider>
                <Routes>
                    <Route element={<Wrapper />}>
                        <Route path={MAIN_PATH()} element={<Home />}></Route>
                        <Route path={USER_PATH(':loginId')} element={<User />}></Route>
                        <Route path={SEARCH_PATH(':searchWord')} element={<Search />}></Route>
                        <Route path={POST_PATH()}>
                            <Route path={POST_WRITE_PATH()} element={<PostWrite />}></Route>
                            <Route path={POST_UPDATE_PATH(':postId')} element={<PostUpdate />}></Route>
                            <Route path={POST_DETAIL_PATH(':postId')} element={<PostDetail />}></Route>
                        </Route>
                        <Route path='*' element={<h1>404 Not Found</h1>}/>
                    </Route>
                </Routes>
            </LoginContextProvider>
        </EventModalContextProvider>
        </BrowserRouter>
    );
}

export default App;
