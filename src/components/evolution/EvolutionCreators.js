import React from 'react';
import Navigators from '../common/Navigators';
import ImageHolder from '../common/ImageHolder';
import CodeSnippet from '../common/CodeSnippet';

let code = `

  public abstract class FighterFactory {

    public static final FighterFactory getFactory(String fighterType) {
      // based on fightertype (Amateur, Professional, etc) find a factory
      return fighterFactory;
    }

    public abstract Fighter getFighter(
      String fighterSubtype, ArenaMediator arena, String name);

  }



  public class AmateurFighters extends FighterFactory {

    public static final String FACTORY = "Amateur";
    public static final String BULLY = "Bully";
    public static final String NERD = "Nerd";
    public static final String CAPTAIN = "Captain";

    private AmateurFighters() {
      // singleton
    }

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

      ArenaMediator arena;
      String name;
      String fighterSubtype;
      double strength, dexterity, life;
      boolean punch, kick, block, evade;
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

      AmateurFighterBuilder withPunch() {
        punch = true;
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

const EvolutionCreators = () => {
  return (
    <div className="main">
      <div className="pageTitle">How To Create So Many Fighters?</div>
      <p>
        Till now all the sections talked about were Design Patterns which were of Structural 
        or Behavioral in nature.
      </p>
      <p>What about the Creational Patterns?</p>
      <p> Before starting on the creation part, let's check what is being created.</p>
      <p>Below image is a view of all possible Fighter Types available.</p>
      <ImageHolder imgId="hierarchyFighter" imgSize="large" />
      <p>
        Note that each Fighter type has multiple subtypes.
        <ul>
          <li>For <code>Amateur</code>, there is Bully, Nerd and Captain</li>
          <li>For <code>Professional</code>, there is Boxer, Karateka and Taekwondo</li>
          <li>For <code>Hero</code>, there is Superman, Flash and Batman</li>
          <li>For <code>Mage</code>, there is Elemental and Dark</li>
          <li>For Enhanced Fighters (<code>Professional</code> fighters enhanced by Decorators), there 
          is Metal-armed Boxer, Samurai and Ninja</li>
        </ul>
      </p>
      <p>
        Let's start with <strong>Factory Pattern</strong> to have dedicated Factories for each of 
        the Fighter types. So there is Amateur Fighter Factory, Professional Fighter Factory, 
        Hero Fighter Factory, Mage Fighter Factory, Enhanced Fighter Factory.
      </p>
      <p>
        Each of these factories are <strong>Singleton</strong> and housing all of them there is the 
        Factory of Factoies <code>FighterFactory</code> following the <strong>Abstract Factory 
        Pattern</strong>.
      </p>
      <p>
        Moreover each of the factories internally uses <strong>Builder Pattern</strong> to build the 
        fighter subtypes.
      </p>
      <CodeSnippet code={code} />
      <Navigators prev="evolutionHits" next="evolutionVisitors" />
    </div>
  );
};

export default EvolutionCreators;