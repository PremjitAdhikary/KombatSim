import React from 'react';
import {Link} from 'react-router-dom';
import  {getRoute} from '../../const/Routes';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';

let codeDecorator = `

  ConcreteFighter baseFighter = new ConcreteFighter();
  FighterDecorator fighterWithSword = new Weapon(baseFighter);
  FighterDecorator armoredFighter = new Armor(baseFighter);
  FighterDecorator armoredFighterWithSword = new Armor(new Weapon(baseFighter));

`;

let codeFullDecorator = `

  ConcreteFighter baseFighter = new ConcreteFighter();
  FighterDecorator fighterWithSword = new Weapon(baseFighter);
  fighterWithSword.setName("Broad Sword");
  fighterWithSword.buildAndAddWeaponCommand(new WeaponCommand("Slash", slashDamage));
  fighterWithSword.buildAndAddWeaponCommand(new WeaponCommand("Cut", cutDamage));
  fighterWithSword.replaceActions();
  fighterWithSword.equip();

  ConcreteFighter anotherBaseFighter = new ConcreteFighter();
  FighterDecorator armoredFighter = new Armor(anotherBaseFighter);
  armoredFighter.armorLife(armorLife);
  armoredFighter.damageReduction(reduction);
  armoredFighter.enablePhysicalDamageReduction();
  armoredFighter.equip();

  ConcreteFighter yetAnotherBaseFighter = new ConcreteFighter();
  FighterDecorator anotherFighterWithSword = new Weapon(anotherBaseFighter);
  ... 
  anotherFighterWithSword.equip();
  FighterDecorator anotherFighterWithSwordAndArmor = new Armor(anotherFighterWithSword);
  ... 
  anotherFighterWithSwordAndArmor.equip();

`;

let codeMethodDecorator = `

  FighterDecorator weapon(Fighter baseFighter) {
    FighterDecorator fighterWithSword = new Weapon(baseFighter);
    fighterWithSword.setName("Broad Sword");
    fighterWithSword.buildAndAddWeaponCommand(new WeaponCommand("Slash", slashDamage));
    fighterWithSword.buildAndAddWeaponCommand(new WeaponCommand("Cut", cutDamage));
    fighterWithSword.replaceActions();
    fighterWithSword.equip();
    return fighterWithSword;
  }

  FighterDecorator armor(Fighter baseFighter) {
    FighterDecorator armoredFighter = new Armor(baseFighter);
    armoredFighter.armorLife(armorLife);
    armoredFighter.damageReduction(reduction);
    armoredFighter.enablePhysicalDamageReduction();
    armoredFighter.equip();
    return armoredFighter;
  }

  ConcreteFighter baseFighter = new ConcreteFighter();
  FighterDecorator fighterWithSword = weapon(baseFighter);
  FighterDecorator armoredFighter = armor(baseFighter);
  FighterDecorator armoredFighterWithSword = armor(weapon(baseFighter));

`;

let codeFunctionDecorator = `

  Function&lt;Fighter, FighterDecorator&gt; weapon = baseFighter -> {
    FighterDecorator fighterWithSword = new Weapon(baseFighter);
    fighterWithSword.setName("Broad Sword");
    fighterWithSword.buildAndAddWeaponCommand(new WeaponCommand("Slash", slashDamage));
    fighterWithSword.buildAndAddWeaponCommand(new WeaponCommand("Cut", cutDamage));
    fighterWithSword.replaceActions();
    fighterWithSword.equip();
    return fighterWithSword;
  };

  Function&lt;Fighter, FighterDecorator&gt; armor = baseFighter -> {
    FighterDecorator armoredFighter = new Armor(baseFighter);
    armoredFighter.armorLife(armorLife);
    armoredFighter.damageReduction(reduction);
    armoredFighter.enablePhysicalDamageReduction();
    armoredFighter.equip();
    return armoredFighter;
  };

  ConcreteFighter baseFighter = new ConcreteFighter();
  FighterDecorator fighterWithSword = weapon.apply(baseFighter);
  FighterDecorator armoredFighter = armor.apply(baseFighter);

`;

let codeFunctionComposition = `

  Function&lt;Fighter, FighterDecorator&gt; weaponAndArmor = weapon.andThen(armor);
  FighterDecorator armoredFighterWithSword = weaponAndArmor.apply(baseFighter);

`;

let codeBatmanComposition = `

  Function&lt;Fighter, FighterDecorator&gt; utilityBelt = 
      batarang
          .andThen(thermitePellets)
          .andThen(freezeGrenades)
          .andThen(stunPellets)
          .andThen(gasPellets)
          .andThen(manaBurners)
          .andThen(kryptonite);
  FighterDecorator beltedBatman = utilityBelt.apply(batman);

`;

const LambdaDecorator = () => {
  return (
    <div className="main">
      <div className="pageTitle">Composing Decorators</div>
      <p>
        <strong>Decorator Pattern</strong> is one of the most under appreciated pattern!
      </p>
      <p>
        Obviously it is not studied much.
      </p>
      <p>
        And here I am ... making an already not-so-known pattern even more complicated by 
        injecting Functional elements in it. <em>Or am I?</em>
      </p>
      <p>
        Let's revisit how using <strong>Decorator Pattern</strong> we created <Link 
        to={getRoute('evolutionDecorator').path}>enhanced fighters</Link>.
      </p>
      <CodeSnippet code={codeDecorator} />
      <p>
        In reality though, it's not so simple. A lot of additional code goes in to weaponize 
        a fighter. Something like the below
      </p>
      <CodeSnippet code={codeFullDecorator} />
      <p>
        That's ugly!
      </p>
      <p>
        So what do we do about it? Start by extracting into methods!
      </p>
      <CodeSnippet code={codeMethodDecorator} />
      <p>
        Yup, much better!
      </p>
      <p>
        Let's go a step further. Make them Functions!!
      </p>
      <CodeSnippet code={codeFunctionDecorator} />
      <p>
        And??? What about <code>armoredFighterWithSword</code>?
      </p>
      <p>
        That's where function composition comes into picture. Now I can compose the decorators.
      </p>
      <CodeSnippet code={codeFunctionComposition} />
      <p>
        I know the above doesn't look much, but look how <em>readable</em> it makes Batman's UtilityBelt.
      </p>
      <CodeSnippet code={codeBatmanComposition} />
      <Navigators prev="lambdaCommand" next="lambdaLoaner" />
    </div>
  );
};

export default LambdaDecorator;