import React from 'react';
import {Link} from 'react-router-dom';
import  {getRoute} from '../../const/Routes';
import Navigators from '../common/Navigators';

const EvolutionFighter = () => {
  return (
    <div className="main">
      <div className="pageTitle">So What Is A Fighter?</div>
      <p>
        A <code>ConcreteFighter</code> is a <strong>Facade</strong> of <code>ActionCommand
        </code>(s), <code>ActionStrategy</code>, <code>ReactionCommand</code>(s), <code>
        ReactionStrategy</code>, <code>BeatObservable</code>, I guess that's it.
      </p>
      <p>
        Remember that in all those previous pages you came across <em>Fighter properties</em> and 
        how they affect the fighter.
      </p>
      <p>Let's explore those properties, <em>a.k.a</em> attributes a bit.</p>
      <div className="sectionTitle">Attribute: Life</div>
      <p>The most important attribute!!!</p>
      <p>Every fighter starts with it being full.</p>
      <p>
        Every hit the fighter takes, depending on the damage the hit delivers, a fraction of the 
        fighters life is lost.
      </p>
      <p>If all of life is depleted, the fighter is knocked out and he loses the fight.</p>
      <div className="sectionTitle">Attribute: Strength</div>
      <p>This is an attribute which decides the amount of damage a hit can dish out.</p>
      <p>
        <code>Punch</code> which is one of the subclasses of <code>ActionCommand</code>, calculates 
        move based on function of Strength. Stronger the fighter has, deadlier his Punch.
      </p>
      <div className="sectionTitle">Attribute: Dexterity</div>
      <p>This defines how agile is the fighter.</p>
      <p>
        Remember the rhythm? Dexterity is the fuel for it.
      </p>
      <p><em>What???</em></p>
      <p>
        What I mean by it is that based on Dexterity, <code>ActionObserver</code> calls <code>
        Fighter.act()</code>. More the Dexterity, less the number of beats the fighter needs to 
        wait before he can hit again.
      </p>
      <p>
        Similarly, more the dexterity, more the number of hits the fighter can react to in a given 
        time period.
      </p>
      <div className="sectionTitle">Amateur Fighters</div>
      <p>With these basic set of attributes, we have our first class of fighter, the Amateur.</p>
      <p>
        They have access to 2 types of ActionCommand, Punch and Kick. While Punch depends on 
        strength, Kick depends on both Strength and Dexterity.
      </p>
      <p>
        Amateurs also have 2 types of ReactionCommand, Block and Evade. Block depends on Strength 
        (more the Strength, lesser the Damage taken) while Evade depends on Dexterity (more the 
        Dexterity, higher the chance to Evade the hit).
      </p>
      <div className="sectionTitle">Professional Fighters</div>
      <p>
        Before I start on Professional Fighter's, a little bit on the attributes.
        <ul>
          <li>
            Life keeps on varying through out the fight. Every hit taken reduces it. Let's call 
            this variable attribute.
          </li>
          <li>But note that Strength and Dexterity dont't. Let's keep it as attribute.</li>
        </ul>
      </p>
      <p>To a Professional I added another variable attribute, <strong>Stamina</strong>.</p>
      <p>Every professional will have a custom ActionCommand based on Stamina.</p>
      <p>
        Every such actions affect will be a function of the amount of Stamina the fighter has. 
        It will also have a cost on the Stamina itself.
      </p>
      <p>
        Along with that another advantage the Professional fighters enjoy is Restorers. These 
        Restorers keep replenishing their life and stamina over time.
      </p>
      <p><em>How did I implement that?</em></p>
      <p>
        Implement a <code>BeatObserver</code> which increments a variable attribute by some 
        amount at every <code>update()</code> and register it to the Professional's beat.
      </p>
      <p>
        Again, this feature would not have been possible without the design change of incorporating 
        the heartbeat system discussed <Link to={getRoute('evolutionObserver').path}>here</Link>.
      </p>
      <Navigators prev="evolutionActionReaction" next="evolutionDecorator" />
    </div>
  );
};

export default EvolutionFighter;