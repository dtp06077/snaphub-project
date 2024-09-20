import React from 'react'

//component: 게시물 상세 화면 컴포넌트
export default function PostDetail() {

    //component: 게시물 상세 상단 컴포넌트
    const PostDetailTop = () => {

        //render: 게시물 상세 상단 컴포넌트 렌더링
        return(
            <div id = 'post-detail-top'>
                <div className='post-detail-top-header'>
                    <div className='post-detail-title'></div>
                    <div className='post-detail-top-sub-box'>
                        <div className='post-detail-write-info-box'>
                            <div className='post-detail-writer-profile-image'></div>
                            <div className='post-detail-writer-nickname'></div>
                            <div className='post-detail-info-divider'></div>
                            <div className='post-detail-write-date'></div>
                        </div>
                        <div className='icon-button'>
                            <div className='icon more-icon'></div>
                        </div>
                        <div className='post-detail-more-box'>
                            <div className='post-detail-update-button'></div>
                            <div className='divider'></div>
                            <div className='post-detail-delete-button'></div>
                        </div>
                    </div>
                </div>
                <div className='divider'></div>
                <div className='post-detail-top-main'>
                    <div className='post-detail-main-text'></div>
                    <div className='post-detail-main-image'></div>
                </div>
            </div>
        );
    };

    //component: 게시물 상세 하단 컴포넌트
    const PostDetailBottom = () => {

        //render: 게시물 상세 하단 컴포넌트 렌더링
        return(
            <div></div>
        );
    };
    //render: 게시물 상세 화면 컴포넌트 렌더링
    return (
        <div id = 'post-detail-wrapper'>
            <div className='post-detail-container'>
                <PostDetailTop/>
                <PostDetailBottom/>
            </div>
        </div>
    )
}
