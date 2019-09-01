import React from 'react';
import Navigators from '../common/Navigators';
import ImageHolder from '../common/ImageHolder';

const EvolutionVisitors = () => {
  return (
    <div className="main">
      <div className="pageTitle">Visitors</div>
      <p>
        From my understanding, <strong>Visitor Pattern</strong> can be used to solve 2 types of 
        problems. There may be more, but the below is as per my limited understanding of this 
        awesome pattern:
      </p>
      <ul>
        <li>Add new operations to existing class without modifying the structures.</li>
        <li>
          This is going to be a little bit hard to explain. Basically if we have 2 sets of 
          objects where every pair of object has a different outcome, this pattern nails it. 
          The main idea being <em>Double Dispatch</em>.
        </li>
      </ul>
      <p>The implementation here is mostly solving problems of the second nature.</p>
      <div className="sectionTitle">Move Visitor</div>
      <p>Let's revisit Batman.</p>
      <p>
        Batman does not have any Super Powers so he uses technology to his favor. BatSuit is capable 
        of resisting most of the type of attacks that comes Batman's way.
      </p>
      <p>Awesome! <em>But how does that translate into code?</em></p>
      <p><strong>Visitor Pattern</strong> is the answer!</p>
      <p>
        Armor (using <strong>Decorator Pattern</strong>) takes the hit first instead of Batman. But 
        there are different types of damages to deal with!
      </p>
      <ImageHolder imgId="evolutionMoveVisitorCD" imgSize="large" />
      <p>
        When <code>Armor.react()</code> gets a <code>Move</code> it passes a <code>ArmorDamageAbsorber
        </code> to determine the type of damage and accordingly reduce the damage in the <code>Move
        </code>.
      </p>
      <p>This is how Batman is able to stand up against so many types of damages.</p>
      <ImageHolder imgId="evolutionMoveVisitorSD" imgSize="large" />
      <p>In the above diagram, not all Damage types are denoted, but you get the picture!</p>
      <p>
        <code>DamageReducer</code>, a MoveVisitor implementation, works the same way to enable <code>
        ReflectDamageSpell</code> to reflect different types of Moves as they come in.
      </p>
      <div className="sectionTitle">Fighter Visitor</div>
      <p>Now let's dissect Cold Damage.</p>
      <p>
        Cold Damage gives the Chills. So it slows down the opponent. As discussed in an earlier 
        section this is achieved simply by lowering the Dexterity.
      </p>
      <p><em>But Mages don't have Dexterity bub! What now?</em></p>
      <p>
        Eureka moment!! Mages only cast spells. And the ability to cast spells depends on how much 
        Mana the caster has in store. So lower Mana.
      </p>
      <p>Now that we have the solution, <strong>Visitor Pattern</strong> to implement it!</p>
      <ImageHolder imgId="evolutionFighterVisitorSD" imgSize="medium" />
      <p>
        In a similar manner, <code>BatmanActionStrategy</code> utilizes <code>TracerVisitor</code> to 
        determine Fighter type and deliver an attack which massively weakens his opponent.
      </p>
      <p>
        For example if the opponent is Superman, throw a Kyptonite Pellet at him to reduce the mighty 
        Red to a Punching Bag!
      </p>
      <p>If it's Flash we are talking, Stun and Freeze Pellets ought to slow him down to a trickle!</p>
      <p>For Mages, gas them and burn their Mana!</p>
      <Navigators prev="evolutionCreators" next="evolutionMemento" />
    </div>
  );
};

export default EvolutionVisitors;