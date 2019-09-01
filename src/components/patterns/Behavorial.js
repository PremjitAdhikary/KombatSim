import React from 'react';
import Navigators from '../common/Navigators';
import ExternalLink from '../common/ExternalLink';

const Behavorial = () => {
  return (
    <div className="main">
      <div className="pageTitle">Behavorial Patterns</div>
      <div className="sectionTitle">Chain Of Responsibility</div>
      <p>Not implemented</p>
      <div className="sectionTitle">Command</div>
      <ul>
        <li><ExternalLink link="ActionCommand" /> and all it's subclasses.</li>
        <li><ExternalLink link="ReactionCommand" /> and all it's subclasses.</li>
      </ul>
      <div className="sectionTitle">Interpreter</div>
      <p>Not implemented</p>
      <div className="sectionTitle">Observer</div>
      <ul>
        <li><ExternalLink link="BeatObserver" /> and <ExternalLink link="BeatObservable" /> make 
        up the Observer Pattern</li>
        <li>
          Noteable BeatObservers are:
          <ul>
            <li><ExternalLink link="DexterityBasedActionObserver" /></li>
            <li><ExternalLink link="FlipFlopObserver" /></li>
            <li><ExternalLink link="VariableAttributeModifier" /></li>
          </ul>
        </li>
        <li>
          Noteable BeatObservables are: 
          <ul>
            <li><ExternalLink link="BeatObservableImpl" /></li>
            <li><ExternalLink link="TikTok" /></li>
          </ul>
        </li>
      </ul>
      <div className="sectionTitle">Mediator</div>
      <ul>
        <li><ExternalLink link="ArenaMediator" /> and <ExternalLink link="Fighter" /> form the 
        Mediator Pattern</li>
        <li>Check out the implementations in <ExternalLink link="AbstractArena"/> and <ExternalLink 
        link="AbstractFighter"/></li>
      </ul>
      <div className="sectionTitle">Memento</div>
      <ul>
        <li><ExternalLink link="MysticalForest" /> with inner class <code>
        MysticalForest.AttributeMemento</code></li>
      </ul>
      <div className="sectionTitle">State</div>
      <ul>
        <li><ExternalLink link="TagTeam" /> implements State Pattern to switch among fighters.</li>
      </ul>
      <div className="sectionTitle">Strategy</div>
      <ul>
        <li>
          <ExternalLink link="ActionStrategy" /> and <ExternalLink link="ReactionStrategy" />
        </li>
        <li>
           Notable implementations are:
           <ul>
             <li><ExternalLink link="BatmanActionStrategy" /></li>
             <li><ExternalLink link="SpellBook" /></li>
           </ul>
        </li>
      </ul>
      <div className="sectionTitle">Template</div>
      <ul>
        <li>
          <ExternalLink link="AbstractFighterActionStrategy" /> implements <code>
          ActionStrategy.perform()</code> using Template pattern for it's subclasses to add behavior.
        </li>
        <li>
          Similarly <ExternalLink link="AbstractFighterReactionStrategy" /> implements <code>
          ReactionStrategy.perform(move)</code> using Template pattern for it's subclasses.
        </li>
      </ul>
      <div className="sectionTitle">Visitor</div>
      <ul>
        <li>
          <ExternalLink link="MoveVisitor" /> and implementations like:
          <ul>
            <li><ExternalLink link="ArmorDamageAbsorber" /></li>
            <li><ExternalLink link="DamageReducer" /></li>
          </ul>
        </li>
        <li>
          <ExternalLink link="FighterVisitor" /> and implementations like:
           <ul>
             <li><ExternalLink link="ColdFighterVisitor" /></li>
             <li><ExternalLink link="TracerVisitor" /></li>
           </ul>
        </li>
      </ul>
      <Navigators prev="patternStructural" />
    </div>
  );
};

export default Behavorial;