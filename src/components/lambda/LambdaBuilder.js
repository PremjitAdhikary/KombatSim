import React from 'react';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';
import {Link} from 'react-router-dom';
import  {getRoute} from '../../const/Routes';

let codeFunctionWeapon = `

  Function&lt;Fighter, FighterDecorator&gt; weapon = baseFighter -> {
    FighterDecorator fighterWithSword = new Weapon(baseFighter);
    fighterWithSword.setName("Broad Sword");
    fighterWithSword.buildAndAddWeaponCommand(new WeaponCommand("Slash", slashDamage));
    fighterWithSword.buildAndAddWeaponCommand(new WeaponCommand("Cut", cutDamage));
    fighterWithSword.replaceActions();
    fighterWithSword.equip();
    return fighterWithSword;
  };

`;

let codeFluentWeaponBuilder = `

  FighterDecorator fighterWithSword = Weapon.create( sword -> 
    sword
      .name("Broad Sword")
      .wielder(baseFighter)
      .addCommand()
      .withName("Slash")
      .andMove(slashDamage)
      .addCommand()
      .withName("Cut")
      .andMove(cutDamage)
      .replaceActions()
  );

`;

let codeWeaponBuilder = `

  public class Weapon extends FighterDecorator {

    ...

    private Weapon(Fighter fighter, String weaponName) { ... }

    ...

    // inner builder

    public static Weapon create(Consumer&lt;WeaponNameBuilder&gt; block) {
      WeaponBuilder builder = new WeaponBuilder();
      block.accept(builder);
      builder.build();
    }

    public interface WeaponNameBuilder {
      WeaponFighterBuilder name(String name);
    }
  
    public interface WeaponFighterBuilder {
      WeaponOptionsBuilder wielder(Fighter fighter);
    }
  
    public interface WeaponOptionsBuilder {
      WeaponCommandNameBuilder addCommand();
      WeaponOptionsBuilder replaceActions();
    }
  
    public interface WeaponCommandNameBuilder {
      WeaponMoveBuilder withName(String name);
    }
  
    public interface WeaponMoveBuilder {
      WeaponOptionsBuilder andMove(Function&lt;Fighter, Move&gt; move);
    }
  
    public static class WeaponBuilder implements WeaponNameBuilder, 
        WeaponFighterBuilder, WeaponOptionsBuilder,
        WeaponCommandNameBuilder, WeaponMoveBuilder
    {
      private String weaponName;
      private String commandName;
      private Weapon weapon;

      private WeaponBuilder() { }

      @Override
      public WeaponFighterBuilder name(String name) {
        weaponName = name;
        return this;
      }

      @Override
      public WeaponOptionsBuilder wielder(Fighter fighter) {
        weapon = new Weapon(fighter, weaponName);
        return this;
      }

      @Override
      public WeaponCommandNameBuilder addCommand() {
        return this;
      }

      @Override
      public WeaponMoveBuilder withName(String name) {
        commandName = name;
        return this;
      }

      @Override
      public WeaponOptionsBuilder andMove(Function&lt;Fighter, Move&gt; move) {
        WeaponCommand action = new WeaponCommand(commandName, move);
        weapon.addAction(action);
        return this;
      }

      @Override
      public WeaponOptionsBuilder replaceActions() {
        weapon.replaceActions = true;
        return this;
      }

      private Weapon build() {
        // validate at least 1 WeaponCommand
        assert(!weapon.allActions().isEmpty());
        weapon.equip();
        return weapon;
      }

    }

    class WeaponCommand implements ActionCommand {

      ...

      WeaponCommand(String name, Function&lt;Fighter, Move&gt; moveFunction) { ... }

      @Override
      public void execute() {
        sendMove(moveFunction.apply(theFighter));
      }


    }

  }

`;

const LambdaBuilder = () => {
  return (
    <div className="main">
      <div className="pageTitle">Building Restrictions</div>
      <p>This section expands upon the previous one.</p>
      <p>Expands more on the Builder side that is. Not much to do with lambda!</p>
      <p>You can skip if you want to.</p>
      <p><em>Still here?</em></p>
      <p>Alright then!</p>
      <p>
        Let's take a look into the Function 'weapon' from the section <Link 
        to={getRoute('lambdaDecorator').path}>Compose/Decorate</Link>.
      </p>
      <CodeSnippet code={codeFunctionWeapon} />
      <p>
        A couple of points pop out:
        <ul>
          <li><em>baseFighter</em> and weapon name are mandatory.</li>
          <li>At least 1 <code>WeaponCommand</code> must be added.</li>
          <li>Call to <code>FighterDecorator.equip()</code> is mandatory at the end.</li>
          <li><code>buildAndAddWeaponCommand()</code> is passed a 'freshly constructed' <code>
          WeaponCommand</code>. Leads to the same problem - 'to look into documentation for what 
          goes in a constructor'</li>
        </ul>
      </p>
      <p>From the above list, two of them can be sorted out.</p>
      <p>
        Check for the presence of 'at least 1 <code>WeaponCommand</code>' and calling <code>
        FighterDecorator.equip()</code>, both can be handled in <code>build()</code>.
      </p>
      <p>
        What if we can guide the client for the rest? What if we could restrict the path of the 
        fluent api? Something like below...
      </p>
      <CodeSnippet code={codeFluentWeaponBuilder} />
      <p>
        Here the builder drives you:
        <ul>
          <li>At <code>sword.</code> the only api exposed is <code>name()</code></li>
          <li>Once <code>name()</code> is set, only api exposed is <code>wielder()</code></li>
          <li>
            At this point you have options to:
            <ul>
              <li>
                <code>addCommand()</code> - to add a <code>WeaponCommand</code>. If this path is 
                taken..
                <ul>
                  <li>The next api exposed is <code>withName()</code></li>
                  <li>Then <code>andMove()</code> is exposed</li>
                  <li>Back to options</li>
                </ul>
              </li>
              <li><code>replaceActions()</code> - to use only <code>WeaponCommand</code>s for 
              attacks</li>
            </ul>
          </li>
        </ul>
      </p>
      <p>Cool!</p>
      <p><em>But how?</em></p>
      <p>A lot of code actually...</p>
      <CodeSnippet code={codeWeaponBuilder} />
      <p>
        I know. It's an overkill. But it's sure a good pattern to know!
      </p>
      <Navigators prev="lambdaLoaner" next="lambdaMore" />
    </div>
  );
};

export default LambdaBuilder;