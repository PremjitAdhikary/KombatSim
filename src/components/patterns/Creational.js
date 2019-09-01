import React from 'react';
import Navigators from '../common/Navigators';
import ExternalLink from '../common/ExternalLink';

const Creational = () => {
  return (
    <div className="main">
      <div className="pageTitle">Creational Patterns</div>
      <div className="sectionTitle">Singleton</div>
      <ul>
        <li><ExternalLink link="ArenaFactory" /></li>
        <li><ExternalLink link="AmateurFighters" /></li>
        <li><ExternalLink link="ProfessionalFighters" /></li>
        <li><ExternalLink link="EnhancedFighters" /></li>
        <li><ExternalLink link="Heroes" /></li>
        <li><ExternalLink link="Mages" /></li>
      </ul>
      <div className="sectionTitle">Factory</div>
      <ul>
        <li><ExternalLink link="ArenaFactory" /> is a Factory for all the Arenas</li>
        <li>
          <ExternalLink link="FighterFactory" /> is an <em>AbstractFactory</em> for 
          <ul>
            <li><ExternalLink link="AmateurFighters" /></li>
            <li><ExternalLink link="ProfessionalFighters" /></li>
            <li><ExternalLink link="EnhancedFighters" /></li>
            <li><ExternalLink link="Heroes" /></li>
            <li><ExternalLink link="Mages" /></li>
          </ul>
        </li>
      </ul>
      <div className="sectionTitle">Builder</div>
      <ul>
        <li>
          All Factories for fighters rely on Builder Pattern.
          <ul>
            <li><ExternalLink link="AmateurFighterBuilder"/></li>
            <li><ExternalLink link="ProfessionalFighterBuilder"/></li>
            <li><ExternalLink link="EnhancedFighterBuilder"/></li>
            <li><ExternalLink link="HeroBuilder"/></li>
            <li><ExternalLink link="MageBuilder"/></li>
          </ul>
        </li>
        <li>
          All Moves have a create method which is based on inner builder class.
          <ul>
            <li><ExternalLink link="PhysicalDamageBuilder"/></li>
            <li><ExternalLink link="FireDamageBuilder"/></li>
            <li><ExternalLink link="ColdDamageBuilder"/></li>
            <li><ExternalLink link="ShockDamageBuilder"/></li>
            <li><ExternalLink link="AffectAttributeBuilder"/></li>
            <li><ExternalLink link="AffectVariableAttributeBuilder"/></li>
          </ul>
        </li>
        <li>
          Just like Moves, few Commands too have inner builders exposed by the create 
          api. 
          <ul>
            <li><ExternalLink link="MojoActionBuilder"/></li>
            <li><ExternalLink link="ActionSpellBuilder"/></li>
            <li><ExternalLink link="RelfectDamageSpellBuilder"/></li>
          </ul>
        </li>
        <li>
          All the Decorators also have thier own inner builders. 
          <ul>
            <li><ExternalLink link="AttributeEnhancerBuilder"/></li>
            <li><ExternalLink link="ArmorBuilder"/></li>
            <li><ExternalLink link="WeaponBuilder"/></li>
            <li><ExternalLink link="ProjectilesBuilder"/></li>
          </ul>
        </li>
      </ul>
      <div className="sectionTitle">Object Pool</div>
      <ul>
        <li><ExternalLink link="SpellBook"/> is inspired from Object Pool</li>
      </ul>
      <div className="sectionTitle">Prototype</div>
      <p>Not implemented</p>
      <Navigators prev="patternIntro" next="patternStructural" />
    </div>
  );
};

export default Creational;