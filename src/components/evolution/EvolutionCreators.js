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
      </p>
      <ul>
        <li>
          For <code>Amateur</code>, there is Bully, Nerd and Captain. As mentioned earlier, these 
          are the most basic of fighters with Life, Strength and Dexterity.
          <ul>
            <li>Bully is strong but slow.</li>
            <li>Nerd is weak but fast.</li>
            <li>Captain is balanced.</li>
          </ul>
        </li>
        <li>
          For <code>Professional</code>, there is Boxer, Karateka and Taekwondo. They have an 
          additional attribute, stamina which they utilise to execute special hits. They are far 
          better than any of the amateurs.
          <ul>
            <li>Boxer is strongest but slowest of the professionals.</li>
            <li>Karateka is the balanced one.</li>
            <li>Taekwondo is fastest but the weakest among the three.</li>
          </ul>
        </li>
        <li>
          For Enhanced Fighters (<code>Professional</code> fighters enhanced by Decorators), there 
          is Metal-armed Boxer, Samurai and Ninja.
          <ul>
            <li>Metal-armed Boxer is just a much stronger Boxer.</li>
            <li>
              Samurai is an enhanced Karateka with armor and weapon. The sword restricts him 
              from using his original attack abilities, but gives him new abilities.
            </li>
            <li>
              Ninja is an enhanced Taekwondo with additional agility, weapon and projectiles. Unlike 
              Samurai sword, Ninja's weapon doesnt restrict him from using his original atttack 
              abilities, but on the contrary adds to it.
            </li>
          </ul>
        </li>
        <li>
          For <code>Hero</code>, there is Superman, Flash and Batman. They have an attribute called 
          Mojo which they use for their powers.
          <ul>
            <li>Superman is the strongest and very fast.</li>
            <li>Flash is the fastest and quite strong too.</li>
            <li>
              Batman, being the non-super hero here, is the weakest and slowest among the three. But 
              what he lacks there he more than makes up for it using his gadgets. So along with 
              the vanilla version we also have an equipped version of Batman (Decorator Pattern) who 
              is more than a match for the other gods in this category.
            </li>
          </ul>
        </li>
        <li>
          For <code>Mage</code>, there is Elemental and Dark. They have only 2 attributes. Life and 
          Mana. They use spells for both offense and defense. They use Mana for all the spells they 
          cast. They are weak but make up for it with their fantastical powers and are able to match 
          the Heroes in any fight.
          <ul>
            <li>Elemental uses elemental spells consisting of fire and cold.</li>
            <li>
              Dark uses, well, dark magic. His spells affect the core attributes and life of 
              opponent.
            </li>
          </ul>
        </li>
      </ul>
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