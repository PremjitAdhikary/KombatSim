package practice.premjit.patterns.kombatsim.fighters.factory;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.beats.MageActionObserver;
import practice.premjit.patterns.kombatsim.beats.VariableAttributeModifier;
import practice.premjit.patterns.kombatsim.commands.AllActions;
import practice.premjit.patterns.kombatsim.commands.AllReactions;
import practice.premjit.patterns.kombatsim.commands.magic.ActionSpell;
import practice.premjit.patterns.kombatsim.commands.magic.ReflectDamageSpell;
import practice.premjit.patterns.kombatsim.commands.magic.Spells;
import practice.premjit.patterns.kombatsim.fighters.Fighter;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.strategies.magic.SpellBook;

public class Mages extends FighterFactory {
    public static final String FACTORY = Mage.TYPE;
    public static final String ELEMENTAL = "Elemental";
    public static final String DARK = "Dark";
    
    private Mages() {
        // singleton
    }
    
    static {
        System.out.println("Initializing Mages");
        FighterFactory.registerFactory(FACTORY, new Mages());
    }

    @Override
    public Fighter getFighter(String fighterSubtype, ArenaMediator arena, String name) {
        switch (fighterSubtype) {
        case ELEMENTAL:
            Mage elemental = new MageBuilder(arena, name, fighterSubtype)
                .withLife(100, 0.1)
                .withMana(90, 20, 4)
                .build();
            
            ((SpellBook) elemental.getActionStrategy()).setActionChance(100);
            
            elemental.addAction((ActionSpell) Spells.getSpell(AllActions.FLAME_BREATH, elemental));
            elemental.addAction((ActionSpell) Spells.getSpell(AllActions.ICE_BLAST, elemental));
            elemental.addReaction((ReflectDamageSpell) Spells.getSpell(AllReactions.FIRE_SHIELD, elemental));
            elemental.addReaction((ReflectDamageSpell) Spells.getSpell(AllReactions.FROZEN_WALL, elemental));
            
            return elemental;
        case DARK:
            Mage dark = new MageBuilder(arena, name, fighterSubtype)
                .withLife(80, 0.72)
                .withMana(120, 25, 3.6)
                .build();
            
            ((SpellBook) dark.getActionStrategy()).setActionChance(20);
            
            dark.addAction((ActionSpell) Spells.getSpell(AllActions.PARALYZE, dark));
            dark.addAction((ActionSpell) Spells.getSpell(AllActions.LIFE_STEAL, dark));
            dark.addAction((ActionSpell) Spells.getSpell(AllActions.BLEED, dark));
            dark.addReaction((ReflectDamageSpell) Spells.getSpell(AllReactions.THORNS_CURSE, dark));
            
            return dark;
        }
        throw new IllegalArgumentException("Invalid input fighter: "+fighterSubtype);
    }
    
    private static class MageBuilder {
        ArenaMediator arena;
        String name;
        String fighterSubtype;
        double life;
        double lifeIncr; 
        double manaBase; 
        double manaCurrent; 
        double manaIncr;
        
        MageBuilder(ArenaMediator arena, String name, String fighterSubtype) {
            this.arena = arena;
            this.name = name;
            this.fighterSubtype = fighterSubtype;
        }
        
        MageBuilder withLife(double life, double increment) {
            this.life = life;
            this.lifeIncr = increment;
            return this;
        }
        
        MageBuilder withMana(double manaBase, double manaCurrent, double increment) {
            this.manaBase = manaBase;
            this.manaCurrent = manaCurrent;
            this.manaIncr = increment;
            return this;
        }
        
        Mage build() {
            Mage mage = new Mage(name, arena, fighterSubtype);
            
            mage.addAttribute(AttributeUtility.buildLife(life));
            VariableAttributeModifier vaLM = new VariableAttributeModifier(mage, AttributeType.LIFE, lifeIncr);
            mage.registerObserver(vaLM);
            
            mage.addAttribute(AttributeUtility.buildManaAsPrimary(manaBase, manaCurrent));
            VariableAttributeModifier vaMM = new VariableAttributeModifier(mage, AttributeType.MANA, manaIncr);
            mage.registerObserver(vaMM);
            
            SpellBook book = new SpellBook(mage);
            mage.setActionStrategy(book);
            mage.setReactionStrategy(book);
            
            MageActionObserver actionObserver = new MageActionObserver();
            actionObserver.setMage(mage);
            mage.registerObserver(actionObserver);
            
            return mage;
        }
        
    }

}
