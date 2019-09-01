import React from 'react';
import ImageHolder from '../common/ImageHolder';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';

let code = `

  public interface Fighter {
    void act();
    void react(Move move);
  }

  public interface ArenaMediator {
    void sendMove(Move move, Fighter fighter);
    void addFighter(Fighter fihter);
  }

  public class AConcreteFighter implements Fighter {
    ArenaMediator arena;

    @Override
    public void act() {
      // create a Move
      arena.sendMove(move, this);
    }

    @Override
    public void react(Move move) {
      // react to move somehow
    }

  }

  public class AConcreteArena implements ArenaMediator {

    Fighter champion, challenger;

    @Override
    public void sendMove(Move move, Fighter fighter) {
      if (champion.equals(fighter))
        challenger.react(move);
      else
        champion.react(move);
    }

    @Override
    public void addFighter(Fighter fighter) {
      // add champion and challenger
    }

  }

  // setting it up
  ArenaMediator arena = new AConcreteArena();
  AConcreteFighter fighterA = new AConcreteFighter();
  AConcreteFighter fighterB = new AConcreteFighter();
  arena.addFighter(fighterA);
  arena.addFighter(fighterB);

`;

const EvolutionMediator = () => {
  return (
    <div className="main">
      <div className="pageTitle">A Basic Fight Engine</div>
      <p>Now what do I need to simulate a fight?</p>
      <p><em>Fighters obviously!</em> And a place where they can fight.</p>
      <p>Hmm... So an Arena and at least two fighters.</p>
      <p>
        They should not be tightly coupled. What I mean is that I should be able to have a fighter 
        fight any other fighter, on any arena.
      </p>
      <p>
        Enter <strong>Mediator Pattern</strong> where we have a mediator (in my case, the arena) to 
        mediate the different parties (in my case, the two fighters).
      </p>
      <ImageHolder imgId="evolutionMediatorCD" />
      <p>Every fighter has two main functions. Attack and defend!</p>
      <p>
        Every attack is sent to the arena, who 'mediates' and passes on the attack to the opponent 
        to defend.
      </p>
      <ImageHolder imgId="evolutionMediatorSD" />
      <div className="sectionTitle">Sample Code</div>
      <p>
        Most basic code ever!!
      </p>
      <CodeSnippet code={code} />
      <Navigators prev="evolutionIntro" next="evolutionCommand" />
    </div>
  );
};

export default EvolutionMediator;