import { createActions, handleActions } from "redux-actions";

const initialState = {
    summaryList: [],
    download: null,
    saveSummary: null,
}

export const GET_SUMMARY_LIST = 'summary/GET_SUMMARY_LIST';
export const GET_DOWNLOAD_SUMMARY = 'summary/GET_DOWNLOAD_SUMMARY';
export const POST_SUMMARY = 'summary/POST_SUMMARY';

const actions = createActions({
    [GET_SUMMARY_LIST]: () => {},
    [GET_DOWNLOAD_SUMMARY]: () => {},
    [POST_SUMMARY]: () => {},
});

const summaryReducer = handleActions({
    
    [GET_SUMMARY_LIST]: (state, {payload}) =>({
        ...state,
        summaryList: payload
    }),
    [GET_DOWNLOAD_SUMMARY]: (state, {payload}) =>({
        ...state,
        download: payload
    }),

    // POST_SUMMARY 액션이 발생했을 때
    // state : 현재 상태, payload: 전달된 데이터
    // ...state : 기존에 있는 상태값 복사
    // saveSummary 필드만 payload로 업데이트
    [POST_SUMMARY]: (state, {payload}) =>({
        ...state,
        saveSummary: payload
    }),
}, initialState);

export default summaryReducer;