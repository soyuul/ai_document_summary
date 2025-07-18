import { createActions, handleActions } from "redux-actions";

const initialState = {
    saveSummary: null
}

export const POST_SUMMARY = 'summary/POST_SUMMARY';

const actions = createActions({
    [POST_SUMMARY]: () => {},
});

const summaryReducer = handleActions({
    // POST_SUMMARY 액션이 발생했을 때
    // state : 현재 상태, payload: 전달된 데이터
    // ...state : 기존에 있는 상태값 복사
    // saveSummary 필드만 payload로 업데이트
    [POST_SUMMARY]: (state, {payload}) =>({
        ...state,
        saveSummary: payload
    })
}, initialState);

export default summaryReducer;