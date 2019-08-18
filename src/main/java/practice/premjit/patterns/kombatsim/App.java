package practice.premjit.patterns.kombatsim;

import static practice.premjit.patterns.kombatsim.arenas.ArenaFactory.*;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Scanner;

import practice.premjit.patterns.kombatsim.arenas.AbstractArena;
import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.common.Initializer;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.fighters.factory.AmateurFighters;
import practice.premjit.patterns.kombatsim.fighters.factory.EnhancedFighters;
import practice.premjit.patterns.kombatsim.fighters.factory.Heroes;
import practice.premjit.patterns.kombatsim.fighters.factory.Mages;
import practice.premjit.patterns.kombatsim.fighters.factory.ProfessionalFighters;
import practice.premjit.patterns.kombatsim.fights.FighterPool;

/**
 * Interactive
 *
 */
public class App {
	private static final Map<String, String> fighterKey = new LinkedHashMap<>();
	static ArenaMediator arena;
	static AbstractFighter champion;
	static AbstractFighter challenger;
	private static Scanner sc;
	
	static {
		fighterKey.put("ab", AmateurFighters.BULLY);
		fighterKey.put("ac", AmateurFighters.CAPTAIN);
		fighterKey.put("an", AmateurFighters.NERD);
		
		fighterKey.put("pb", ProfessionalFighters.BOXER);
		fighterKey.put("pk", ProfessionalFighters.KARATEKA);
		fighterKey.put("pt", ProfessionalFighters.TAEKWONDO);
		
		fighterKey.put("eb", EnhancedFighters.EQUIPED_BOXER);
		fighterKey.put("es", EnhancedFighters.SAMURAI);
		fighterKey.put("en", EnhancedFighters.NINJA);
        
		fighterKey.put("hs", Heroes.SUPERMAN);
		fighterKey.put("hf", Heroes.FLASH);
		fighterKey.put("hb", Heroes.BATMAN);
		fighterKey.put("he", Heroes.EQUIPPED_BATMAN);
        
		fighterKey.put("me", Mages.ELEMENTAL);
		fighterKey.put("md", Mages.DARK);
	}
	
    public static void main( String[] args )
    {
		Initializer.init();
        getInputs();
        setupAndFight();
    }
    
    private static void getInputs() {
		sc = new Scanner(System.in);
		arena = getInstance().getArena(BACKYARD);
		champion = selectFighter();
		challenger = selectFighter();
		sc.close();
    }
    
    private static void setupAndFight() {
    	arena.addFighter(champion);
    	arena.addFighter(challenger);
    	((AbstractArena) arena).fight();
    }
    
    private static AbstractFighter selectFighter() {
    	listAvailableFighters();
    	while (true) {
    		System.out.println(">");
			String f = sc.nextLine();
			if (fighterKey.containsKey(f)) {
				System.out.println("Selected fighter: "+fighterKey.get(f));
				return FighterPool.get(arena, fighterKey.get(f), fighterKey.get(f));
			}
			System.out.println("Enter correct key!!");
    	}
    }
    
    private static void listAvailableFighters() {
    	System.out.println("Select Fighter:");
    	int i=0;
    	for (Map.Entry<String, String> e : fighterKey.entrySet()) {
    		if (!FighterPool.availableFighters().contains(e.getValue()))
    			continue;
			if (i%3==0) System.out.println();
			String name = fighterKey.get(e.getKey());
			System.out.print(" ("+e.getKey()+") "+String.format("%1$-20s", name));
			i++;
    	}
    	System.out.println();
    }
}
