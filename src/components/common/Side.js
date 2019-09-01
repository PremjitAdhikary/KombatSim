import React, { Component } from 'react';
import {NavLink} from 'react-router-dom';
import  {allRoutes} from '../../const/Routes';
import './Side.css';

class Side extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedOption: ''
    };
  }

  handleOptionChange = changeEvent => {
    this.setState(
      {selectedOption: changeEvent.target.value}
    );
  }

  navigators(id, label, routes) {
    return(
      <div>
        <input 
          type="radio" 
          name="item" 
          id={id} 
          value={id} 
          checked={this.state.selectedOption === id}
          onChange={this.handleOptionChange} 
          hidden />
        <label htmlFor={id}>{label}</label>
          {this.state.selectedOption === id  && 
            <div className="subLinks" >
              {routes.map( (route) => 
                <div key={route.id}>&ensp;
                  <NavLink 
                    activeStyle={{
                      fontWeight: "bold",
                      color: "greenyellow"
                    }}
                    to={route.path}>{route.name}
                  </NavLink> 
                </div>
              )}
            </div>
          }
      </div>
    );
  }

  render() {
    return (
      <div id="side">
        {this.navigators('item-evolution','Evolution',allRoutes.evolution)}
        {this.navigators('item-lambda','Lambda Patterns',allRoutes.lambda)}
        {this.navigators('item-patterns','Pattern Glossary',allRoutes.patterns)}
      </div>
    );
  }

}

export default Side;