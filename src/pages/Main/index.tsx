import React, { useEffect, useState } from 'react'
import Top3Item from '../../components/Top3Item'
import { PostListItem } from '../../types/interface'
import { latestPostListMock, top3PostListMock } from '../../mocks';
import PostItem from '../../components/PostItem';
import Pagination from '../../components/Pagination';
import './style.css';

//component: 메인 화면 컴포넌트
export default function Main() {

    //component: 메인 화면 상단 컴포넌트
    const MainTop = () => {

        //state: 주간 top3 게시물 리스트 상태
        const [top3PostList, setTop3PostList] = useState<PostListItem[]>([]);

        //effect: 첫 마운트 시 실행될 함수
        useEffect(() => {
            setTop3PostList(top3PostListMock);
        }, []);

        //render: 메인 화면 상단 컴포넌트 렌더링
        return (
            <div id='main-top-wrapper'>
                <div className='main-top-container'>
                    <div className='main-top-title'>{'SnapHub 입니다. \n 방문하신 것을 환영합니다.'}</div>
                    <div className='main-top-contents-box'>
                        <div className='main-top-contents-title'>{'주간 TOP 3 게시글'}</div>
                        <div className='main-top-contents'>
                            {top3PostList.map(top3PostListItem => <Top3Item top3ListItem={top3PostListItem} />)}
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    //component: 메인 화면 하단 컴포넌트
    const MainBottom = () => {

        //state: 최신 게시물 리스트 상태
        const [currentPostList, setCurrentPostList] = useState<PostListItem[]>([]);
        //state: 인기 검색어 리스트 상태
        const [popularWordList, setPopularWordList] = useState<string[]>([]);

        //effect: 첫 마운트 시 실행될 함수
        useEffect(() => {
            setCurrentPostList(latestPostListMock);
            setPopularWordList(['하이','하이요','방가']);
        }, []);

        //render: 메인 화면 하단 컴포넌트 렌더링
        return (
            <div id='main-bottom-wrapper'>
                <div className='main-bottom-container'>
                    <div className='main-bottom-title'>{'최신 게시물'}</div>
                    <div className='main-bottom-contents-box'>
                        <div className='main-bottom-current-contents'>
                            {currentPostList.map(postListItem => <PostItem postListItem={postListItem} />)}
                        </div>
                        <div className='main-bottom-popular-box'>
                            <div className='main-bottom-popular-card'>
                                <div className='main-bottom-popular-card-box'>
                                    <div className='main-bottom-popular-card-title'>{'인기 검색어'}</div>
                                    <div className='main-bottom-popular-card-contents'>
                                        {popularWordList.map(word => <div className='word-badge'>{word}</div>)}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className='main-bottom-pagination-box'>
                        {/* <Pagination/> */}
                    </div>
                </div>
            </div>
        )
    }

    //render: 메인 화면 컴포넌트 렌더링
    return (
        <div>
            <MainTop />
            <MainBottom />
        </div>
    )
}
