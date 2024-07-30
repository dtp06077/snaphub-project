import React from 'react'
import LoginContextConsumer from '../contexts/LoginContextConsumer'
import Layout from '../components/layouts/Layout'
import { Container } from 'react-bootstrap'

const Home = () => {
    return (
        <>
            <Layout>
                <Container>
                    <div className='container'>
                        <h1>Home</h1>
                        <hr />
                        <h2>메인 페이지</h2>
                        <LoginContextConsumer />
                    </div>
                </Container>
            </Layout>
        </>
    )
}

export default Home