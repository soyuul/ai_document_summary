import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { callDocumentListApi } from '../apis/DocumentAPICalls';

function DocumentList() {
  console.log("문서 전체 리스트 페이지");
  
  const dispatch = useDispatch();
  const documentList = useSelector(state => state.documentReducer.documentList);
  console.log("documentList data : ", documentList);
  
  const [checkedId, setCheckedId] = useState([]);

  useEffect(() =>{
    dispatch(callDocumentListApi());
  }, [dispatch]);

  const handleCheckboxChange = (documentId) =>{
    setCheckedId(documentId);
  };

  console.log("문서 id: ", checkedId);


  // 스타일
  const thStyle = {
    padding: "12px",
    borderBottom: "2px solid #ccc",
    textAlign: "left",
  };

  const tdStyle = {
    padding: "10px",
    textAlign: "left",
  };
  

  return (
    <div style={{ padding: "20px" }}>
      <h2>문서 목록</h2>
      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr style={{ backgroundColor: "#f5f5f5" }}>
            <th style={thStyle}>문서 이름</th>
            <th style={thStyle}>경로</th>
            <th style={thStyle}>업로드 날짜</th>
            <th style={thStyle}>선택</th>
          </tr>
        </thead>
        <tbody>
          {documentList && documentList.length > 0 ? (
            documentList.map((doc) => (
              <tr key={doc.documentId} style={{ borderBottom: "1px solid #ddd" }}>
                <td style={tdStyle}>{doc.documentTitle}</td>
                <td style={tdStyle}>{doc.filePath}</td>
                <td style={tdStyle}>
                  {Array.isArray(doc.uploadedAt)
                    ? doc.uploadedAt.join("-")
                    : doc.uploadedAt}
                </td>
                <td style={tdStyle}>
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
    </div>
  );




}

export default DocumentList;
