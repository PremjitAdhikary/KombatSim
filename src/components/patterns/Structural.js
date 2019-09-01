import React from 'react';
import Navigators from '../common/Navigators';
import ExternalLink from '../common/ExternalLink';

const Structural = () => {
  return (
    <div className="main">
      <div className="pageTitle">Structural Patterns</div>
      <div className="sectionTitle">Adapter</div>
      <ul>
        <li>
          <ExternalLink link="AbstractTeam" /> and it's subclasses: 
          <ul>
            <li><ExternalLink link="OffenderDefenderTeam" /></li>
            <li><ExternalLink link="TagTeam" /></li>
          </ul>
        </li>
      </ul>
      <div className="sectionTitle">Bridge</div>
      <ul>
        <li>
          The way <ExternalLink link="ActionCommand" /> and <ExternalLink link="ActionStrategy" /> work 
          together in <code>AbstractFighter.act()</code>, it's the Bridge Pattern.
        </li>
        <li>
          Similarly, <ExternalLink link="ReactionCommand" /> and <ExternalLink 
          link="ReactionStrategy" /> form another Bridge.
        </li>
      </ul>
      <div className="sectionTitle">Composite</div>
      <ul>
        <li><ExternalLink link="Endure" /></li>
      </ul>
      <div className="sectionTitle">Decorator</div>
      <ul>
        <li>
          <code>AbstractFighter</code> has following decorators: 
          <ul>
            <li><ExternalLink link="AttributeEnhancer" /></li>
            <li><ExternalLink link="Armor" /></li>
            <li><ExternalLink link="Weapon" /></li>
            <li><ExternalLink link="Projectiles" /></li>
          </ul>
        </li>
      </ul>
      <div className="sectionTitle">Facade</div>
      <ul>
        <li><ExternalLink link="AbstractFighter" /> is a Facade</li>
      </ul>
      <div className="sectionTitle">Flyweight</div>
      <p>Not implemented</p>
      <div className="sectionTitle">Marker</div>
      <p>Not implemented</p>
      <div className="sectionTitle">Proxy</div>
      <ul>
        <li>
          <ExternalLink link="ProxyArena" /> and it's subclasses: 
          <ul>
            <li><ExternalLink link="OffenderDefenderArena" /></li>
            <li><ExternalLink link="TagTeamArena" /></li>
          </ul>
        </li>
      </ul>
      <Navigators prev="patternCreational" next="patternBehavorial" />
    </div>
  );
};

export default Structural;