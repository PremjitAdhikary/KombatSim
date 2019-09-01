import React from 'react';
import ImageHolder from '../common/ImageHolder';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';

let code = `

  public interface ActionCommand {
    void execute();
  }

  public class AConcreteAction implements ActionCommand {
    AConcreteFighter fighter;

    public AConcreteAction(Fighter fighter) {
      this.fighter = fighter;
    }

    @Override
    public void execute() {
      Move move = calculateMove();
      sendMove(move);
    }

    private Move calculateMove() {
      // create a move based on Fighter attributes
    }

    private void sendMove() {
      this.fighter.arena().sendMove(move, this.fighter);
    }

  }

  public class AConcreteFighter implements Fighter {
    ArenaMediator arena;
    ActionCommand action;
    ...

    public ArenaMediator arena() {
      return this.arena;
    }

    @Override
    public void act() {
      action.execute();
    }

    public void addAction(ActionCommand action) {
      this.action = action;
    }
    
    ...

  }

  // setting it up
  AConcreteFighter fighter = new AConcreteFighter();
  ActionCommand anAction = new AConcreteAction();
  fighter.addAction(anAction);

`;

const EvolutionCommand = () => {
  return (
    <div className="main">
      <div className="pageTitle">A Fighter Hits</div>
      <p>Basic Engine Up! Fighters can send moves to the arena. Next?</p>
      <p>
        Unless you want the fighters to <em>stare</em> each other to death, we need to give them 
        the capability to hit.
      </p>
      <p>
        These hits should be different for each fighter. Maybe they depend on some characteristics 
        of the fighter?
      </p>
      <p>
        For example a hit from a Punch will have a far more impact if thrown by a stronger fighter. 
        Similarly, an agile fighter has a deadlier Kick.
      </p>
      <p>
        <strong>Command Pattern</strong> allows to wrap the request (in our case the hit) as objects 
        which then can be executed based on the fighter's attributes.
      </p>
      <ImageHolder imgId="evolutionActionCommandCD" />
      <p>
        The <code>execute()</code> method first creates a move (<code>calculateMove()</code>) based 
        on the fighter attributes. 
      </p>
      <p>Then sends it away to the arena for the opponent to react to.</p>
      <ImageHolder imgId="evolutionActionCommandSD" />
      <div className="sectionTitle">Sample Code</div>
      <p>
        Example code to implement command pattern for the hits.
      </p>
      <CodeSnippet code={code} />
      <Navigators prev="evolutionMediator" next="evolutionStrategy" />
    </div>
  );
};

export default EvolutionCommand;