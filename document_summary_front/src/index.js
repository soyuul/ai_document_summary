import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { Provider } from 'react-redux';
import store from './Store';

// Provider : React 앱에 store를 주입해주는 컴포넌트
// => Redux 상태(store)를 전 컴포넌트에 연결시켜준다
// => store를 React 전체 앱에 공급하는 React 컴포넌트
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <Provider store={store}>
    <App />
  </Provider>
);

