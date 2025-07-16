import { applyMiddleware, createStore } from "redux";
import { thunk } from "redux-thunk";
import rootReducer from "./modules";

// rootReducer : 여러 개의 reducer를 하나로 합친 것
// applyMiddleware(thunk) : 비동기 처리를 위한 thunk 연결
const store = createStore(
    rootReducer, 
    applyMiddleware(thunk));

export default store;
