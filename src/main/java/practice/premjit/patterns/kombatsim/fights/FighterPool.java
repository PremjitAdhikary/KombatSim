package practice.premjit.patterns.kombatsim.fights;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.fighters.factory.AmateurFighters;
import practice.premjit.patterns.kombatsim.fighters.factory.EnhancedFighters;
import practice.premjit.patterns.kombatsim.fighters.factory.FighterFactory;
import practice.premjit.patterns.kombatsim.fighters.factory.Heroes;
import practice.premjit.patterns.kombatsim.fighters.factory.Mages;
import practice.premjit.patterns.kombatsim.fighters.factory.ProfessionalFighters;

public final class FighterPool {
	private static final Set<String> uniqueFighters = new HashSet<>();
	private static final Map<String, Boolean> fightersAvailable = new HashMap<>();
	private static final Map<String, BiFunction<ArenaMediator, String, AbstractFighter>> fighterFunctions 
		= new HashMap<>();
	
	static {
		uniqueFighters.add(Heroes.SUPERMAN);
		uniqueFighters.add(Heroes.FLASH);
		uniqueFighters.add(Heroes.BATMAN);
		uniqueFighters.add(Heroes.EQUIPPED_BATMAN);
		uniqueFighters.add(Mages.ELEMENTAL);
		uniqueFighters.add(Mages.DARK);
		
		fightersAvailable.put(AmateurFighters.BULLY, true);
		fightersAvailable.put(AmateurFighters.CAPTAIN, true);
		fightersAvailable.put(AmateurFighters.NERD, true);
		
		fightersAvailable.put(ProfessionalFighters.BOXER, true);
		fightersAvailable.put(ProfessionalFighters.KARATEKA, true);
		fightersAvailable.put(ProfessionalFighters.TAEKWONDO, true);
		
		fightersAvailable.put(EnhancedFighters.EQUIPED_BOXER, true);
		fightersAvailable.put(EnhancedFighters.SAMURAI, true);
		fightersAvailable.put(EnhancedFighters.NINJA, true);

		fightersAvailable.put(Heroes.SUPERMAN, true);
		fightersAvailable.put(Heroes.FLASH, true);
		fightersAvailable.put(Heroes.BATMAN, true);
		fightersAvailable.put(Heroes.EQUIPPED_BATMAN, true);

		fightersAvailable.put(Mages.ELEMENTAL, true);
		fightersAvailable.put(Mages.DARK, true);
		
		fighterFunctions.put(AmateurFighters.BULLY, FighterPool::bully);
		fighterFunctions.put(AmateurFighters.CAPTAIN, FighterPool::captain);
		fighterFunctions.put(AmateurFighters.NERD, FighterPool::nerd);
		
		fighterFunctions.put(ProfessionalFighters.BOXER, FighterPool::boxer);
		fighterFunctions.put(ProfessionalFighters.KARATEKA, FighterPool::karateka);
		fighterFunctions.put(ProfessionalFighters.TAEKWONDO, FighterPool::taekwondo);
		
		fighterFunctions.put(EnhancedFighters.EQUIPED_BOXER, FighterPool::equippedBoxer);
		fighterFunctions.put(EnhancedFighters.SAMURAI, FighterPool::samurai);
		fighterFunctions.put(EnhancedFighters.NINJA, FighterPool::ninja);
        
		fighterFunctions.put(Heroes.SUPERMAN, (a, n) -> superman(a));
		fighterFunctions.put(Heroes.FLASH, (a, n) -> flash(a));
		fighterFunctions.put(Heroes.BATMAN, (a, n) -> batman(a));
		fighterFunctions.put(Heroes.EQUIPPED_BATMAN, (a, n) -> equippedBatman(a));
        
		fighterFunctions.put(Mages.ELEMENTAL, FighterPool::elemental);
		fighterFunctions.put(Mages.DARK, FighterPool::dark);
	}
	
	public static List<String> availableFighters() {
		return fightersAvailable.entrySet().stream()
				.filter(Map.Entry::getValue)
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}
	
	public static void reset() {
		fightersAvailable.entrySet().stream()
				.forEach(e -> e.setValue(true));
	}
	
	private FighterPool() { }
	
