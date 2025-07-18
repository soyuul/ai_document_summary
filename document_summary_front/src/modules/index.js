import { combineReducers } from "redux";
import summaryReducer from "./SummaryModule";

// combineReducers : 여러 개의 reducer를 하나의 루트 리듀서로 결함 시키는 함수
const rootReducer = combineReducers({
    summaryReducer,
})

export default rootReducer;