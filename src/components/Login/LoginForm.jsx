import React from 'react'

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
            </form>
        </div>

    )
}

export default LoginForm