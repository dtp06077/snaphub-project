import React from 'react'
import LoginContextConsumer from '../contexts/LoginContextConsumer'
import { Container } from 'react-bootstrap'

const Home = () => {
    return (
        <>
            <Container style={{ minHeight: '75vh' }}>
                <div className='container'>
                    <h1>Home</h1>
                    <hr />
                    <h2>메인 페이지</h2>
                    <LoginContextConsumer />
                </div>
            </Container>

        </>
    )
}

export default Home