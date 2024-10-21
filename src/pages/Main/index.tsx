import React, { useContext, useEffect, useState } from 'react'
import Top3Item from '../../components/Top3Item'
import { PostListItem } from '../../types/interface'
import PostItem from '../../components/PostItem';
import Pagination from '../../components/Pagination';
import './style.css';
import { useNavigate } from 'react-router-dom';
import { SEARCH_PATH } from '../../constants';
import { getLatestPostListRequest, getPopularSearchListRequest, getTop3PostListRequest } from '../../apis';
import { GetLatestPostListResponseDto, GetTop3PostListResponseDto } from '../../apis/response/post';
import { ResponseDto } from '../../apis/response';
import { EventModalContext } from '../../contexts/EventModalProvider';
import { usePagination } from '../../hooks';
import { GetPopularSearchListResponseDto } from '../../apis/response/search';

//component: 메인 화면 컴포넌트
export default function Main() {

    //context: 이벤트 모달 창
    const eventContext = useContext(EventModalContext);

    //function: 네비게이트 함수
    const navigate = useNavigate();

    // eventContext가 undefined일 경우
    if (!eventContext) {
        throw new Error("이벤트 모달 창이 존재하지 않습니다.");
    }

    const { showModal } = eventContext;

    //component: 메인 화면 상단 컴포넌트
    const MainTop = () => {

        //state: 주간 top3 게시물 리스트 상태
        const [top3PostList, setTop3PostList] = useState<PostListItem[]>([]);

        //function : getTop3PostListResponse 처리 함수
        const getTop3PostListResponse = (responseBody: GetTop3PostListResponseDto | ResponseDto | null) => {

            if (!responseBody) return;
            const { code } = responseBody;

            if (code === 'DE') {
                showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
                return;
            }

            if (code !== 'SU') return;

            const { top3List } = responseBody as GetTop3PostListResponseDto;
            setTop3PostList(top3List);
            return;
        };

        //effect: 첫 마운트 시 실행될 함수
        useEffect(() => {
            getTop3PostListRequest().then(getTop3PostListResponse)
        }, []);

        //render: 메인 화면 상단 컴포넌트 렌더링
        return (
            <div id='main-top-wrapper'>
                <div className='main-top-container'>
                    <div className='main-top-title'>{'인생의 순간을 snapHub에 저장하세요!'}</div>
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

        //state: 페이지네이션 관련 상태 
        const { currentPage, setCurrentPage, currentSection, setCurrentSection, viewList,
            viewPageList, totalSection, setTotalList } = usePagination<PostListItem>(7);

        //state: 인기 검색어 리스트 상태
        const [popularWordList, setPopularWordList] = useState<string[]>([]);

        //event handler: 인기 검색어 클릭 이벤트 처리
        const onPopularWordClickHandler = (word: string) => {
            navigate(SEARCH_PATH(word));
        }

        //function: getLatestPostListResponse 처리 함수
        const getLatestPostListResponse = (responseBody: GetLatestPostListResponseDto | ResponseDto | null) => {

            if (!responseBody) return;
            const { code } = responseBody;

            if (code === 'DE') {
                showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
                return;
            }

            if (code !== 'SU') return;

            const { latestList } = responseBody as GetLatestPostListResponseDto;
            setTotalList(latestList);
        }

        //function: getPopularSearchListResponse 처리 함수
        const getPopularSearchListResponse = (responseBody: GetPopularSearchListResponseDto | ResponseDto | null) => {

            if (!responseBody) return;
            const { code } = responseBody;

            if (code === 'DE') {
                showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
                return;
            }

            if (code !== 'SU') return;

            const { popularSearchList } = responseBody as GetPopularSearchListResponseDto;
            setPopularWordList(popularSearchList);
        }

        //effect: 첫 마운트 시 실행될 함수
        useEffect(() => {
            getLatestPostListRequest().then(getLatestPostListResponse)
            getPopularSearchListRequest().then(getPopularSearchListResponse)
        }, []);

        //render: 메인 화면 하단 컴포넌트 렌더링
        return (
            <div id='main-bottom-wrapper'>
                <div className='main-bottom-container'>
                    <div className='main-bottom-title'>{'최신 게시물'}</div>
                    <div className='main-bottom-contents-box'>
                        <div className='main-bottom-current-contents'>
                            {viewList.map(postListItem => <PostItem postListItem={postListItem} />)}
                        </div>
                        <div className='main-bottom-popular-box'>
                            <div className='main-bottom-popular-card'>
                                <div className='main-bottom-popular-card-box'>
                                    <div className='main-bottom-popular-card-title'>{'인기 검색어'}</div>
                                    <div className='main-bottom-popular-card-contents'>
                                        {popularWordList.map(word => <div className='word-badge' onClick={() => onPopularWordClickHandler(word)}>{word}</div>)}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className='main-bottom-pagination-box'>
                        {

                        }
                        <Pagination
                            currentPage={currentPage}
                            currentSection={currentSection}
                            setCurrentPage={setCurrentPage}
                            setCurrentSection={setCurrentSection}
                            viewPageList={viewPageList}
                            totalSection={totalSection}
                        />
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
