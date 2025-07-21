import { createActions, handleAction, handleActions } from "redux-actions";

const initialState = {
    saveDocument: null
}

export const POST_DOCUMENT = 'document/POST_DOCUMENT';

const actions = createActions({
    [POST_DOCUMENT]: () => {},
});

const documentReducer = handleActions({

    [POST_DOCUMENT]: (state, {payload}) =>({
        ...state,
        saveDocument: payload
    })
}, initialState);

export default documentReducer;