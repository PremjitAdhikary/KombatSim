import React from 'react';
import ImageHolder from '../common/ImageHolder';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';

let code = `

  public interface ActionStrategy {
    void perform();
  }

  public abstract class AbstractActionStrategy implements ActionStrategy {
    protected AbstractFighter fighter;

    @Override
    public void perform() {
      ActionCommand action = selectAction();
      execute(action);
    }

    protected abstract ActionCommand selectAction();

    protected abstract void execute(ActionCommand action);

  }

  public class ConcreteActionStrategy extends AbstractActionStrategy {

    @Override
    protected ActionCommand selectAction() {
      List&lt;ActionCommand&gt; actions = this.fighter.allActions();
      // some strategy to select an action from actions 
      return selectedAction;
    }

    @Override
    protected void execute(ActionCommand action) {
      action.execute();
    }

  }

  public class AConcreteFighter implements Fighter {
    List&lt;ActionCommand&gt; actions; // all available actions to the fighter
    ActionStrategy actionStrategy;
    ...

    public List&lt;ActionCommand&gt; allActions() {
      return this.actions;
    }

    @Override
    public void act() {
      actionStrategy.perform();
    }

    public void addAction(ActionCommand action) {
      this.actions.add(action);
    }

    public void setActionStrategy(ActionStrategy actionStrategy) {
      this.actionStrategy = actionStrategy;
    }

    ...

  }

  // setting it up
  AConcreteFighter fighter = new AConcreteFighter();
  ActionCommand actionA = new AConcreteAction();
  fighter.addAction(actionA);
  ActionCommand actionB = new AConcreteAction();
  fighter.addAction(actionB);
  ActionStrategy actionStrategy = new ConcreteActionStrategy();
  fighter.setActionStrategy(actionStrategy);

`;

const EvolutionStrategy = () => {
  //const slash = '&#47;';
  const slash = '/';
  return (
    <div className="main">
      <div className="pageTitle">Strategy For Action</div>
      <p>Ok, now the fighters are capable of delivering hits based upon their properties.</p>
      <p>But should they be limited to just one type of hit?</p>
      <p>If not, which hit to perform?</p>
      <p>
        Introducting <strong>Strategy Pattern</strong>.
      </p>
      <ImageHolder imgId="evolutionStrategyTemplateCD" />
      <p>
        Instead of calling the <code>execute()</code> method of <code>ActionCommand</code> directly, 
        the Fighter delegates the call to it's <code>ActionStrategy.perform()</code>.
      </p>
      <p>
        The method <code>perform()</code> follows <strong>Template Pattern</strong> where it selects 
        an action from the available list of actions available to the fighter and then executes it.
      </p>
      <ImageHolder imgId="evolutionStrategyTemplateSD" />
      <div className="sectionTitle">Sample Code</div>
      <p>
        The modified code looks somewhat like this. The (<code>{slash+slash} some strategy to select 
        an action from actions</code>) differs for different implementation of templates resulting 
        in different strategies.
      </p>
      <CodeSnippet code={code} />
      <div className="sectionTitle">Bridge It</div>
      <p>
        As a small side note, they way our <code>ActionStrategy</code> and <code>ActionCommand
        </code> are in such harmony here, this is what the <em>gurus</em> call <strong>Bridge 
        Pattern</strong>.
      </p>
      <p>
        The idea is that the fighter can have any implementation of the said strategy with any 
        number of hits available to him, none of them are coupled to each other. Again individual 
        strategy implementation can be specifically customized to the fighter, his style and 
        available actions.
      </p>
      <ImageHolder imgId="evolutionBridgeCD" />
      <Navigators prev="evolutionCommand" next="evolutionObserver" />
    </div>
  );
};

export default EvolutionStrategy;