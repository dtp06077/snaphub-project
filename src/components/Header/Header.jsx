import React, { useContext, useState } from 'react'
import './Header.css'
import { Link } from 'react-router-dom'
import { LoginContext } from '../../contexts/LoginContextProvider'
import JoinModal from '../../modals/JoinModal'

const Header = () => {

    //isLogin : 로그인 여부 - 로그인(true), 비로그인(false)
    //logout() : 로그아웃 함수 -> setLogin(false)
    const { isLogin, login, logout } = useContext(LoginContext);
    const [ JoinModalOn, setJoinModalOn ] = useState(false);

    return (
        <header>
            <JoinModal
            show={JoinModalOn}
            onHide={()=>setJoinModalOn(false)}
            />
            <div className='logo'>
                <Link to="/">
                    <img src="https://i.imgur.com/fzADqJo.png" alt="logo" className='logo' />
                </Link>
            </div>

            <div className='util'>
                {
                    !isLogin
                        ?
                        //비로그인 시
                        <ul>
                            <li><Link to="/login">로그인</Link></li>
                            <li><button className='link' onClick={()=>setJoinModalOn(true)}>회원가입</button></li>
                            <li><Link to="/about">소개</Link></li>
                        </ul>
                        :
                        //로그인 시
                        <ul>
                            <li><Link to="/user">마이페이지</Link></li>
                            <li><button className='link' onClick={ () => logout() } >로그아웃</button></li>
                        </ul>
                }
            </div>

        </header>
    )
}

export default Header