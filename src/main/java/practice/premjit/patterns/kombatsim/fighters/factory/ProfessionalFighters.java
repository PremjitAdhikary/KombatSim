package practice.premjit.patterns.kombatsim.fighters.factory;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.beats.DexterityBasedActionObserver;
import practice.premjit.patterns.kombatsim.beats.VariableAttributeModifier;
import practice.premjit.patterns.kombatsim.commands.AllActions;
import practice.premjit.patterns.kombatsim.commands.physical.Block;
import practice.premjit.patterns.kombatsim.commands.physical.Evade;
import practice.premjit.patterns.kombatsim.commands.physical.Kick;
import practice.premjit.patterns.kombatsim.commands.physical.Punch;
import practice.premjit.patterns.kombatsim.commands.professional.StaminaBasedAction;
import practice.premjit.patterns.kombatsim.fighters.Fighter;
import practice.premjit.patterns.kombatsim.fighters.Professional;
import practice.premjit.patterns.kombatsim.strategies.physical.BasicActionStrategy;
import practice.premjit.patterns.kombatsim.strategies.physical.BasicReactionStrategy;

public class ProfessionalFighters extends FighterFactory {
	public static final String FACTORY = Professional.TYPE;
	public static final String BOXER = "Boxer";
	public static final String KARATEKA = "Karateka";
	public static final String TAEKWONDO = "Taekwondo";
	
	private ProfessionalFighters() {
		// singleton
	}
	
	static {
		System.out.println("Initializing ProfessionalFighters");
		FighterFactory.registerFactory(FACTORY, new ProfessionalFighters());
	}

	@Override
	public Fighter getFighter(String fighterSubtype, ArenaMediator arena, String name) {
		switch (fighterSubtype) {
		case BOXER:
			return new ProfessionalFighterBuilder(arena, name, fighterSubtype)
					.withLife(160, 0.16)
					.withStrength(100)
					.withDexterity(30)
					.withStamina(80, 25, 1.6)
					.withStrengthPrimary()
					.withPunch()
					.withBlock()
					.withStaminaAction(AllActions.CROSS.value())
					.build();
		case KARATEKA:
			return new ProfessionalFighterBuilder(arena, name, fighterSubtype)
					.withLife(120, 0.12)
					.withStrength(70)
					.withDexterity(40)
					.withStamina(120, 25, 2)
					.withStaminaPrimary()
					.withPunch()
					.withKick()
					.withBlock()
					.withStaminaAction(AllActions.CHOP.value())
					.build();
		case TAEKWONDO:
			return new ProfessionalFighterBuilder(arena, name, fighterSubtype)
					.withLife(90, 0.1)
					.withStrength(30)
					.withDexterity(50)
					.withStamina(80, 25, 1.6)
					.withDexterityPrimary()
					.withKick()
					.withEvade()
					.withStaminaAction(AllActions.FLYING_KICK.value())
					.build();
		}
		throw new IllegalArgumentException("Invalid input fighter: "+fighterSubtype);
	}
	
	private static class ProfessionalFighterBuilder {
		ArenaMediator arena;
		String name;
		String fighterSubtype;
		boolean punch;
		boolean kick;
		boolean block;
		boolean evade;
		boolean staminaAction;
		String staminaActionName;
		double strength;
		double dexterity; 
		double life;  
		double lifeIncr; 
		double staminaBase; 
		double staminaCurrent; 
		double staminaIncr;
		boolean strPrimary;
		boolean dexPrimary;
		boolean staPrimary;
		
		ProfessionalFighterBuilder(ArenaMediator arena, String name, String fighterSubtype) {
			this.arena = arena;
			this.name = name;
			this.fighterSubtype = fighterSubtype;
		}
		
		ProfessionalFighterBuilder withStrength(double strength) {
			this.strength = strength;
			return this;
		}
		
		ProfessionalFighterBuilder withDexterity(double dexterity) {
			this.dexterity = dexterity;
			return this;
		}
		
		ProfessionalFighterBuilder withLife(double life, double increment) {
			this.life = life;
			this.lifeIncr = increment;
			return this;
		}
		
		ProfessionalFighterBuilder withStamina(double staminaBase, double staminaCurrent, double increment) {
			this.staminaBase = staminaBase;
			this.staminaCurrent = staminaCurrent;
			this.staminaIncr = increment;
			return this;
		}
		
		ProfessionalFighterBuilder withStrengthPrimary() {
			strPrimary = true;
			dexPrimary = false;
			staPrimary = false;
			return this;
		}
		
		ProfessionalFighterBuilder withDexterityPrimary() {
			strPrimary = false;
			dexPrimary = true;
			staPrimary = false;
			return this;
		}
		
		ProfessionalFighterBuilder withStaminaPrimary() {
			strPrimary = false;
			dexPrimary = false;
			staPrimary = true;
			return this;
		}
		
		ProfessionalFighterBuilder withPunch() {
			punch = true;
			return this;
		}
		
		ProfessionalFighterBuilder withKick() {
			kick = true;
			return this;
		}
		
		ProfessionalFighterBuilder withEvade() {
			evade = true;
			return this;
		}
		
		ProfessionalFighterBuilder withBlock() {
			block = true;
			return this;
		}
		
		ProfessionalFighterBuilder withStaminaAction(String name) {
			staminaAction = true;
			staminaActionName = name;
			return this;
		}
		
		Professional build() {
			Professional fighter = new Professional(name, arena, fighterSubtype);
			
			fighter.addAttribute(strPrimary ? 
					AttributeUtility.buildStrengthAsPrimary(strength) : AttributeUtility.buildStrength(strength));
			
			fighter.addAttribute(dexPrimary ? 
					AttributeUtility.buildDexterityAsPrimary(dexterity) : AttributeUtility.buildDexterity(dexterity));
			
			fighter.addAttribute(AttributeUtility.buildLife(life));
			VariableAttributeModifier vaLM = new VariableAttributeModifier(fighter, AttributeType.LIFE, lifeIncr);
			fighter.registerObserver(vaLM);
			
			fighter.addAttribute(staPrimary ? 
					AttributeUtility.buildStaminaAsPrimary(staminaBase, staminaCurrent) : 
						AttributeUtility.buildStamina(staminaBase, staminaCurrent));
			VariableAttributeModifier vaSM = new VariableAttributeModifier(fighter, AttributeType.STAMINA, staminaIncr);
			fighter.registerObserver(vaSM);
			
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
			if (staminaAction)
				fighter.addAction(new StaminaBasedAction(fighter, staminaActionName));
			
			DexterityBasedActionObserver actionObserver = new DexterityBasedActionObserver();
			actionObserver.setFighter(fighter);
			fighter.registerObserver(actionObserver);
			
			return fighter;
		}
		
	}

}
