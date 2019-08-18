package practice.premjit.patterns.kombatsim.fighters.factory;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.beats.DexterityBasedActionObserver;
import practice.premjit.patterns.kombatsim.beats.VariableAttributeModifier;
import practice.premjit.patterns.kombatsim.commands.AllActions;
import practice.premjit.patterns.kombatsim.commands.hero.Endure;
import practice.premjit.patterns.kombatsim.commands.hero.MojoActions;
import practice.premjit.patterns.kombatsim.commands.physical.Block;
import practice.premjit.patterns.kombatsim.commands.physical.Evade;
import practice.premjit.patterns.kombatsim.commands.physical.Kick;
import practice.premjit.patterns.kombatsim.commands.physical.Punch;
import practice.premjit.patterns.kombatsim.fighters.Fighter;
import practice.premjit.patterns.kombatsim.fighters.Hero;
import practice.premjit.patterns.kombatsim.fighters.decorators.AllDecorators;
import practice.premjit.patterns.kombatsim.strategies.hero.BatmanActionStrategy;
import practice.premjit.patterns.kombatsim.strategies.physical.BasicActionStrategy;
import practice.premjit.patterns.kombatsim.strategies.physical.BasicReactionStrategy;

public class Heroes extends FighterFactory {
	public static final String FACTORY = Hero.TYPE;
	public static final String FLASH = "Flash";
	public static final String SUPERMAN = "Superman";
	public static final String BATMAN = "Batman";
	public static final String EQUIPPED_BATMAN = "Equipped Batman";
	
	private Heroes() {
		// singleton
	}
	
	static {
		System.out.println("Initializing Heroes");
		FighterFactory.registerFactory(FACTORY, new Heroes());
	}

	@Override
	public Fighter getFighter(String fighterSubtype, ArenaMediator arena, String name) {
		switch (fighterSubtype) {
		case FLASH:
			Hero f = new HeroBuilder(arena, name, fighterSubtype)
					.withLife(200, 0.5)
					.withStrength(120)
					.withDexterity(150)
					.withMojo(100, 20, 2)
					.withDexterityPrimary()
					.withKick()
					.withEvade()
					.build();
			
			f.addAction(MojoActions.getAction(AllActions.HASTE, f));
			f.addAction(MojoActions.getAction(AllActions.ARC_LIGHTNING, f));
			
			return f;
		case SUPERMAN:
			Hero s = new HeroBuilder(arena, name, fighterSubtype)
					.withLife(450, 1.0)
					.withStrength(400)
					.withDexterity(80)
					.withMojo(180, 40, 3)
					.withStrengthPrimary()
					.withPunch()
					.withBlock()
					.build();
			
			s.addAction(MojoActions.getAction(AllActions.HEAT_VISION, s));
			s.addAction(MojoActions.getAction(AllActions.FREEZE_BREATH, s));
			
			return s;
		case BATMAN:
			return batman(arena, name, fighterSubtype, false);
		case EQUIPPED_BATMAN:
			Hero b = batman(arena, name, fighterSubtype, true);
			return AllDecorators.getDecorator(AllDecorators.BATMAN, b);
		}
		throw new IllegalArgumentException("Invalid input fighter: "+fighterSubtype);
	}
	
	// Batman setup
	
	private Hero batman(ArenaMediator arena, String name, String fighterSubtype, boolean equipped) {
		Hero b = new HeroBuilder(arena, name, fighterSubtype)
			.withLife(140, 0.4)
			.withStrength(90)
			.withDexterity(70)
			.withMojo(120, 40, 5)
			.withMojoPrimary()
			.withPunch()
			.withKick()
			.build();
		
		b.addReaction(new Endure(b, equipped));
		
		b.setActionStrategy(new BatmanActionStrategy(b, equipped));
		
		return b;
	}
	
	private static class HeroBuilder {
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
		double lifeIncr;
		double mojoBase;
		double mojoCurrent;
		double mojoIncr;
		boolean strPrimary;
		boolean dexPrimary;
		boolean mjPrimary;
		
		HeroBuilder(ArenaMediator arena, String name, String fighterSubtype) {
			this.arena = arena;
			this.name = name;
			this.fighterSubtype = fighterSubtype;
		}
		
		HeroBuilder withStrength(double strength) {
			this.strength = strength;
			return this;
		}
		
		HeroBuilder withDexterity(double dexterity) {
			this.dexterity = dexterity;
			return this;
		}
		
		HeroBuilder withLife(double life, double increment) {
			this.life = life;
			this.lifeIncr = increment;
			return this;
		}
		
		HeroBuilder withMojo(double mojoBase, double mojoCurrent, double increment) {
			this.mojoBase = mojoBase;
			this.mojoCurrent = mojoCurrent;
			this.mojoIncr = increment;
			return this;
		}
		
		HeroBuilder withStrengthPrimary() {
			strPrimary = true;
			dexPrimary = false;
			mjPrimary = false;
			return this;
		}
		
		HeroBuilder withDexterityPrimary() {
			strPrimary = false;
			dexPrimary = true;
			mjPrimary = false;
			return this;
		}
		
		HeroBuilder withMojoPrimary() {
			strPrimary = false;
			dexPrimary = false;
			mjPrimary = true;
			return this;
		}
		
		HeroBuilder withPunch() {
			punch = true;
			return this;
		}
		
		HeroBuilder withKick() {
			kick = true;
			return this;
		}
		
		HeroBuilder withEvade() {
			evade = true;
			return this;
		}
		
		HeroBuilder withBlock() {
			block = true;
			return this;
		}
		
		Hero build() {
			Hero fighter = new Hero(name, arena, fighterSubtype);
			
			fighter.addAttribute(strPrimary ? 
					AttributeUtility.buildStrengthAsPrimary(strength) : AttributeUtility.buildStrength(strength));
			
			fighter.addAttribute(dexPrimary ? 
					AttributeUtility.buildDexterityAsPrimary(dexterity) : AttributeUtility.buildDexterity(dexterity));
			
			fighter.addAttribute(AttributeUtility.buildLife(life));
			VariableAttributeModifier vaLM = new VariableAttributeModifier(fighter, AttributeType.LIFE, lifeIncr);
			fighter.registerObserver(vaLM);
			
			fighter.addAttribute(mjPrimary ? 
					AttributeUtility.buildMojoAsPrimary(mojoBase, mojoCurrent) : 
						AttributeUtility.buildMojo(mojoBase, mojoCurrent));
			VariableAttributeModifier vaMM = new VariableAttributeModifier(fighter, AttributeType.MOJO, mojoIncr);
			fighter.registerObserver(vaMM);
			
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
