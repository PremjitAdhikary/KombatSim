import React from 'react';
import ImageHolder from '../common/ImageHolder';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';

let code = `

  public interface Fighter {
    void act();
    void react(Move move);
  }

  public class ConcreteFighter implements Fighter {
    ArenaMediator arena;

    @Override
    public void act() {
      // Fighter acts
    }

    @Override
    public void react(Move move) {
      // Fighter reacts
    }
  }

  public abstract class FighterDecorator extends ConcreteFighter {
    ConcreteFighter fighter;

    public FighterDecorator(ConcreteFighter fighter) {
      this.fighter = fighter;
    }

    @Override
    public void act() {
      this.fighter.act();
    }

    @Override
    public void react(Move move) {
      this.fighter.react(move);
    }
  }

  public class Weapon extends FighterDecorator {

    public Weapon(ConcreteFighter fighter) {
      super(fighter);
    }

    @Override
    public void act() {
      // alternate action
    }

  }

  public class Armor extends FighterDecorator {

    public Armor(ConcreteFighter fighter) {
      super(fighter);
    }

    @Override
    public void react(Move move) {
      reduce(move); // reduce the effect of the move
      this.fighter.react(move);
    }

  }

  // setting it up
  ConcreteFighter baseFighter = new ConcreteFighter();
  FighterDecorator fighterWithSword = new Weapon(baseFighter);
  FighterDecorator armoredFighter = new Armor(baseFighter);
  FighterDecorator armoredFighterWithSword = new Armor(new Weapon(baseFighter));

`;

const EvolutionDecorator = () => {
  return (
    <div className="main">
      <div className="pageTitle">Enhanced Fighters</div>
      <p>I have professional fighters now. <em>Yeah!!!</em></p>
      <p><em>What if I want my fighter to don an armor? What if I want him to slash a sword?</em></p>
      <p>
        The armor changes the way the fighter reacts to moves and the sword changes the way the 
        fighter acts.
      </p>
      <p>
        Well I can have two subclasses <code>ArmoredFighter</code> and <code>FighterWithASword
        </code> to have them.
      </p>
      <p><em>What if I want an armored fighter with a sword?</em></p>
      <p>
        I can have a third subclass with code redundancy (since multiple inheritance) is not 
        possible.
      </p>
      <p>
        Now if I have a third enhancement? A dart? It's another form of a weapon. But not exactly 
        a weapon.
      </p>
      <p>How many subclasses? One for every possible combination will mean seven of them!</p>
      <p>
        Here is where <strong>Decorator Pattern</strong> shines. This pattern allows us to stack  
        multiple decorators on top of each other. Each of these adds a new functionality to the 
        overridden methods.
      </p>
      <ImageHolder imgId="evolutionDecoratorCD" />
      <p>
        For every reaction, the <code>Armor</code> decorator reduces the intensity of the <code>
        Move</code> based on some parameter and passes it on to the wrapped Fighter.
      </p>
      <p>
        Here the wrapped Fighter is another decorator. As <code>Weapon</code> decorator doesn't know 
        how to <code>react()</code> it just forwards the altered Move to the wrapped Fighter, which 
        here is the original Fighter (who then reacts to the 'usually' weakened move).
      </p>
      <p>
        Similarly, when it's time to <code>act()</code>, the <code>Armor</code> forwards it to <code>
        Weapon</code> which responds with an alternate action instead of forwarding it to <code>
        Fighter</code>.
      </p>
      <p>The Sequence Diagram below depicts the behavior described above.</p>
      <ImageHolder imgId="evolutionDecoratorSD" />
      <div className="sectionTitle">Sample Code</div>
      <CodeSnippet code={code} />
      <Navigators prev="evolutionFighter" next="evolutionAdapter" />
    </div>
  );
};

export default EvolutionDecorator;