import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { callSummaryListApi } from '../apis/SummaryAPICalls';
import listStyle from '../styles/common/TableListStyle.module.css';
import pagiNation from '../styles/common/Pagination.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFileArrowDown } from '@fortawesome/free-solid-svg-icons';

function SummaryList() {
  console.log("요약된 문서 전체 리스트 페이지");

  const dispatch = useDispatch();
  const summaryList = useSelector(state => state.summaryReducer.summaryList);
  console.log("summaryList data : ", summaryList);

  // 페이지네이션 상태
  const [sum, setSum] = useState([]);

  const [currentPage, setCurrentPage] = useState(1);
  const postsPerPage = 4;
    
  // 현재 페이지에 표시할 게시글 계산
  const indexOfLastList = currentPage * postsPerPage;
  const indexOfFirstList = indexOfLastList - postsPerPage;
  const currentList = sum.slice(indexOfFirstList, indexOfLastList);

  // 페이지 번호 버튼 생성
  const totalPages = Math.ceil(sum.length / postsPerPage);
  const pageNumbers = Array.from({ length: totalPages }, (_, i) => i + 1);


  useEffect(() =>{
    dispatch(callSummaryListApi());
  }, [dispatch]);

  useEffect(()=>{
      if(Array.isArray(summaryList)){
        setSum(summaryList)
      }
  },[summaryList]);  


  return (
    <div className={listStyle.listBox}>
      <div className={listStyle.titleBox}>
        <h2>요약된 문서 목록</h2>
      </div>
      <table className={listStyle.summaryBox}>
        <thead>
          <tr>
            <th>키워드</th>
            <th>요약 내용</th>
            <th>요약 날짜</th>
            <th>다운로드</th>
          </tr>
        </thead>
        <tbody>
        {currentList && currentList.length > 0 ? (
          currentList.map((sum) => (
            <tr key={sum.summaryId}>
              <td>{sum.keyword}</td>
              <div className={listStyle.lineClamp}>
                <td>{sum.summaryContent}</td>
              </div>
              <td>
                {Array.isArray(sum.summaryCreatedAt)
                  ? sum.summaryCreatedAt.join("-")
                  : sum.summaryCreatedAt}
              </td>
              <td>
               <FontAwesomeIcon icon={faFileArrowDown} />
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="4" style={{ textAlign: "center", padding: "20px" }}>
              요약된 문서가 없습니다.
            </td>
          </tr>
        )}
      </tbody>
      </table>

      <div className={pagiNation.pagination}>
        {pageNumbers.map((number) => (
          <button
            key={number}
            onClick={() => setCurrentPage(number)}
            className={`${pagiNation.pageButton} ${currentPage === number ? pagiNation.pageButtonActive : ''}`}
          >
            {number}
          </button>
        ))}
      </div>
    </div>
  );
}

export default SummaryList;
