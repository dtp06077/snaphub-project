import React from 'react'
import Header from '../components/layouts/Header'
import LoginContextConsumer from '../contexts/LoginContextConsumer'
import JoinForm from '../components/Join/JoinForm'

const Join = () => {
    return (
        <>
            <Header />
            <div className='container'>
                <JoinForm/>
            </div>
        </>
    )
}

export default Join