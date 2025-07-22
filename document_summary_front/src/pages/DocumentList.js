import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { callDocumentListApi } from '../apis/DocumentAPICalls';
import SummaryInput from '../components/common/summary/SummaryInput';
import listStyle from '../styles/common/TableListStyle.module.css';
import pagiNation from '../styles/common/Pagination.module.css';
import { useNavigate } from 'react-router-dom';

function DocumentList() {
  console.log("문서 전체 리스트 페이지");
  
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const documentList = useSelector(state => state.documentReducer.documentList);
  console.log("documentList data : ", documentList);
  
  // 체크리스트 상태
  const [checkedId, setCheckedId] = useState([]);

  // 페이지네이션 상태
  const [doc, setDoc] = useState([]);

  const [currentPage, setCurrentPage] = useState(1);
  const postsPerPage = 10;

  // 현재 페이지에 표시할 게시글 계산
  const indexOfLastList = currentPage * postsPerPage;
  const indexOfFirstList = indexOfLastList - postsPerPage;
  const currentList = doc.slice(indexOfFirstList, indexOfLastList);

  // 페이지 번호 버튼 생성
  const totalPages = Math.ceil(doc.length / postsPerPage);
  const pageNumbers = Array.from({ length: totalPages }, (_, i) => i + 1);


  useEffect(() =>{
    dispatch(callDocumentListApi());
  }, [dispatch]);

  useEffect(()=>{
      if(Array.isArray(documentList)){
        setDoc(documentList)
      }
  },[documentList]);


  const handleCheckboxChange = (documentId) =>{
    setCheckedId(documentId);
  };
  console.log("문서 id: ", checkedId);

  const onClickSummaryListPageHandler = () =>{
    navigate(`/summary/list`);
  }


  return (
    <div className={listStyle.listBox}>
      <div className={listStyle.titleBox}>
        <h2>문서 목록</h2>
        <div>
          <SummaryInput/>
          <button type="submit" onClick={onClickSummaryListPageHandler}>요약된 문서 보기</button>
        </div>
      </div>
      <table className={listStyle.tableBox}>
        <thead>
          <tr>
            <th>문서 이름</th>
            <th>경로</th>
            <th>업로드 날짜</th>
            <th>선택</th>
          </tr>
        </thead>
        <tbody>
        {currentList && currentList.length > 0 ? (
          currentList.map((doc) => (
            <tr key={doc.documentId}>
              <td>{doc.documentTitle}</td>
              <td>{doc.filePath}</td>
              <td>
                {Array.isArray(doc.uploadedAt)
                  ? doc.uploadedAt.join("-")
                  : doc.uploadedAt}
              </td>
              <td>
                <input
                  type="checkbox"
                  checked={checkedId === doc.documentId}
                  onChange={() => handleCheckboxChange(doc.documentId)}
                />
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="4" style={{ textAlign: "center", padding: "20px" }}>
              문서가 없습니다.
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

export default DocumentList;
