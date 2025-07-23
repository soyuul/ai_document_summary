import { useState } from 'react';
import { useDispatch } from 'react-redux';
import sumInputStyle from '../../../styles/SummaryInput.module.css';
import btnStyle from '../../../styles/Global/Button.module.css';
import { useNavigate, useParams } from 'react-router-dom';
import { callSaveSummary, callSaveSummaryApi } from '../../../apis/SummaryAPICalls';

function SummaryInput({ documentId }) {
  console.log("List에서 넘겨받은 documentId : ", documentId);

  const dispatch = useDispatch();
  const navigate = useNavigate();

  // 키워드용 state => 기본값 ""(빈문자열)
  const [keyword, setKeyword] = useState("");
  
  // 키워드 변경을 알려주는 이벤트 함수
  const handleChangeKeyword = e =>{
    // 현재 상태의 값을 setKeyword에 할당
    setKeyword(e.target.value);
  }
  console.log("입력된 키워드 : ", keyword);


  const handleSubmit = e =>{
    
      e.preventDefault();

      if(!documentId){
          console.log("documentId??? ", documentId);
          alert("요약할 파일을 선택해주세요.");
          return;
      }

      const formData = new FormData();
      formData.append("documentId", documentId);
      formData.append("keyword", keyword);

      dispatch(callSaveSummaryApi(formData));
      alert("파일이 요약되었습니다.");
      navigate(0);
  }
   
  return (
    <form 
    className={sumInputStyle.formBox}
    onSubmit={handleSubmit}>
      <input
        type="text"
        value={keyword}
        onChange={handleChangeKeyword}
        placeholder="키워드 입력"
      />
      <button
      className={btnStyle.btnInput}
      type="submit">요약 저장</button>
    </form>
  );
};

export default SummaryInput;
