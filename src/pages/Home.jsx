import React from 'react'
import LoginContextConsumer from '../contexts/LoginContextConsumer'
import Layout from '../layouts/Layout'
import { Container } from 'react-bootstrap'
import PostItem from '../components/PostItem'
import Top3Item from '../components/Top3Item'
import CommentItem from '../components/CommentItem'
import { latestPostListMock, top3PostListMock, commentListMock } from '../mocks'

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
                        <div style={{padding: '0 20px', display: 'flex', flexDirection: 'column', gap: '30px'}}>
                        {commentListMock.map(commentListItem => <CommentItem commentListItem={commentListItem}/>)}
                        </div>
                        <h2>메인 페이지</h2>
                        <LoginContextConsumer />
                    </div>
                </Container>
            </Layout>
        </>
    )
}

export default Home