import React from 'react';
import Navigators from '../common/Navigators';
import {Link} from 'react-router-dom';
import  {getRoute} from '../../const/Routes';
import CodeSnippet from '../common/CodeSnippet';

let codeAmateurFighters = `

  public class AmateurFighters extends FighterFactory {

    ...

    @Override
    public Fighter getFighter(
        String fighterSubtype, ArenaMediator arena, String name) {
      switch (fighterSubtype) {
        case NERD:
            return new AmateurFighterBuilder(arena, name, fighterSubtype)
                        .withLife(20)
                        .withStrength(10)
                        .withDexterity(20)
                        .withKick()
                        .withEvade()
                        .build();
        case BULLY:
            return new AmateurFighterBuilder(arena, name, fighterSubtype)
                        .withLife(60)
                        ...
                        .build();
        case CAPTAIN:
            ...
      }
    }


    // internal builder
    private static class AmateurFighterBuilder {

      ...

      AmateurFighterBuilder(
          ArenaMediator arena, String name, String fighterSubtype) {
        ...
      }

      AmateurFighterBuilder withDexterity(double dexterity) {
        this.dexterity = dexterity;
        return this;
      }

      ...

      Amateur build() {
        Amateur fighter = new Amateur(name, arena, fighterSubtype);
        fighter.addDexterity(dexterity);
        ...
        
        if (punch)
          fighter.addAction(new Punch(fighter));

        ...

        return fighter;
      }

    }

  }

  // Setting it up
  Fighter nerd = FighterFactory.getFactory(AmateurFighters.FACTORY)
                    .getFighter(AmateurFighters.NERD, arena, "Some Nerd");

`;

let codeFireDamage = `

  public class FireDamage implements Move {

    double damageAmount;
    int burnDuration;
    double burnDamage;

    private FireDamage(double damage, int burnDuration, double burnDamage) {
      this.damageAmount = damage;
      this.burnDuration = burnDuration;
      this.burnDamage = burnDamage;
    }

    @Override
    public void affect(Fighter fighter) {

    }
    
  }

`;

let codeFireDamageBuilder = `

  public static class FireDamageBuilder {

    double min, max;
    double damage;
    int duration;

    public FireDamageBuilder() {}

    public FireDamageBuilder min(double m) {
      this.min = m;
      return this;
    }

    public FireDamageBuilder max(double m) {
      this.max = m;
      return this;
    }

    public FireDamageBuilder burnDamage(double d) { ... }

    public FireDamageBuilder duration(int d) { ... }

    public FireDamage build() {
      FireDamage d = new FireDamage(randomDoubleInRange(min, max), duration, damage);
    }
  }

  // usage
  FireDamageBuilder builder = new FireDamageBuilder();
  FireDamage fire = builder
                        .min(10)
                        .max(20)
                        .duration(5)
                        .burnDamage(8)
                        .build();

`;

let codeLambdaBuilder = `

  public class FireDamage implements Move {
    
    ...

    private FireDamage(double damage, int burnDuration, double burnDamage) { ... }

    ...

    public static FireDamage create(Consumer&lt;FireDamageBuilder&gt; block) {
      FireDamageBuilder builder = new FireDamageBuilder();
      block.accept(builder);
      return builder.build();
    }

    public static class FireDamageBuilder {

      ...

      private FireDamageBuilder() {}

      ...

      private FireDamage build() { ... }

    }

  }

  // usage
  FireDamage fire = FireDamage.create(builder -> 
          builder
            .min(10)
            .max(20)
            .duration(5)
            .burnDamage(8)
  );

`;

const LambdaLoaner = () => {
  return (
    <div className="main">
      <div className="pageTitle">A Builder on Loan?</div>
      <p>
        Yep, you read it right. It's a <em>Loan</em>(not lone) Builder.
      </p>
      <p>A Formal definition of the <strong>Loaner Pattern</strong> that I could find:</p>
      <blockquote>
      <p>
        <em>When a pair of actions have to be taken together, you can use a HigherOrderFunction that 
        wraps the actions around the function that is passed in.</em>
      </p>
      </blockquote>
      <p>
        Done scratching your head? Allow me to dumb it down for you.
      </p>
      <p>
        Let's go back to the <strong>Builder Pattern</strong> example from an <Link 
        to={getRoute('evolutionCreators').path}>earlier section</Link>.
      </p>
      <CodeSnippet code={codeAmateurFighters} />
      <p>
        The biggest advantage for having a Builder is the ease with which the object creation 
        happens. Instead of having to look into documentation for what goes in a constructor, the 
        builder just <em>flows</em>.
      </p>
      <p>
        What if we try something similar for <code>FireDamage</code>?
      </p>
      <CodeSnippet code={codeFireDamage} />
      <p>
        In the FighterFactories, we have an internal builder which is only used by the outer Factory. 
        The Builder keeps the nitty-gritty of creating and setting up a Fighter to itself. The client 
        request and gets back a Fighter ready for action. Perfect encapsulation.
      </p>
      <p>
        But may I remind you that FireDamage gets instantiated from multiple places with dynamic 
        values! <em>A private builder is out of the question!!!</em>
      </p>
      <CodeSnippet code={codeFireDamageBuilder} />
      <p>
        So what if we have an exposed builder?
      </p>
      <p>
        Well, the client can now reuse the builder.
      </p>
      <p>
        While fine for our <code>FireDamageBuilder</code>, not so cool if the builder has 100s of 
        properties setup. That's a bug cultivator!
      </p>
      <p>
        That's where <strong>Loaner Pattern</strong> comes into the picture!
      </p>
      <p>
        Here's what you do.
        <ul>
          <li>Make <code>FireDamageBuilder</code> an inner class of <code>FireDamage</code>.</li>
          <li>Make the constructor and the build() method of the builder private.</li>
          <li>Add static method create() to <code>FireDamage</code> which loans the builder.</li>
        </ul>
      </p>
      <CodeSnippet code={codeLambdaBuilder} />
      <p>
        No more shared builder. You get a builder on loan, you set it up and that's it!
      </p>
      <Navigators prev="lambdaDecorator" next="lambdaBuilder" />
    </div>
  );
};

export default LambdaLoaner;