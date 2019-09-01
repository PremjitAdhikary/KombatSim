import React from 'react';
import {NavLink} from 'react-router-dom';
import PropTypes from 'prop-types';
import  {getRoute} from '../../const/Routes';
import './Navigators.css';

const Navigators = (props) => {
  return (
    <div className="navs">
      <span className="left">
        {getContent(props.prev, 'Previous')}
      </span>
      <span className="right">
        {getContent(props.next, 'Next')}
      </span>
    </div>
  );
};

Navigators.propTypes = {
  prev: PropTypes.string,
  next: PropTypes.string
};

let getContent = (pathKey, text) => {
  if (pathKey) {
    return <NavLink className="btn" to={getRoute(pathKey).path}>{text}</NavLink>;
  } else {
    return <span className="btn-disabled">{text}</span>;
  }
}

export default Navigators;