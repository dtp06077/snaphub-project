import { useEffect, useState } from "react";

const usePagination = <T>(countPerPage: number) => {
    //state: 전체 객체 리스트 상태
    const [totalList, setTotalList] = useState<T[]>([]);
    //state: 보여줄 객체 리스트 상태
    const [viewList, setViewList] = useState<T[]>([]);
    //state: 현재 페이지 번호 상태
    const [currentPage, setCurrentPage] = useState<number>(1);

    //state: 전체 페이지 번호 리스트 상태
    const [totalPageList, setTotalPageList] = useState<number[]>([1]);
    //state: 보여줄 페이지 번호 리스트 상태
    const [viewPageList, setViewPageList] = useState<number[]>([1]);
    //state: 현재 섹션 상태
    const [currentSection, setCurrentSection] = useState<number>(1);

    //state: 전체 섹션 상태
    const [totalSection, setTotalSection] = useState<number>(1);

    //function: 보여줄 객체 리스트 추출 함수
    const setView = () => {
        const START_INDEX = countPerPage * (currentPage - 1);
        const END_INDEX = Math.min(totalList.length, countPerPage * currentPage);
        const viewList = totalList.slice(START_INDEX, END_INDEX);
        setViewList(viewList);
    }

    //function: 보여줄 페이지 리스트 추출 함수
    const setViewPage = () => {
        const START_INDEX = 10 * (currentSection - 1);
        const END_INDEX = Math.min(totalPageList.length, 10 * currentSection);
        const viewPageList = totalPageList.slice(START_INDEX, END_INDEX);

        setViewPageList(viewPageList);
    }

    // effect: totalList가 변경될 때마다 실행할 작업
    useEffect(() => {
        const totalPage = Math.ceil(totalList.length / countPerPage);
        const totalPageList: number[] = Array.from({ length: totalPage }, (_, i) => i + 1);
        setTotalPageList(totalPageList);

        const totalSection = Math.ceil(totalPage / 10);
        setTotalSection(totalSection);

        setCurrentPage(1);
        setCurrentSection(1);

        setView();
        setViewPage();
    }, [totalList, countPerPage]);

    //effect: currentPage가 변경될 때마다 실행할 작업
    useEffect(setView, [currentPage])

    //effect: currentSection이 변경될 때마다 실행할 작업
    useEffect(setViewPage, [currentSection])

    return {
        currentPage,
        setCurrentPage,
        currentSection,
        setCurrentSection,
        viewList,
        viewPageList,
        totalSection,
        setTotalList
    }
};

export default usePagination;