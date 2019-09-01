import React from 'react';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';

let codeSpellBook = `

  public class SpellBook implements ActionStrategy, ReactionStrategy {

    protected Mage mage;

    // Action Command Pool
    protected List&lt;ActionSpell&gt; actionSpellsReady;
    protected List&lt;ActionSpell&gt; actionSpellsCooldown;

    // Reaction Command Pool
    protected List&lt;ReactionSpell&gt; reactionSpellsReady;
    protected List&lt;ReactionSpell&gt; reactionSpellsCooldown;


    @Override
    public void perform() {
      // selects a Spell from actionSpellsReady
      ActionSpell action = getReactionSpell();

      // at execution, the Spell gets removed from actionSpellsReady 
      // and gets added to actionSpellsCooldown
      // after Cooldown, the Spell again moves back to actionSpellsReady
      action.execute();
    }


    @Override
    public boolean perform(Optional<Move> move) {
      // at any point of the fight, anyone of the available reactions of the 
      // Mage must be active
      if (!isReactionActive()) {
        // say no Spell is active, select a Spell from reactionSpellsReady
        ReactionSpell reaction = getReactionSpell();
        // at activation, move it to reactionSpellsCooldown
        reaction.activate();
      }

      ReactionSpell activeReaction = getActiveReaction();
      activeReaction.execute(move);
      return true;
    }

    ...

  }

`;

const EvolutionMagic = () => {
  return (
    <div className="main">
      <div className="pageTitle">Mages, Spells and Buffs</div>
      <p>
        Fire, Cold, Shock, that's fantasyland right there. No surprises that I wanted Mages in my 
        Simulator.
      </p>
      <p>
        I wanted to make the Mages as different as possible from any of the fighters that I have 
        come up with yet.
      </p>
      <p>First I wanted them to be independent of the Dexterity based system.</p>
      <p>Also I didn't want my Mages to Punch and Kick like those common fighters.</p>
      <div className="sectionTitle">Spells and Spell Book</div>
      <p>My Mages will just have 2 attributes. <em>Life</em> and <em>Mana</em>.</p>
      <p>All Mages can do is cast Spells. Offensive and Defensive.</p>
      <p>Mana is the fuel for all Spells and each Spell have a Cooldown associated with it.</p>
      <p><em>Cooldown?</em></p>
      <p>
        Cooldown is the factor that will be the so called rhythm for the Mages. Spells can only be 
        cast if it is ready. Once casted, there is a cooldown period during which the Spell is not 
        ready (and hence can't be casted).
      </p>
      <p>
        Defensive Spells are essentially some sort of a protective shield that defends against any 
        attack on the Mages.
      </p>
      <p>
        Defensive Spells have an additional property called active which when casted, stays activated 
        for a certain duration and then dissipates.
      </p>
      <p>Again, and you must be tired of hearing this by now, <strong>Observer Pattern</strong>.</p>
      <p>
        Instead of having separate ActionStrategy and ReactionStrategy for selecting actions and 
        reactions from a list, as the logic for availability of Spells is embedded in the Spells 
        themselves, we have a SpellBook to deploy the Commands. This is modelled after <strong>Object 
          Pool Pattern</strong>.
      </p>
      <CodeSnippet code={codeSpellBook} />
      <p><em>So what kind of damage does a Spell do?</em></p>
      <p>Action Spells can deliver Fire, Cold or Shock Damage.</p>
      <p>Reaction Spells can also deliver a feedback damage.</p>
      <p>
        For example, Elemental Mage can conjure up a Fire Shield which can burn anyone who attacks 
        the Mage.
      </p>
      <p>But these are all damages that can be reacted to.</p>
      <p><em>What if there are moves that simply affects the fighter?</em></p>
      <div className="sectionTitle">Buffs</div>
      <p>
        Remember the additional effect of Cold Damage? It reduces the Dexterity for a certain 
        duration to slow the affected fighter.
      </p>
      <p>
        Buffs are Moves which depending on the type of Buff, might affect any one or more attributes, 
        gradually or on-shot, in a temporary or permanent way.
      </p>
      <p>
        In the last section a Super Power, Haste was mentioned. Haste results in a Buff which 
        Flash casts on self to boost his <em>Dexterity</em> for a short burst.
      </p>
      <p>Dark Mage has a couple of Buffs under his disposal.</p>
      <p>
        Most interesting of them being 'Attribute Steal' where one of opponent's Variable Attributes 
        is targeted and it's points siphoned out to fill up one of Dark Mage's Variable Attributes.
      </p>
      <p>
        Batman has a myriad projectiles. While most of them are of the Damage type, few of them 
        deliver powerful Buffs too.
      </p>
      <Navigators prev="evolutionHeroes" next="evolutionHits" />
    </div>
  );
};

export default EvolutionMagic;