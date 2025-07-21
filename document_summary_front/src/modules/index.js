import { combineReducers } from "redux";
import summaryReducer from "./SummaryModule";
import documentReducer from "./DocumentModule";

// combineReducers : 여러 개의 reducer를 하나의 루트 리듀서로 결함 시키는 함수
const rootReducer = combineReducers({
    documentReducer,
    summaryReducer,
})

export default rootReducer;