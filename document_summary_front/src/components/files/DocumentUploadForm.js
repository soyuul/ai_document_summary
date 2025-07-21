import React, { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux';
import { callSaveDocumentApi } from '../../apis/DocumentAPICalls';
import { useNavigate } from 'react-router-dom';

function DocumentUploadForm() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  // 파일 업로드용 state => 기본값 null
  const [file, setFile] = useState(null);

  // 파일의 변경을 알려주는 이벤트 함수
  const handleChangeFile = e =>{
    console.log("파일 선택? : ", e.target.files[0]);
    setFile(e.target.files[0]);
  }

  // 페이지를 이동시키는 이벤트 함수
    const handleSubmit = e =>{
        // 페이지 이동 중단
        e.preventDefault();

        // 선택한 파일이 없는 경우 파일을 선택해달라는 안내창
        if(!file){
            console.log("file이 있나요?? ", file);
            alert("파일을 선택해주세요.");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        dispatch(callSaveDocumentApi(formData));

        alert("파일이 등록되었습니다.");

    }

    const refreshPage = () =>{
      navigate(0);
    }

  return (
    <form onSubmit={handleSubmit}>
      <input type="file" name="file" onChange={handleChangeFile}/>
      <button type="submit" onClick={refreshPage}>파일 저장</button>
    </form>
  )
}

export default DocumentUploadForm;