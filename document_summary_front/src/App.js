import { BrowserRouter, Route, Routes } from "react-router-dom";
import Layout from "./layouts/Layout";
import Main from "./pages/Main";
import DocumentList from "./pages/DocumentList";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout/>}>
          <Route index element={<Main/>}/>
          <Route path="document/list" element={<DocumentList/>}/>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
