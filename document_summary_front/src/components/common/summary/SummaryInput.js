import { useState } from 'react';
import { useDispatch } from 'react-redux';
import sumInputStyle from '../../../styles/SummaryInput.module.css';
import btnStyle from '../../../styles/Global/Button.module.css';

function SummaryInput() {

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
    <form className={sumInputStyle.formBox}>
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
