package practice.premjit.patterns.kombatsim.arenas;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.beats.TikTok;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;

/**
 * This arena has a mind of its own. At regular intervals it takes snapshots of the life of both the fighters. And at 
 * certain intervals chooses one of the fighter and restores his life to the highest life from its snapshots. 
 * <p>
 * This is based on Memento Pattern.
 * 
 * @author Premjit Adhikary
 *
 */
public class MysticalForest extends AbstractArena {
	static final int CREATE_MEMENTO_BEAT_INTERVAL = 20;
	int currentBeat;
	Deque<Integer> beatsToRestoreStack;
	PriorityQueue<AttributeMemento> championLives;
	PriorityQueue<AttributeMemento> challengerLives;

	public MysticalForest() {
		super(ArenaFactory.MYSTICAL_FOREST);

		beatsToRestoreStack = new LinkedList<>();
		beatsToRestoreStack.addAll(Arrays.asList(55,85,115,145,175,205,235,265,295,345,395,445,495,595,695,795,895));
		
		Comparator<AttributeMemento> comparator = 
				(AttributeMemento o1, AttributeMemento o2) -> o1.life.compareTo(o2.life);
		championLives = new PriorityQueue<>(comparator.reversed());
		challengerLives = new PriorityQueue<>(comparator.reversed());
		
		tiktok = new TikTok();
	}
	
	@Override
	public void update() {
		super.update();
		if (!tiktok.isRunning())
			return;
		
		currentBeat++;
		if (createMementoBeat())
			createMemento();
		if (restoreMementoBeat()) 
			restoreMemento();
	}
	
	private boolean createMementoBeat() {
		return currentBeat % CREATE_MEMENTO_BEAT_INTERVAL == 0;
	}
	
	private boolean restoreMementoBeat() {
		return !beatsToRestoreStack.isEmpty() && currentBeat == beatsToRestoreStack.peek();
	}
	
	void createMemento() {
		championLives.offer(new AttributeMemento(champion.currentLife()));
		challengerLives.offer(new AttributeMemento(challenger.currentLife()));
	}
	
	void restoreMemento() {
		beatsToRestoreStack.pop();
		boolean restoreChampion = Randomizer.hit(50);
		if (restoreChampion) 
			restoreMemento(champion, championLives, "Champion");
		else
			restoreMemento(challenger, challengerLives, "Challenger");
	}
	
	void restoreMemento(AbstractFighter fighter, PriorityQueue<AttributeMemento> lives, String who) {
		double from = fighter.currentLife();
		double to = lives.poll().life;
		fighter.getAttribute(AttributeType.LIFE).ifPresent(life -> {
			((VariableAttribute) life).incrementCurrent(to - from);
			logRestoration(who, from, to);
		});
	}
	
	private void logRestoration(String who, double from, double to) {
		KombatLogger.getLogger().log(
				KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.INFO, 
				this.mapify(),
				KombatLogger.mapBuilder()
					.withName("Life Restored")
					.with("For", who)
					.with("From", Double.toString(from))
					.with("To", Double.toString(to))
					.build());
	}
	
	static class AttributeMemento {
		Double life;
		
		AttributeMemento(double value) {
			life = value;
		}
		
	}

}
