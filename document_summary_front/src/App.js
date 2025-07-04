import { BrowserRouter, Route, Routes } from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<div>여기는 메인입니다.</div> }/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
