import React from 'react'
import './LoginForm.css'

const LoginForm = () => {

    const onLogin = () => {

    }

    return (
        <div className='form'>
            <h2 className="login-title">Login</h2>

            <form className='login-form' onSubmit={(e) => onLogin}>
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
                    <input type="text"
                        id='password'
                        placeholder='password'
                        name='password'
                        autoComplete='password'
                        required
                        // defaultValue={}   

                    />
                </div>

                <button className='btn btn--form btn-login'>
                    Login
                </button>
            </form>
        </div>

    )
}

export default LoginForm