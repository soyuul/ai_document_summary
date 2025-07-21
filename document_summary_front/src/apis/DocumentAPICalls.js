import {GET_DOCUMENT_LIST, POST_DOCUMENT} from "../modules/DocumentModule";

const prefix = `http://${process.env.REACT_APP_RESTAPI_IP}:8080`;


// 업로드한 파일 리스트 전체 조회
export const callDocumentListApi = () =>{
    let requestURL = `${prefix}/document/list`;
    console.log('[callDocumentListApi] requestURL : ', requestURL);
    
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
                dispatch({type: GET_DOCUMENT_LIST, payload: result.data});
            }
            console.log("dispatch? : ", dispatch);
        }catch (e){
            console.error('[callDocumentListApi] ERROR: ', e);
            throw e;
        }
    };
}


// 파일 업로드
export const callSaveDocumentApi = formData =>{
    let requestURL = `${prefix}/document`;
    console.log('[callDaveDocumentApi] requestURL : ', requestURL);

    return async (dispatch, getState) =>{
        try{
            const result = await fetch(requestURL,{
                method: 'POST',
                body: formData,
            }).then((response) => response.json());

            if(result.state === 200){
                dispatch({type: POST_DOCUMENT, payload: result.data});
            }
        }catch (e){
            console.error('[callSaveDocumentApi] ERROR: ', e);
            throw e;
        }
    };
};
