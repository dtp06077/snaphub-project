import React from 'react'
import LoginContextConsumer from '../contexts/LoginContextConsumer'
import Layout from '../layouts/Layout'

const About = () => {
    return (
        <>
            <Layout>
            <div className='container'>
                <h1>About</h1>
                <hr />
                <h2>소개 페이지</h2>
                <LoginContextConsumer/>
            </div>
            </Layout>
        </>
    )
}

export default About