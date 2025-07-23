import { faFileArrowDown } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { callDownloadSummaryApi } from '../../../apis/SummaryAPICalls';

function SummaryDownload({filename}) {
    console.log("filename : ", filename);

    const dispatch = useDispatch();
    const summaryDownload = useSelector(state => state.summaryReducer.download);
    console.log("summaryDownload ?? ", summaryDownload);

    const handleDownload = () =>{
        if(!filename){
            alert("파일명이 없습니다.");
        }
        const cleanFilename = filename.startsWith('/') ? filename.substring(1) : filename;
        dispatch(callDownloadSummaryApi(cleanFilename));
    };



  return (
    <div onClick={handleDownload} style={{cursor: 'pointer'}}>
      <FontAwesomeIcon icon={faFileArrowDown} style={{color: '#676c84ff'}}/>
    </div>
  )
}

export default SummaryDownload;