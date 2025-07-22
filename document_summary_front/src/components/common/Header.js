import { height } from '@fortawesome/free-solid-svg-icons/fa0';
import React from 'react'
import { useNavigate } from 'react-router-dom';

function Header() {

    const navigate = useNavigate();

    const onClickMainPageHandler = () =>{
        navigate(`/`);
    }

    // style
    const headerStyle = {
        width: "100%",
        height: "70px",
        backgroundColor: "red"
    };

  return (
    <div style={headerStyle}>
      
    </div>
  )
}

export default Header;
