import React from 'react';
import {NavLink} from 'react-router-dom';
import  {getRoute} from '../../const/Routes';

const Header = () => {
  return (
    <div className="header">
      <strong><em>
            <NavLink 
              exact 
              activeStyle={{
                fontWeight: "bold",
                color: "greenyellow"
              }}
              to={getRoute('home').path}>Kombat Sim
            </NavLink> 
      </em></strong>
    </div>
  );
};

export default Header;