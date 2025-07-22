import { height } from '@fortawesome/free-solid-svg-icons/fa0';
import React from 'react'
import { useNavigate } from 'react-router-dom';
import headerStyle from '../../styles/Global/Header.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHouse } from '@fortawesome/free-solid-svg-icons';

function Header() {

    const navigate = useNavigate();

    const onClickMainPageHandler = () =>{
        navigate(`/`);
    }


  return (
    <div className={headerStyle.headerBox}>
      <div 
      className={headerStyle.logoBox}
      onClick={onClickMainPageHandler}>
        <FontAwesomeIcon icon={faHouse} />
      </div>
    </div>
  )
}

export default Header;
