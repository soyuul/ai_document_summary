import React, { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux';
import { callSaveDocumentApi } from '../../../apis/DocumentAPICalls';
import { useNavigate } from 'react-router-dom';
import docStyle from '../../../styles/DocUploadForm.module.css';
import btnStyle from '../../../styles/Global/Button.module.css';

function DocumentUploadForm() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  // 파일 업로드용 state => 기본값 null
  const [file, setFile] = useState(null);
  const [fileName, setFileName] = useState("첨부파일");

  // 파일의 변경을 알려주는 이벤트 함수
  const handleChangeFile = e =>{
    console.log("파일 선택? : ", e.target.files[0]);
    const selectedFile = e.target.files[0];
    if(selectedFile){
      setFile(selectedFile);
      setFileName(selectedFile.name);
    }
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

    const onClickDocumentListPageHandler = () =>{
      navigate(`/document/list`);
    }

  return (
    <div className={docStyle.docBox}>
      <form
      className={docStyle.formBox}
      onSubmit={handleSubmit}>
        <div className={docStyle.fileBox}>
          <input className={docStyle.uploadName} value={fileName} placeholder="첨부파일" disabled/>
          <label for="file">파일선택</label> 
          <input type="file" id="file"
          onChange={handleChangeFile}/>
        </div>
      </form>

      <div className={docStyle.btnBox}>
        <button
        className={btnStyle.btnNonColor}
        type="submit" onClick={refreshPage}>파일 저장</button>
        <button 
        className={btnStyle.btnColor}
        type="submit" onClick={onClickDocumentListPageHandler}>문서 저장소</button>
      </div>
    </div>

    

  )


}

export default DocumentUploadForm;