	public static AbstractFighter get(ArenaMediator arena, String name, String subtype) {
		if (fighterFunctions.containsKey(subtype))
			return fighterFunctions.get(subtype).apply(arena, name);
		throw new IllegalArgumentException("Fighter "+subtype+" not available!");
	}
	
	public static AbstractFighter bully(ArenaMediator arena, String name) {
		return amateur(arena, name, AmateurFighters.BULLY);
	}
	
	public static AbstractFighter nerd(ArenaMediator arena, String name) {
		return amateur(arena, name, AmateurFighters.NERD);
	}
	
	public static AbstractFighter captain(ArenaMediator arena, String name) {
		return amateur(arena, name, AmateurFighters.CAPTAIN);
	}
	
	private static AbstractFighter amateur(ArenaMediator arena, String name, String subtype) {
		validate(subtype);
		return (AbstractFighter) FighterFactory
				.getFactory(AmateurFighters.FACTORY)
				.getFighter(subtype, arena, name);
	}
	
	public static AbstractFighter boxer(ArenaMediator arena, String name) {
		return professional(arena, name, ProfessionalFighters.BOXER);
	}
	
	public static AbstractFighter karateka(ArenaMediator arena, String name) {
		return professional(arena, name, ProfessionalFighters.KARATEKA);
	}
	
	public static AbstractFighter taekwondo(ArenaMediator arena, String name) {
		return professional(arena, name, ProfessionalFighters.TAEKWONDO);
	}
	
	private static AbstractFighter professional(ArenaMediator arena, String name, String subtype) {
		validate(subtype);
		return (AbstractFighter) FighterFactory
				.getFactory(ProfessionalFighters.FACTORY)
				.getFighter(subtype, arena, name);
	}
	
	public static AbstractFighter equippedBoxer(ArenaMediator arena, String name) {
		return enhancedFighters(arena, name, EnhancedFighters.EQUIPED_BOXER);
	}
	
	public static AbstractFighter samurai(ArenaMediator arena, String name) {
		return enhancedFighters(arena, name, EnhancedFighters.SAMURAI);
	}
	
	public static AbstractFighter ninja(ArenaMediator arena, String name) {
		return enhancedFighters(arena, name, EnhancedFighters.NINJA);
	}
	
	private static AbstractFighter enhancedFighters(ArenaMediator arena, String name, String subtype) {
		validate(subtype);
		return (AbstractFighter) FighterFactory
				.getFactory(EnhancedFighters.FACTORY)
				.getFighter(subtype, arena, name);
	}
	
	public static AbstractFighter superman(ArenaMediator arena) {
		return hero(arena, "Superman", Heroes.SUPERMAN);
	}
	
	public static AbstractFighter flash(ArenaMediator arena) {
		return hero(arena, "Flash", Heroes.FLASH);
	}
	
	public static AbstractFighter batman(ArenaMediator arena) {
		AbstractFighter b = hero(arena, "Batman", Heroes.BATMAN);
		fightersAvailable.put(Heroes.EQUIPPED_BATMAN, false);
		return b;
	}
	
	public static AbstractFighter equippedBatman(ArenaMediator arena) {
		AbstractFighter b = hero(arena, "Batman", Heroes.EQUIPPED_BATMAN);
		fightersAvailable.put(Heroes.BATMAN, false);
		return b;
	}
	
	private static AbstractFighter hero(ArenaMediator arena, String name, String subtype) {
		validate(subtype);
		return (AbstractFighter) FighterFactory
				.getFactory(Heroes.FACTORY)
				.getFighter(subtype, arena, name);
	}
	
	public static AbstractFighter elemental(ArenaMediator arena, String name) {
		return mage(arena, name, Mages.ELEMENTAL);
	}
	
	public static AbstractFighter dark(ArenaMediator arena, String name) {
		return mage(arena, name, Mages.DARK);
	}
	
	private static AbstractFighter mage(ArenaMediator arena, String name, String subtype) {
		validate(subtype);
		return (AbstractFighter) FighterFactory
				.getFactory(Mages.FACTORY)
				.getFighter(subtype, arena, name);
	}
	
	private static boolean validate(String subtype) {
		if (!fightersAvailable.get(subtype))
			throw new IllegalArgumentException("Fighter "+subtype+" not available!");
		if (uniqueFighters.contains(subtype))
			fightersAvailable.put(subtype, false);
		return true;
	}

}
