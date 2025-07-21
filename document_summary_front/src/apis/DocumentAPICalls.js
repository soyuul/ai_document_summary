import {POST_DOCUMENT} from "../modules/DocumentModule";

const prefix = `http://${process.env.REACT_APP_RESTAPI_IP}:8080`;

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
