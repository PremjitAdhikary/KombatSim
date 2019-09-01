package practice.premjit.patterns.kombatsim.fights;

import java.util.Scanner;

import practice.premjit.patterns.kombatsim.arenas.Backyard;
import practice.premjit.patterns.kombatsim.common.Initializer;
import practice.premjit.patterns.kombatsim.fighters.teams.AbstractTeam;
import practice.premjit.patterns.kombatsim.fighters.teams.OffenderDefenderTeam;
import practice.premjit.patterns.kombatsim.fighters.teams.TagTeam;

public class ClashOfTeams {
	static Backyard arena;
	static AbstractTeam teamA;
	static AbstractTeam teamB;
	
	private static void init(int choice) {
		arena = new Backyard();
		
		switch(choice) {
		case 1:
			teamA = new OffenderDefenderTeam(arena);
			((OffenderDefenderTeam) teamA).addOffender(FighterPool.karateka(arena, "Kenichi"));
			((OffenderDefenderTeam) teamA).addDefender(FighterPool.boxer(arena, "Ippo"));
			teamB = new OffenderDefenderTeam(arena);
			((OffenderDefenderTeam) teamB).addOffender(FighterPool.taekwondo(arena, "Frank"));
			((OffenderDefenderTeam) teamB).addDefender(FighterPool.karateka(arena, "Damme"));
			break;
		case 2:
			teamA = new TagTeam(arena);
			((TagTeam) teamA).addTeammate(FighterPool.boxer(arena, "Ippo"));
			((TagTeam) teamA).addTeammate(FighterPool.karateka(arena, "Kenichi"));
			((TagTeam) teamA).addTeammate(FighterPool.taekwondo(arena, "Lee"));
			teamB = new TagTeam(arena);
			((TagTeam) teamB).addTeammate(FighterPool.taekwondo(arena, "Frank"));
			((TagTeam) teamB).addTeammate(FighterPool.karateka(arena, "Chuck"));
			((TagTeam) teamB).addTeammate(FighterPool.boxer(arena, "Tyson"));
			break;
		case 3:
			teamA = new OffenderDefenderTeam(arena);
			((OffenderDefenderTeam) teamA).addOffender(FighterPool.samurai(arena, "Yoshimitsu"));
			((OffenderDefenderTeam) teamA).addDefender(FighterPool.ninja(arena, "Naruto"));
			teamB = new TagTeam(arena);
			((TagTeam) teamB).addTeammate(FighterPool.taekwondo(arena, "Frank"));
			((TagTeam) teamB).addTeammate(FighterPool.karateka(arena, "Chuck"));
			((TagTeam) teamB).addTeammate(FighterPool.boxer(arena, "Tyson"));
			break;
		}
		arena.addFighter(teamA);
		arena.addFighter(teamB);
	}
	
	private static int getChoice() {
		System.out.println("Press '1' For Offender Defender Fight");
		System.out.println("Press '2' For Tag Team Fight");
		System.out.println("Press '3' For OD vs Tag Team Fight");
		Scanner sc = new Scanner(System.in);
		while (true) {
			int choice = sc.nextInt();
			if (choice>0 && choice<11) {
				sc.close();
				return choice;
			} else
				System.out.println("Press any from 1 to 2");
		}
	}
	
	private static void fight() {
		arena.fight();
	}

	public static void main(String[] args) {
		Initializer.init();
		init(getChoice());
		fight();
	}

}
