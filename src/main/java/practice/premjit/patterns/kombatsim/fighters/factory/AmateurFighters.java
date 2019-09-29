package practice.premjit.patterns.kombatsim.fighters.factory;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.beats.DexterityBasedActionObserver;
import practice.premjit.patterns.kombatsim.commands.physical.Block;
import practice.premjit.patterns.kombatsim.commands.physical.Evade;
import practice.premjit.patterns.kombatsim.commands.physical.Kick;
import practice.premjit.patterns.kombatsim.commands.physical.Punch;
import practice.premjit.patterns.kombatsim.fighters.Amateur;
import practice.premjit.patterns.kombatsim.fighters.Fighter;
import practice.premjit.patterns.kombatsim.strategies.physical.BasicActionStrategy;
import practice.premjit.patterns.kombatsim.strategies.physical.BasicReactionStrategy;

public class AmateurFighters extends FighterFactory {
    public static final String FACTORY = Amateur.TYPE;
    public static final String BULLY = "Bully";
    public static final String NERD = "Nerd";
    public static final String CAPTAIN = "Captain";
    
    private AmateurFighters() {
        // singleton
    }
    
    static {
        System.out.println("Initializing AmateurFighters");
        FighterFactory.registerFactory(FACTORY, new AmateurFighters());
    }

    @Override
    public Fighter getFighter(String fighterSubtype, ArenaMediator arena, String name) {
        switch (fighterSubtype) {
        case BULLY:
            return new AmateurFighterBuilder(arena, name, fighterSubtype)
                    .withLife(60)
                    .withStrength(35)
                    .withDexterity(10)
                    .withStrengthPrimary()
                    .withPunch()
                    .withBlock()
                    .build();
        case NERD:
            return new AmateurFighterBuilder(arena, name, fighterSubtype)
                    .withLife(20)
                    .withStrength(10)
                    .withDexterity(20)
                    .withDexterityPrimary()
                    .withKick()
                    .withEvade()
                    .build();
        case CAPTAIN:
            return new AmateurFighterBuilder(arena, name, fighterSubtype)
                    .withLife(50)
                    .withStrength(30)
                    .withDexterity(15)
                    .withStrengthPrimary()
                    .withPunch()
                    .withKick()
                    .withBlock()
                    .build();
        }
        throw new IllegalArgumentException("Invalid input fighter: "+fighterSubtype);
    }
    
    private static class AmateurFighterBuilder {
        ArenaMediator arena;
        String name;
        String fighterSubtype;
        boolean punch;
        boolean kick;
        boolean block;
        boolean evade;
        double strength;
        double dexterity;
        double life;
        boolean stPrimary;
        boolean dexPrimary;
        
        AmateurFighterBuilder(ArenaMediator arena, String name, String fighterSubtype) {
            this.arena = arena;
            this.name = name;
            this.fighterSubtype = fighterSubtype;
        }
        
        AmateurFighterBuilder withStrength(double strength) {
            this.strength = strength;
            return this;
        }
        
        AmateurFighterBuilder withDexterity(double dexterity) {
            this.dexterity = dexterity;
            return this;
        }
        
        AmateurFighterBuilder withLife(double life) {
            this.life = life;
            return this;
        }
        
        AmateurFighterBuilder withStrengthPrimary() {
            stPrimary = true;
            dexPrimary = false;
            return this;
        }
        
        AmateurFighterBuilder withDexterityPrimary() {
            dexPrimary = true;
            stPrimary = false;
            return this;
        }
        
        AmateurFighterBuilder withPunch() {
            punch = true;
            return this;
        }
        
        AmateurFighterBuilder withKick() {
            kick = true;
            return this;
        }
        
        AmateurFighterBuilder withEvade() {
            evade = true;
            return this;
        }
        
        AmateurFighterBuilder withBlock() {
            block = true;
            return this;
        }
        
        Amateur build() {
            Amateur fighter = new Amateur(name, arena, fighterSubtype);
            
            fighter.addAttribute(stPrimary ? 
                    AttributeUtility.buildStrengthAsPrimary(strength) : AttributeUtility.buildStrength(strength));
            fighter.addAttribute(dexPrimary ? 
                    AttributeUtility.buildDexterityAsPrimary(dexterity) : AttributeUtility.buildDexterity(dexterity));
            fighter.addAttribute(AttributeUtility.buildLife(life));
            
            fighter.setActionStrategy(new BasicActionStrategy(fighter));
            fighter.setReactionStrategy(new BasicReactionStrategy(fighter));
            
            if (punch)
                fighter.addAction(new Punch(fighter));
            if (kick)
                fighter.addAction(new Kick(fighter));
            if (block)
                fighter.addReaction(new Block(fighter));
            if (evade)
                fighter.addReaction(new Evade(fighter));
            
            DexterityBasedActionObserver actionObserver = new DexterityBasedActionObserver();
            actionObserver.setFighter(fighter);
            fighter.registerObserver(actionObserver);
            
            return fighter;
        }
    }

}
