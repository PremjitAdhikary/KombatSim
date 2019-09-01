import React from 'react';
import {Link} from 'react-router-dom';
import  {getRoute} from '../../const/Routes';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';

let codeAbstractMojoAction = `

  public abstract class MojoBasedAction implements ActionCommand {
    Hero fighter;
    double mojoCost;
    String name;
    ...
    
    public MojoBasedAction(Hero fighter, String name, double mojoCost) {
      ...
    }

    @Override
    public void execute() {
      chargeMojo();
      Move move = calculateMove();
      sendMove(move);
    }

    protected void chargeMojo() {
      this.fighter.getMojo().incrementMojo(-mojoCost);
    }

    protected void sendMove(Move move) {
      // send move to the arena
    }

    protected abstract Move calculateMove();

  }

`;

let codeConcreteMojoAction = `

  public class HeatVision extends MojoBasedAction {
    ...

    public HeatVision(Hero fighter, String name, double mojoCost) {
      super(fighter, name, mojoCost);
      ...
    }
    
    @Override
    protected Move calculateMove() {
      return new FireDamage(damage, duration, burnDamage);
    }

  }

`;

let codeMojoAction = `

  public class MojoBasedAction implements ActionCommand {
    Hero fighter;
    double mojoCost;
    String name;
    Supplier&lt;Move&gt; calculateMove;
    ...
    
    public MojoBasedAction(Hero fighter, String name, 
        double mojoCost, Supplier&lt;Move&gt; calculateMove) {
      ...
    }

    @Override
    public void execute() {
      chargeMojo();
      Move move = calculateMove.get();
      sendMove(move);
    }

    ... // chargeMojo() and sendMove(move) stays the same

  }

`;

let codeHeat = `

  MojoBasedAction heatVision = new MojoBasedAction(
    fighter, 
    "Heat Vision", 
    cost, 
    () -> new FireDamage(damage, duration, burnDamage)
  );

`;

let codeLambda = `
  
  MojoBasedAction freezeBreath = new MojoBasedAction(
    fighter, 
    "Freeze Breath", 
    cost, 
    () -> new ColdDamage(damage, duration, reduceDexterity)
  );
  
  MojoBasedAction arcLightning = new MojoBasedAction(
    fighter, 
    "Arc Lightning", 
    cost, 
    () -> new ShockDamage(damage, duration)
  );

`;

const LambdaCommand = () => {
  return (
    <div className="main">
      <div className="pageTitle">Lambda For Command</div>
      <p>
        We saw how lambda can help redefine the implementation of <strong>Strategy 
        Pattern</strong>. Who say's we are just limited to that?
      </p>
      <p>
        Let's visit <Link to={getRoute('evolutionDecorator').path}>MojoBasedAction</Link> from 
        earlier.
      </p>
      <CodeSnippet code={codeAbstractMojoAction} />
      <p>
        And here we have it's subclass <code>HeatVision</code>.
      </p>
      <CodeSnippet code={codeConcreteMojoAction} />
      <p>
        Notice the similarity with the last section? Lot of plumbing to flesh out a different 
        behavior.
      </p>
      <p>
        We know what to do. But how?
      </p>
      <p>
        First we promote <code>MojoBasedAction</code> from an abstract to a concrete class. Then 
        we add a <code>Supplier</code> to it. And finally remove that annoying abstract 
        method <code>calculateMove()</code>.
      </p>
      <CodeSnippet code={codeMojoAction} />
      <p>
        Now we rewrite HeatVision as follows:
      </p>
      <CodeSnippet code={codeHeat} />
      <p>
        That's it! We keep passing different suppliers to get different actions.
      </p>
      <p>
        We have successfully reduced full-fledged subclasses to mere instantiations!
      </p>
      <CodeSnippet code={codeLambda} />
      <p>
        Same idea is extended to other commands like <code>ActionSpell</code> and <code>
        ReactionSpell</code>.
      </p>
      <Navigators prev="lambdaStrategy" next="lambdaDecorator" />
    </div>
  );
};

export default LambdaCommand;