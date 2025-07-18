import axios from 'axios';
import React, { useState } from 'react'
import { useDispatch } from 'react-redux';
import { callSaveSummary } from '../../apis/SummaryAPICalls';

function SummaryUploadForm() {
    const dispatch = useDispatch();

    // 파일 업로드용 state => 기본값 null
    const [file, setFile] = useState(null);
    // 키워드용 state => 기본값 ""(빈문자열)
    const [keyword, setKeyword] = useState("");

    // 파일의 변경을 알려주는 이벤트 함수
    const handleChangeFile = e =>{
      console.log("파일 선택됨: ", e.target.files[0]);
        // file의 현재 상태의 0번째를 setFile에 할당
        setFile(e.target.files[0]);
    }

    // 키워드 변경을 알려주는 이벤트 함수
    const handleChangeKeyword = e =>{
        // 현재 상태의 값을 setKeyword에 할당
        setKeyword(e.target.value);
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
        formData.append("keyword", keyword);

        dispatch(callSaveSummary(formData));
    }

  return (
    <form onSubmit={handleSubmit}>
      <input type="file" name="file" onChange={handleChangeFile} />
      <input
        type="text"
        value={keyword}
        onChange={handleChangeKeyword}
        placeholder="키워드 입력"
      />
      <button type="submit">요약 저장</button>
    </form>
  );
};

export default SummaryUploadForm;
