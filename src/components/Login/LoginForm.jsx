import React, { useContext } from 'react'
import './LoginForm.css'
import { LoginContext } from '../../contexts/LoginContextProvider';

const LoginForm = () => {

    const { login } = useContext(LoginContext);

    const onLogin = (e) => {
        //데이터 셋팅

        e.preventDefault();

        const form = e.target;
        const loginId = form.loginId.value;
        const password = form.password.value;

        login(loginId, password);

    }

    return (
        <div className='form'>
            <h2 className="login-title">Login</h2>

            <form className='login-form' onSubmit={(e) => onLogin(e)}>
                <div>
                    <label htmlFor="loginId">loginId</label>
                    <input type="text"
                        id='loginId'
                        placeholder='loginId'
                        name='loginId'
                        autoComplete='loginId'
                        required
                        //TODO: 아이디 저장 기능 구현 후 추가
                        // defaultValue={}   

                    />
                </div>

                <div>
                    <label htmlFor="password">password</label>
                    <input type="password"
                        id='password'
                        placeholder='password'
                        name='password'
                        autoComplete='password'
                        required
                        // defaultValue={}   

                    />
                </div>

                <button type='submit' className='btn btn--form btn-login'>
                    Login
                </button>
            </form>
        </div>

    )
}

export default LoginForm