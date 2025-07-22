import React from 'react'
import { Outlet } from 'react-router-dom'
import Header from '../components/common/Header';

function Layout() {
  return (
    <>
      <Header/>
      <div>
        <Outlet/>
      </div>
    </>
  )
}

export default Layout;
