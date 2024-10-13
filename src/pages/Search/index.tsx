import React, { useContext, useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { PostListItem } from '../../types/interface';
import PostItem from '../../components/PostItem';
import { SEARCH_PATH } from '../../constants';
import Pagination from '../../components/Pagination';
import './style.css';
import { getSearchPostListRequest } from '../../apis';
import { GetSearchPostListResponseDto } from '../../apis/response/post';
import { ResponseDto } from '../../apis/response';
import { EventModalContext } from '../../contexts/EventModalProvider';
import { usePagination } from '../../hooks';

//component: 검색 화면 컴포넌트
export default function Search() {

    //state: searchWord path variable 상태
    const { searchWord } = useParams();
    //state: 이전 검색어 상태
    const [ preSearchWord, setPreSearchWord ] = useState<string | null>(null);
    //state: 검색 게시물 리스트 상태
    const [searchPostList, setSearchPostList] = useState<PostListItem[]>([]);
    //state: 검색 게시물 갯수 상태
    const [count, setCount] = useState<number>(0);
    //state: 관련 검색어 리스트 상태
    const [relationList, setRelationList] = useState<string[]>([]);

    //state: 페이지네이션 관련 상태 
    const { currentPage, setCurrentPage, currentSection, setCurrentSection, viewList,
        viewPageList, totalSection, setTotalList } = usePagination<PostListItem>(7);

    //context: 이벤트 모달 창
    const eventContext = useContext(EventModalContext);

    //function: 네비게이트 함수
    const navigate = useNavigate();

    // eventContext가 undefined일 경우
    if (!eventContext) {
        throw new Error("이벤트 모달 창이 존재하지 않습니다.");
    }

    const { showModal } = eventContext;

    //function: getSearchPostListResponse 처리 함수
    const getSearchPostListResponse = (responseBody: GetSearchPostListResponseDto | ResponseDto | null) => {
        if (!responseBody) return;
            const { code } = responseBody;

            if (code === 'DE') {
                showModal('Database Error', '데이터베이스에서 오류가 발생했습니다.');
                return;
            }
            if (code !== 'SU') return;
            
            if(!searchWord) return;
            const { searchList } = responseBody as GetSearchPostListResponseDto;
            setTotalList(searchList);
            setCount(searchList.length);
            setPreSearchWord(searchWord);
    }

    //event handler: 연관 검색어 클릭 이벤트 처리
    const onRelationWordClickHandler = (word: string) => {
        navigate(SEARCH_PATH(word));
    }

    //effect: 첫 마운트 시 실행될 함수
    useEffect(() => {
        if (!searchWord) return;
        getSearchPostListRequest(searchWord, preSearchWord).then(getSearchPostListResponse);
    }, [searchWord]);

    //render: 검색 화면 컴포넌트 렌더링
    if (!searchWord) return (<></>)

    return (
        <div id='search-wrapper'>
            <div className='search-container'>
                <div className='search-title-box'>
                    <div className='search-title'><span className='search-title-emphasis'>{searchWord}</span>{'에 대한 검색결과 입니다.'}</div>
                    <div className='search-count'>{count}</div>
                </div>
                <div className='search-contents-box'>
                    {count === 0 ?
                        <div className='search-contents-nothing'>{"검색결과가 없습니다."}</div> :
                        <div className='search-contents'>{searchPostList.map(postListItem => <PostItem postListItem={postListItem} />)}</div>
                    }
                    <div className='search-relation-box'>
                        <div className='search-relation-card'>
                            <div className='search-relation-card-container'>
                                <div className='search-relation-card-title'>{"관련 검색어"}</div>
                                {relationList.length === 0 ?
                                    <div className='search-relation-card-contents-nothing'>{"관련 검색어가 없습니다."}</div> :
                                    <div className='search-relation-card-contents'>
                                        {relationList.map(word => <div className='word-badge' onClick={() => onRelationWordClickHandler(word)}>{word}</div>)}
                                    </div>
                                }
                            </div>
                        </div>
                    </div>
                </div>
                <div className='search-pagination-box'>
                    {count !== 0 && {/* <Pagination/> */} }
                </div>
            </div>
        </div>
    )
}
