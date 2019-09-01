import React from 'react';
import Navigators from '../common/Navigators';
import {Link} from 'react-router-dom';
import  {getRoute} from '../../const/Routes';
import CodeSnippet from '../common/CodeSnippet';

let codeLambdaFactory = `

  public final class AllDecorators {

    public static final String SAMURAI = "Samurai";
    ...

    private static final Map&lt;String, Function&lt;Fighter, FighterDecorator&gt;&gt; decoratorMap 
              = new HashMap&lt;&gt;();

    Function&lt;Fighter, FighterDecorator&gt; samuraiArmor = samurai -> 
        Armor.create( aSamuraiArmor -> 
            aSamuraiArmor
              .toProtect(samurai)
              .armorLife(30)
              .damageRecutionMultiplier(0.3)
              .enablePhysicalDamageReduction()
        );

    Function&lt;Fighter, FighterDecorator&gt; samuraiSword = samurai -> 
        Weapon.create( aSword -> 
            aSword
              .name("Sword")
              .wielder(samurai)
              .addCommand()
              .withName("Slash")
              .andMove( f -> 
                  PhysicalDamage.create( damage -> 
                    damage.min(f.strength() * 0.4).max(f.strength() * 0.4 + 10)
                  )
              )
              .addCommand()
              .withName("Cut")
              .andMove( f -> 
                  PhysicalDamage.create( damage -> 
                    damage.min(f.strength() * 0.5).max(f.strength() * 0.5 + 20)
                  )
              )
              .replaceActions()
        );

    Function&lt;Fighter, FighterDecorator&gt; samurai = samuraiArmor.andThen(samuraiSword);

    ...

    static {
      decoratorMap.put(SAMURAI, samurai);
      ...
    }

    private AllDecorators() { }

    public static FighterDecorator getDecorator(String decorator, Fighter baseFighter) {
      if (decoratorMap.contains(decorator)) {
        return decoratorMap.get(decorator).apply(baseFighter);
      }
      throw new IllegalArgumentException("Invalid Decorator");
    }
  }

`;

let codeLambdaCombinatorAction = `

  public class ActionSpell implements ActionCommand {
    ...
    Predicate&lt;Mage&gt; customCondition;

    ...

    @Override
    public boolean canBeExecuted() {
      return isReady() && mageHasMana() && customCondition.test(mage);
    }

  }

`;

let codeLambdaCombinatorSpells = `

  public final class Spells {

    ...

    private static Predicate&lt;Mage&gt; critical = 
          mage -> mage.currentLife() < (mage.maxLife() * 0.2);
          
    private static Function&lt;Mage, ActionSpell&gt; lifeSteal = mage -> 
        ActionSpell.create( action -> 
              action
                .mage(mage)
                .name("LifeSteal")
                ...
                .executeCondition(critical) // mage about to die
        );
          
    private static Function&lt;Mage, ActionSpell&gt; paralyze = mage -> 
        ActionSpell.create( action -> 
              action
                .mage(mage)
                .name("Paralyze")
                ...
                .executeCondition( critical.negate() ) // mage healthy
        );

    ...

  }

`;

const LambdaMore = () => {
  return (
    <div className="main">
      <div className="pageTitle">More Lambda</div>
      <p>Let's check out a few other tricks.</p>
      <div className="sectionTitle">Lambda in Factory</div>
      <p>
        While the <Link to={getRoute('evolutionCreators').path}>FighterFactories</Link> follow the 
        conventional way, <code>AllDecorators</code> and <code>Spells</code> are two factories 
        which are built on the lambda concepts that we went through in the last few sections.
      </p>
      <CodeSnippet code={codeLambdaFactory} />
      <p>
        The above code takes a fighter and transforms him into a Samurai! Other decorators include 
        Ninja, Batman.
      </p>
      <p>
        You might have noticed by now that this form of factory implementation is the result of 
        putting in the combined knowledge of the last three sections.
      </p>
      <p>
        That's Functional programming is at its best! Combine, combine, combine!
      </p>
      <div className="sectionTitle">Combinator Pattern</div>
      <p>This sound just like what we discussed above. And It is!</p>
      <p>
        Combine primitives into more complex structures. That's <strong>Combinator Pattern</strong>.
      </p>
      <p>
        A simple example. <code>ActionSpell.canbeExecuted()</code> takes in an additional custom 
        condition. All these conditions are checked and the result combined to deliver the verdict!
      </p>
      <CodeSnippet code={codeLambdaCombinatorAction} />
      <p>Here in Spells factory, we construct 2 spells where we apply a custom condition.</p>
      <p><em>Life Steal</em> spell can only be executed if the mage health is critically low.</p>
      <p>
        For the spell <em>Paralyze</em> it's otherwise (combines the original condition with a 
        negation).
      </p>
      <CodeSnippet code={codeLambdaCombinatorSpells} />
      <p>
        Simple example, limitless possibilites!
      </p>
      <Navigators prev="lambdaBuilder" />
    </div>
  );
};

export default LambdaMore;