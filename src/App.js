import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import User from './pages/User';
import About from './pages/About';
import LoginContextProvider from './contexts/LoginContextProvider';

//component: Application 컴포넌트
function App() {

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
            <LoginContextProvider>
                <Routes>
                    <Route path="/" element={<Home />}></Route>
                    <Route path="/user" element={<User />}></Route>
                    <Route path="/about" element={<About />}></Route>
                </Routes>
            </LoginContextProvider>
        </BrowserRouter>
    );
}

export default App;
