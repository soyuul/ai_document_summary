import { POST_SUMMARY } from "../modules/SummaryModule";

const prefix = `http://${process.env.REACT_APP_RESTAPI_IP}:8080`

export const callSaveSummary = formData =>{
    let requestURL = `${prefix}/summary/insert`;
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