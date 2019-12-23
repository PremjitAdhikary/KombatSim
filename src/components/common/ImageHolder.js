import React, { useState } from 'react';
import PropTypes from 'prop-types';
import  {getImage} from '../../const/Images';
import './ImageHolder.css';

const ImageHolder = props => {
  let img = getImage(props.imgId);
  const [largeDivDisabled, setLargeDivDisabled] = useState(true);
  
  return (
    <div className="center">
      <div className="regular" onClick={() => setLargeDivDisabled(false)}>
        <img 
          className="regular-content" 
          src={process.env.PUBLIC_URL + img.path} 
          alt={img.text} 
        />
        <div>{img.text}</div>
      </div>
      <div className="large" 
        style={{display: largeDivDisabled ? 'none' : 'block' }} >
        <span className="close" onClick={() => setLargeDivDisabled(true)}>&times;</span>
        <img className={largeContentClass(props.imgSize)} alt={img.text} 
          src={process.env.PUBLIC_URL + img.path} />
        <div className="caption">{img.text}</div>
      </div>
    </div>
  );
};

ImageHolder.propTypes = {
  imgId: PropTypes.string,
  imgSize: PropTypes.string
};

let largeContentClass = (full) => {
  if (!full)
    return 'large-content';
  switch(full) {
    case 'medium':
      return 'large-content-med';
    case 'large':
      return 'large-content-full';
    case 'small':
    default:
      return 'large-content';
  }
};

export default ImageHolder;