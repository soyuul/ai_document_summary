import { GET_SUMMARY_LIST, POST_SUMMARY } from "../modules/SummaryModule";

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

// 키워드로 요약 or 문서 전체 요약
export const callSaveSummary = formData =>{
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