package practice.premjit.patterns.kombatsim.arenas;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import practice.premjit.patterns.kombatsim.beats.TikTok;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;

/**
 * This is a special arena which has a restriction of time. The time can be set, if not, it has default beats. <p>
 * 
 * If by the time the fight ends, both the fighters are standing, winner is decided by who has greater percentage of 
 * life remaining.
 * 
 * @author Premjit Adhikary
 *
 */
public class Ring extends AbstractArena {
	public static final int DEFAULT_BEATS = 200;
	private boolean fightOverByKO;
	
	public Ring() {
		this(DEFAULT_BEATS);
	}

	public Ring(int duration) {
		super(ArenaFactory.RING);
		tiktok = new TikTok(duration);
	}
	
	@Override
	public void fight() {
		super.fight();
		if (!fightOverByKO)
			declareResult();
	}
	
	@Override
	public void update() {
		super.update();
		if (!tiktok.isRunning())
			fightOverByKO = true;
	}
	
	private void declareResult() {
		double championLife = champion.currentLife() / champion.maxLife();
		double challengerLife = challenger.currentLife() / challenger.maxLife();
		boolean draw = championLife == challengerLife;

		logFightEnd(draw ? DRAW : WIN, TIME_OUT);
		logFighterResult(champion, draw ? DRAW : (championLife > challengerLife ? WON : LOST), CHAMPION);
		logFighterResult(challenger, draw ? DRAW : (challengerLife > championLife ? WON : LOST), CHALLENGER);
		
		KombatLogger.getLogger().stop();
	}

}
