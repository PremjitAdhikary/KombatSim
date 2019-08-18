package practice.premjit.patterns.kombatsim.fights;

import java.util.Scanner;

import practice.premjit.patterns.kombatsim.arenas.ArenaFactory;
import practice.premjit.patterns.kombatsim.arenas.Backyard;
import practice.premjit.patterns.kombatsim.common.Initializer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.Amateur;

public class AmateursInBackyard {
	
	static Backyard arena;
	static Amateur champion;
	static Amateur challenger;
	
	private static void init(int choice) {
		KombatLogger.getLogger().setLevel(KombatLogger.LEVEL.HIGH);
		
		arena = (Backyard) ArenaFactory.getInstance().getArena(ArenaFactory.BACKYARD);
		switch(choice) {
		case 1:
			bullyVsNerd();
			break;
		case 2:
			nerdVsCaptain();
			break;
		case 3:
			captainVsBully();
			break;
		}
		arena.addFighter(champion);
		arena.addFighter(challenger);
	}
	
	private static void fight() {
		arena.fight();
	}
	
	private static void bullyVsNerd() {
		champion = (Amateur) FighterPool.bully(arena, "Batul");
		challenger = (Amateur) FighterPool.nerd(arena, "Choshma");
	}
	
	private static void nerdVsCaptain() {
		champion = (Amateur) FighterPool.nerd(arena, "Choshma");
		challenger = (Amateur) FighterPool.captain(arena, "Dada");
	}
	
	private static void captainVsBully() {
		champion = (Amateur) FighterPool.captain(arena, "Dada");
		challenger = (Amateur) FighterPool.bully(arena, "Batul");
	}
	
	private static int getChoice() {
		System.out.println("Press '1' For Bully vs Nerd");
		System.out.println("Press '2' For Nerd vs Captain");
		System.out.println("Press '3' For Captain vs Bully");
		Scanner sc = new Scanner(System.in);
		while (true) {
			int choice = sc.nextInt();
			if (choice>0 && choice<4) {
				sc.close();
				return choice;
			} else
				System.out.println("Press 1 or 2 or 3");
		}
	}

	public static void main(String[] args) {
		Initializer.init();
		init(getChoice());
		fight();
	}

}
