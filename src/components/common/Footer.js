import React from 'react';
import {Link} from 'react-router-dom';
import  {getRoute} from '../../const/Routes';

const Footer = () => {
  return (
    <div className="footer">
      <em><Link to={getRoute('home').path}>Kombat Sim</Link></em> is a simple experiment to practice and validate my 
      limited knowledge on Design Patterns. 
    </div>
  );
};

export default Footer;