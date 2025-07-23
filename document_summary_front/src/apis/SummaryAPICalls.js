import { GET_DOWNLOAD_SUMMARY, GET_SUMMARY_LIST, POST_SUMMARY } from "../modules/SummaryModule";

const prefix = `http://${process.env.REACT_APP_RESTAPI_IP}:8080`;

// 파일 요약 리스트 전체 조회
export const callSummaryListApi = () =>{
    let requestURL = `${prefix}/summary/list`;
    console.log('[callSummaryListApi] requestURL : ', requestURL);
    
    return async (dispatch, getState) =>{
        try{
            const result = await fetch(requestURL,{
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    Accept: '*/*'
                }
            }).then((response) => response.json());
            
            console.log("result?? ", result);
            
            if(result.status === 200){
                dispatch({type: GET_SUMMARY_LIST, payload: result.data});
            }
            console.log("dispatch? : ", dispatch);
        }catch (e){
            console.error('[callSummaryListApi] ERROR: ', e);
            throw e;
        }
    };
}


// 요약된 파일 브라우저에서 다운로드
// response.json() 으로 파싱하면 파일 데이터가 망가져서 다운로드가 안된다
// 따라서 blob로 받아서 실제 브라우저에서 파일 다운이 될수 있도록
export const callDownloadSummaryApi = (filename) => {
  let requestURL = `${prefix}/summary/download?filename=${encodeURIComponent(filename)}`;
  console.log('[callDownloadSummaryApi] requestURL : ', requestURL);

  return async () => {
    try {
      const response = await fetch(requestURL, {
        method: 'GET',
      });

      const blob = await response.blob();

      const downloadUrl = window.URL.createObjectURL(blob);
      
      const a = document.createElement('a');   // ✅ 먼저 선언!
      a.href = downloadUrl;
      a.download = filename;
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(downloadUrl);
    } catch (e) {
      console.error('[callDownloadSummaryApi] ERROR: ', e);
      throw e;
    }
  };
};



// 키워드로 요약 or 문서 전체 요약
export const callSaveSummaryApi = formData =>{
    let requestURL = `${prefix}/summary`;
    console.log('[callSaveSummaryAPI] requestURL : ', requestURL);
    
    return async (dispatch, getState) =>{
        try{
            const result = await fetch(requestURL,{
                method: 'POST',
                body: formData,
            }).then((response) => response.json());
            
            if(result.state === 200){
                dispatch({type: POST_SUMMARY, payload: result.data});
            }
        }catch (e){
            console.error('[callSaveSummaryAPI] ERROR: ', e);
            throw e;
        }
    };
};

