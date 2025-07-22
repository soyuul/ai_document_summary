import axios from 'axios';
import React, { useState } from 'react'
import { useDispatch } from 'react-redux';
import { callSaveSummary } from '../../../apis/SummaryAPICalls';

function SummaryInput() {
  console.log("키워드 입력");

  const dispatch = useDispatch();
  
  // 키워드용 state => 기본값 ""(빈문자열)
  const [keyword, setKeyword] = useState("");
  
  // 키워드 변경을 알려주는 이벤트 함수
  const handleChangeKeyword = e =>{
    // 현재 상태의 값을 setKeyword에 할당
    setKeyword(e.target.value);
    console.log("입력된 키워드 : ", setKeyword);
  }

   
  return (
    <form>
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

export default SummaryInput;
