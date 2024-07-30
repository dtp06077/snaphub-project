import React from 'react'
import Header from '../components/layouts/Header/Header'
import LoginForm from '../components/Login/LoginForm'

const Login = () => {
    return (
        <>
            <Header />
            <div className='container'>
            <LoginForm/>
            </div>
        </>
    )
}

export default Login