import { createActions, handleAction, handleActions } from "redux-actions";

const initialState = {
    documentList: [],
    saveDocument: null,
}

export const GET_DOCUMENT_LIST = 'document/GET_DOCUMENT_LIST';
export const POST_DOCUMENT = 'document/POST_DOCUMENT';

const actions = createActions({
    [GET_DOCUMENT_LIST]: () => {},
    [POST_DOCUMENT]: () => {},
});

const documentReducer = handleActions({
    [GET_DOCUMENT_LIST]: (state, {payload}) =>({
        ...state,
        documentList: payload
    }),
    [POST_DOCUMENT]: (state, {payload}) =>({
        ...state,
        saveDocument: payload
    }),
}, initialState);

export default documentReducer;