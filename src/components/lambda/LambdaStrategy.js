import React from 'react';
import {Link} from 'react-router-dom';
import  {getRoute} from '../../const/Routes';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';

let codeShockedAction = `

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

`;

let codeShockedReaction = `

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

`;

let codeShockDamage = `

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
        shockAction.unblock(); // restore ActionStrategy
        shockReaction.unblock(); // restore ReactionStrategy
        this.fighter.unregisterObserver(this);
      } else {
        beatCount++;
      }
    }

  }

`;

let codeLambda = `

  public class ShockDamage implements Move, BeatObserver {
    double damage;
    int shockDuration;

    Fighter fighter;
    int beatCount;
    boolean blocked;
    ActionStrategy originalAction;
    ReactionStrategy originalReaction;

    ...

    @Override
    public void affect(Fighter fighter) {
      this.fighter = fighter;
      this.fighter.getLife().incrementCurrent(-damage);

      this.originalAction = this.fighter.getActionStrategy();
      this.fighter.setActionStrategy(
        // lambda action
        () -> Optional.ofNullable(blocked ? null : originalAction)
                .ifPresent(ActionStrategy::perform)
      );
      
      this.originalReaction = this.fighter.getReactionStrategy();
      this.fighter.setReactionStrategy(
        // lambda reaction
        move -> !blocked && originalReaction.perform(move)
      );
      
      this.blocked = true;
      this.fighter.registerObserver(this);
    }

    @Override
    public void update() {
      if (beatCount == shockDuration) {
        this.blocked = false; // restore ActionStrategy and ReactionStrategy
        this.fighter.unregisterObserver(this);
      } else {
        beatCount++;
      }
    }

  }

`;

const LambdaStrategy = () => {
  return (
    <div className="main">
      <div className="pageTitle">Lambda For Behavior</div>
      <p>
        In Behavorial Patterns, subclasses hold different behaviors.
      </p>
      <p>
        Most of the times those subclasses have very minimal 'behavorial code'.
      </p>
      <p>
        With lambda, we can get rid of those pesky minimalist classes. By doing that, related logic 
        can be kept in one place.
      </p>
      <p>
        We'll start with something simple ... <strong>Strategy Pattern</strong>.
      </p>
      <p>
        For this we will look into our old friend <code>ShockDamage</code>. Let's revisit the <Link 
        to={getRoute('evolutionDamages').path}>code</Link> again. Here is the ShockedActionStrategy.
      </p>
      <CodeSnippet code={codeShockedAction} />
      <p>
        And below is the ShockedReactionStrategy.
      </p>
      <p>
        Notice that in both the Strategies, the actual code to <code>perform()</code> is minimal. 
      </p>
      <CodeSnippet code={codeShockedReaction} />
      <p>
        Check in <code>ShockDamage</code>, we have how on <code>affect()</code>, the affected fighter's 
        strategies are wrapped in <code>ShockedActionStrategy</code> and <code>ShockedReactionStrategy
        </code> to be unblocked once the shockDuration is over (from <code>update()</code>).
      </p>
      <CodeSnippet code={codeShockDamage} />
      <p>With lambda to the rescue, look how we can get rid of <em>(a lot of)</em> unnecessary code.</p>
      <p>
        Instead of having separate classes, we will reduce <code>ShockedActionStrategy</code> and <code>
        ShockedReactionStrategy</code> to mere one liners and in <code>ShockDamage</code> itself.
      </p>
      <CodeSnippet code={codeLambda} />
      <Navigators prev="lambdaIntro" next="lambdaCommand" />
    </div>
  );
};

export default LambdaStrategy;