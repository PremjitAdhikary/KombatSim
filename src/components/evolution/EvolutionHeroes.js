import React from 'react';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';
import ImageHolder from '../common/ImageHolder';

let codeMojo = `

  public abstract class MojoBasedAction implements ActionCommand {
    Hero fighter;
    double mojoCost;
    ...

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

  public class HeatVision extends MojoBasedAction {
    
    @Override
    protected Move calculateMove() {
      return new FireDamage(damage, duration, burnDamage);
    }

  }

  public class FreezeBreath extends MojoBasedAction {
    
    @Override
    protected Move calculateMove() {
      return new ColdDamage(damage, duration, reduceDexterity);
    }

  }

  public class ArcLightning extends MojoBasedAction {
    
    @Override
    protected Move calculateMove() {
      return new ShockDamage(damage, duration);
    }

  }

`;

const EvolutionHeroes = () => {
  return (
    <div className="main">
      <div className="pageTitle">Heroes</div>
      <p>Fighters now <em>can actually</em> stare each other to death!</p>
      <p>
        With the additional type of damages beyond the physical type, now is the time to introduce 
        the next level of fighters.
      </p>
      <p>Heroes have far higher <em>Strength</em> and <em>Dexterity</em> than average fighters.</p>
      <p>
        They can Punch, Kick, Block and Evade but are simply far more efficient and effective at 
        those Actions and Reactions.
      </p>
      <p><em>And they have Super Powers!!!</em></p>
      <p>
        Just like Professionals have an extra attribute <em>Stamina</em>, Heroes have an attribute 
        called <em>Mojo</em>. Mojo allows Heroes to do Mojo based Actions.
      </p>
      <p>
        Superman has Heat Vision (Fire Damage) and Freeze Breath (Cold Damage) while Flash has Arc 
        Lightning (Shock Damage).
      </p>
      <p><em>In case you didn't get it, stare to death refers to Heat Vision.</em></p>
      <div className="sectionTitle">Sample Code for Super Powers</div>
      <CodeSnippet code={codeMojo} />
      <p>
        How can we forget Batman? Batman has no SuperPowers but what he has is technology and a 
        whole lot of willpower.
      </p>
      <p>To mimic willpower, a new Reaction, Endure, is introduced.</p>
      <p>Endure is based on <strong>Composite Pattern</strong>.</p>
      <p>
        Internally it has both Block and Evade. Depending on the situation, Batman chooses either 
        one of those.
      </p>
      <p>
        Beyond that Endure also gives Batman a safety net where in case the damage dealt is fatal 
        in nature, Batman's Mojo is charged and Life is spared. Obviously there is a limit to this 
        otherwise Batman will never die!
      </p>
      <ImageHolder imgId="evolutionCompositeCD" />
      <p>
        Because Batman is <em>not exactly</em> super, his <em>Strength</em> and <em>Dexterity</em> 
        is comparable to a Professional. Batman uses technology to compensate this. He dons an 
        Armor, deploys Projectiles.
      </p>
      <p>That's <strong>Decorator Pattern</strong> for you in action.</p>
      <p>The enhanced Batman is able to stand up against the gods like Superman and Flash.</p>
      <p>
        Flash has another Super Power, Haste. As the name sounds, it makes the already fast Flash 
        even faster for a short duration. The 'how' is revealed in the next section.
      </p>
      <Navigators prev="evolutionDamages" next="evolutionMagic" />
    </div>
  );
};

export default EvolutionHeroes;