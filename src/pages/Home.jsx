import React from 'react'
import LoginContextConsumer from '../contexts/LoginContextConsumer'
import Layout from '../layouts/Layout'
import { Container } from 'react-bootstrap'
import PostListItem from '../components/PostListItem'

const Home = () => {
    return (
        <>
            <Layout>
                <Container style={{minHeight: '75vh'}}>
                    <div className='container'>
                        <h1>Home</h1>
                        <hr />
                        <PostListItem/>
                        <h2>메인 페이지</h2>
                        <LoginContextConsumer />
                    </div>
                </Container>
            </Layout>
        </>
    )
}

export default Home