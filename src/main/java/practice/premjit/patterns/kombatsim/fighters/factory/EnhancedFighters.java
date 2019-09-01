package practice.premjit.patterns.kombatsim.fighters.factory;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.fighters.Fighter;
import practice.premjit.patterns.kombatsim.fighters.decorators.AbstractFighterDecorator;
import practice.premjit.patterns.kombatsim.fighters.decorators.AllDecorators;

public class EnhancedFighters extends FighterFactory {
	public static final String FACTORY = "Enhanced";
	public static final String EQUIPED_BOXER = "Equipped Boxer";
	public static final String NINJA = "Ninja";
	public static final String SAMURAI = "Samurai";
	
	private EnhancedFighters() {
		// singleton
	}
	
	static {
		System.out.println("Initializing EnhancedFighters");
		FighterFactory.registerFactory(FACTORY, new EnhancedFighters());
	}

	@Override
	public Fighter getFighter(String fighterSubtype, ArenaMediator arena, String name) {
		switch (fighterSubtype) {
		case EQUIPED_BOXER:
			return new EnhancedFighterBuilder(arena, name)
					.withBaseFighter(ProfessionalFighters.BOXER)
					.withFighterType(EQUIPED_BOXER)
					.withFighterSubtype("Metal Arms")
					.withDecorator(AllDecorators.ENHANCED_ARMS)
					.build();
		case NINJA:
			return new EnhancedFighterBuilder(arena, name)
					.withBaseFighter(ProfessionalFighters.TAEKWONDO)
					.withFighterType(NINJA)
					.withFighterSubtype("Shuriken Jutsu")
					.withDecorator(AllDecorators.NINJA)
					.build();
		case SAMURAI:
			return new EnhancedFighterBuilder(arena, name)
					.withBaseFighter(ProfessionalFighters.KARATEKA)
					.withFighterType(SAMURAI)
					.withFighterSubtype("Sword and Armor")
					.withDecorator(AllDecorators.SAMURAI)
					.build();
		}
		throw new IllegalArgumentException("Invalid input fighter: "+fighterSubtype);
	}
	
	private static class EnhancedFighterBuilder {
		ArenaMediator arena;
		String name;
		String fighterType;
		String fighterSubtype;
		String baseFighter;
		String decorator;
		
		EnhancedFighterBuilder(ArenaMediator arena, String name) {
			this.arena = arena;
			this.name = name;
		}
		
		EnhancedFighterBuilder withFighterType(String fighterType) {
			this.fighterType = fighterType;
			return this;
		}
		
		EnhancedFighterBuilder withFighterSubtype(String fighterSubtype) {
			this.fighterSubtype = fighterSubtype;
			return this;
		}
		
		EnhancedFighterBuilder withBaseFighter(String baseFighter) {
			this.baseFighter = baseFighter;
			return this;
		}
		
		EnhancedFighterBuilder withDecorator(String decorator) {
			this.decorator = decorator;
			return this;
		}
		
		AbstractFighterDecorator build() {
			AbstractFighter base = (AbstractFighter) FighterFactory.getFactory(ProfessionalFighters.FACTORY)
					.getFighter(baseFighter, arena, name);
			AbstractFighterDecorator decorated = AllDecorators.getDecorator(decorator, base);
			decorated.setFighterType(fighterType);
			decorated.setFighterSubType(fighterSubtype);
			return decorated;
		}
	}

}
