package practice.premjit.patterns.kombatsim.fights;

import java.util.Scanner;

import practice.premjit.patterns.kombatsim.arenas.ArenaFactory;
import practice.premjit.patterns.kombatsim.arenas.Ring;
import practice.premjit.patterns.kombatsim.common.Initializer;
import practice.premjit.patterns.kombatsim.fighters.Professional;

public class ProfessionalsInRing {
	
	static Ring arena;
	static Professional champion;
	static Professional challenger;
	
	private static void init(int choice) {
		//KombatLogger.setPreferredLogger(new Commentator());
		
		arena = (Ring) ArenaFactory.getInstance().getArena(ArenaFactory.RING);
		switch(choice) {
		case 1:
			champion = (Professional) FighterPool.boxer(arena, "Ippo");
			challenger = (Professional) FighterPool.karateka(arena, "Kenichi");
			break;
		case 2:
			champion = (Professional) FighterPool.karateka(arena, "Kenichi");
			challenger = (Professional) FighterPool.taekwondo(arena, "Kurt");
			break;
		case 3:
			champion = (Professional) FighterPool.taekwondo(arena, "Kurt");
			challenger = (Professional) FighterPool.boxer(arena, "Ippo");
			break;
		case 4:
			champion = (Professional) FighterPool.boxer(arena, "Ippo");
			challenger = (Professional) FighterPool.boxer(arena, "Takenouchi");
			break;
		case 5:
			champion = (Professional) FighterPool.karateka(arena, "Kenichi");
			challenger = (Professional) FighterPool.karateka(arena, "Rock Lee");
			break;
		case 6:
			champion = (Professional) FighterPool.taekwondo(arena, "Kurt");
			challenger = (Professional) FighterPool.taekwondo(arena, "Tae Kwon Dodo");
			break;
		}
		arena.addFighter(champion);
		arena.addFighter(challenger);
	}
	
	private static void fight() {
		arena.fight();
	}
	
	private static int getChoice() {
		System.out.println("Press '1' For Boxer vs Karateka");
		System.out.println("Press '2' For Karateka vs Taekwondo");
		System.out.println("Press '3' For Taekwondo vs Boxer");
		System.out.println("Press '4' For Boxer vs Boxer");
		System.out.println("Press '5' For Karateka vs Karateka");
		System.out.println("Press '6' For Taekwondo vs Taekwondo");
		Scanner sc = new Scanner(System.in);
		while (true) {
			int choice = sc.nextInt();
			if (choice>0 && choice<7) {
				sc.close();
				return choice;
			} else
				System.out.println("Press any from 1 to 6");
		}
	}

	public static void main(String[] args) {
		Initializer.init();
		init(getChoice());
		fight();
	}

}
