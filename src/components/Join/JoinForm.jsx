import React from 'react'

const JoinForm = () => {
    const onJoin = () => {

    }

    return (
        <div className='form'>
            <h2 className="login-title">Join</h2>

            <form className='login-form' onSubmit={ (e) => onJoin }>
                <div>
                    <label htmlFor="loginId">loginId</label>
                    <input type="text"
                        id='loginId'
                        placeholder='loginId'
                        name='loginId'
                        autoComplete='loginId'
                        required
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
                    />
                </div>

                <div>
                    <label htmlFor="nickname">nickname</label>
                    <input type="text"
                        id='nickname'
                        placeholder='nickname'
                        name='nickname'
                        autoComplete='nickname'
                        required
                    />
                </div>

                <div>
                    <label htmlFor="email">email</label>
                    <input type="text"
                        id='email'
                        placeholder='email'
                        name='email'
                        autoComplete='email'
                        required
                    />
                </div>

                <button submit = 'submit' className='btn btn--form btn-login'>
                    Join
                </button>
            </form>
        </div>
    )
}

export default JoinForm