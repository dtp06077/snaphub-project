import React from 'react'
import LoginContextConsumer from '../contexts/LoginContextConsumer'
import Layout from '../layouts/Layout'
import { Container } from 'react-bootstrap'
import PostItem from '../components/PostItem'
import Top3Item from '../components/Top3Item'

import { latestPostListMock, top3PostListMock } from '../mocks'

const Home = () => {
    return (
        <>
            <Layout>
                <Container style={{ minHeight: '75vh' }}>
                    <div className='container'>
                        <h1>Home</h1>
                        <hr />
                        {top3PostListMock.map(top3ListItem => <Top3Item top3ListItem={top3ListItem}/>)}
                        {latestPostListMock.map(postListItem => <PostItem postListItem={postListItem}/>)}
                        <h2>메인 페이지</h2>
                        <LoginContextConsumer />
                    </div>
                </Container>
            </Layout>
        </>
    )
}

export default Home