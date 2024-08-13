import React from 'react'
import LoginContextConsumer from '../contexts/LoginContextConsumer'
import Layout from '../layouts/Layout'

const User = () => {
    return (
        <>
            <Layout>
            <div className='container'>
                <h1>User</h1>
                <hr />
                <h2>마이 페이지</h2>
                <LoginContextConsumer/>
            </div>
            </Layout>
        </>
    )
}

export default User