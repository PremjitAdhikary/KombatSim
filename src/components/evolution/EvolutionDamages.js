import React from 'react';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';

let codeFire = `

  public interface Move {
    void affect(Fighter fighter);
  }

  public class FireDamage implements Move, BeatObserver {
    double damage;
    int burnDuration;
    double burnDamage;

    Fighter fighter;
    int beatCount;

    public FireDamage(double damage, int duration, double burnDamage) {
      this.damage = damage;
      this.burnDuration = duration;
      this.burnDamage = burnDamage;
    }

    @Override
    public void affect(Fighter fighter) {
      this.fighter = fighter;
      this.fighter.getLife().incrementCurrent(-damage);
      this.fighter.registerObserver(this);
    }

    @Override
    public void update() {
      if (beatCount == burnDuration) {
        this.fighter.unregisterObserver(this);
      } else {
        this.fighter.getLife().incrementCurrent(-burnDamage/burnDuration);
        beatCount++;
      }
    }

  }

  // setting it up
  FireDamage fire = new FireDamage(damage, duration, burnDamage);
  fire.affect(fighter);

`;

let codeCold = `

  public class ColdDamage implements Move, BeatObserver {
    double damage;
    int chillDuration;
    double reduceDexterity;

    Fighter fighter;
    int beatCount;

    ...

    @Override
    public void affect(Fighter fighter) {
      this.fighter = fighter;
      this.fighter.getLife().incrementCurrent(-damage);
      this.fighter.getDexterity().increment(-reduceDexterity);
      this.fighter.registerObserver(this);
    }

    @Override
    public void update() {
      if (beatCount == chillDuration) {
        this.fighter.getDexterity().increment(reduceDexterity); // restore
        this.fighter.unregisterObserver(this);
      } else {
        beatCount++;
      }
    }

  }

`;

let codeShock = `

  public class ShockedActionStrategy implements ActionStrategy {
    ActionStrategy originalAction;
    boolean blocked;

    public ShockedActionStrategy(ActionStrategy originalAction) {
      this.originalAction = originalAction;
      this.blocked = true;
    }

    @Override
    public void perform() {
      if (!blocked) {
        originalAction.perform();
      }
    }

    public void unblock() {
      this.blocked = false;
    }

  }

  public class ShockedReactionStrategy implements ReactionStrategy {
    ReactionStrategy originalReaction;
    boolean blocked;

    ...

    @Override
    public void perform(Move move) {
      if (!blocked) {
        return originalReaction.perform(move);
      }
      return false;
    }

    ...

  }

  public class ShockDamage implements Move, BeatObserver {
    double damage;
    int shockDuration;

    Fighter fighter;
    int beatCount;
    ShockedActionStrategy shockAction;
    ShockedReactionStrategy shockReaction;

    ...

    @Override
    public void affect(Fighter fighter) {
      this.fighter = fighter;
      this.fighter.getLife().incrementCurrent(-damage);
      shockAction = new ShockedActionStrategy(this.fighter.getActionStrategy());
      this.fighter.setActionStrategy(shockAction);
      shockReaction = new ShockedReactionStrategy(this.fighter.getReactionStrategy());
      this.fighter.setReactionStrategy(shockReaction);
      this.fighter.registerObserver(this);
    }

    @Override
    public void update() {
      if (beatCount == shockDuration) {
        shockAction.unblock(); // restore
        shockReaction.unblock();
        this.fighter.unregisterObserver(this);
      } else {
        beatCount++;
      }
    }

  }

`;

const EvolutionDamages = () => {
  return (
    <div className="main">
      <div className="pageTitle">Other Damage Types</div>
      <p>
        Till now I have been blabbering about Hits and damage that it has on the fighter who gets 
        hit. These are physical in nature.
      </p>
      <p>A Punch or Kick causes physical damage to the opponent.</p>
      <p>Here I will branch out into more forms of damage.</p>
      <p>
        <em>Why?</em> Because that gives out some additional variety and opens up new interesting 
        set of fighters.
      </p>
      <div className="sectionTitle">Fire Damage</div>
      <p>This type of damage, along with the initial damage at impact, also adds a burn damage.</p>
      <p><em>What's burn damage?</em></p>
      <p>
        It's additional damage over time. So after the initial impact, for next n number of beats, 
        the fighter keeps on taking additional damage (burning).
      </p>
      <p>
        <em>Implementation?</em> Again thanks to the Observer system that is in place now, it's a 
        piece of cake.
      </p>
      <p>
        To burn, an Observer is registered to the fighter, which at each update decrements the life 
        of the affected fighter.
      </p>
      <p>At nth beat, the Observer unregisters itself from the fighter.</p>
      <p>
        Think of if as an opposite to the life Restorer concept, only with an expiry attached to 
        it.
      </p>
      <CodeSnippet code={codeFire} />
      <div className="sectionTitle">Cold Damage</div>
      <p>
        This type of damage chills the opponent along with the initial damage at impact. The effect 
        of chill is that the fighter is slowed down.
      </p>
      <p><em>How do I slow down a fighter?</em></p>
      <p>
        The fighter's rhythm is a function of the property Dexterity. So all I need to do is lower 
        the dexterity at impact and restore it after the duration of effect is over.
      </p>
      <p>Observer to the rescue! <em>Again!!</em></p>
      <CodeSnippet code={codeCold} />
      <div className="sectionTitle">Shock Damage</div>
      <p><em>What is a shock?</em></p>
      <p>That which renders the fighter offenseless and defenseless.</p>
      <p>
        I hijack the Action and Reaction Strategy at impact and return it after the duration is 
        over.
      </p>
      <p>Observer + Strategy.</p>
      <CodeSnippet code={codeShock} />
      <Navigators prev="evolutionAdapter" next="evolutionHeroes" />
    </div>
  );
};

export default EvolutionDamages;