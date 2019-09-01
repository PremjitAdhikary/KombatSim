package practice.premjit.patterns.kombatsim.fights;

import java.util.Scanner;

import practice.premjit.patterns.kombatsim.arenas.MysticalForest;
import practice.premjit.patterns.kombatsim.common.Initializer;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;

public class PowerfulAndMystical {
	static MysticalForest forest;
	static AbstractFighter champion;
	static AbstractFighter challenger;
	
	static final String AGHORI = "Aghori";
	static final String GANDALF = "Gandalf";
	
	private static void init(int choice) {
		forest = new MysticalForest();
		
		switch(choice) {
		case 1:
			champion = FighterPool.superman(forest);
			challenger = FighterPool.flash(forest);
			break;
		case 2:
			champion = FighterPool.superman(forest);
			challenger = FighterPool.equippedBatman(forest);
			break;
		case 3:
			champion = FighterPool.flash(forest);
			challenger = FighterPool.equippedBatman(forest);
			break;
		case 4:
			champion = FighterPool.elemental(forest, GANDALF);
			challenger = FighterPool.dark(forest, AGHORI);
			break;
		case 5:
			champion = FighterPool.superman(forest);
			challenger = FighterPool.elemental(forest, GANDALF);
			break;
		case 6:
			champion = FighterPool.superman(forest);
			challenger = FighterPool.dark(forest, AGHORI);
			break;
		case 7:
			champion = FighterPool.flash(forest);
			challenger = FighterPool.elemental(forest, GANDALF);
			break;
		case 8:
			champion = FighterPool.flash(forest);
			challenger = FighterPool.dark(forest, AGHORI);
			break;
		case 9:
			champion = FighterPool.equippedBatman(forest);
			challenger = FighterPool.elemental(forest, GANDALF);
			break;
		case 10:
			champion = FighterPool.equippedBatman(forest);
			challenger = FighterPool.dark(forest, AGHORI);
			break;
		}
		forest.addFighter(champion);
		forest.addFighter(challenger);
	}
	
	private static int getChoice() {
		System.out.println("Press '1' For Superman vs Flash");
		System.out.println("Press '2' For Superman vs Batman");
		System.out.println("Press '3' For Flash vs Batman");
		System.out.println("Press '4' For Elemental vs Dark");
		System.out.println("Press '5' For Superman vs Elemental");
		System.out.println("Press '6' For Superman vs Dark");
		System.out.println("Press '7' For Flash vs Elemental");
		System.out.println("Press '8' For Flash vs Dark");
		System.out.println("Press '9' For Batman vs Elemental");
		System.out.println("Press '10' For Batman vs Dark");
		Scanner sc = new Scanner(System.in);
		while (true) {
			int choice = sc.nextInt();
			if (choice>0 && choice<11) {
				sc.close();
				return choice;
			} else {
				System.out.println("Press any from 1 to 10");
			}
		}
	}
	
	private static void fight() {
		forest.fight();
	}
	
	public static void main(String[] args) {
		Initializer.init();
		init(getChoice());
		fight();
	}

}
