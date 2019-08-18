package practice.premjit.patterns.kombatsim.beats;

import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;

/**
 * This has 2 Observables notifying alternatively (Which is why the Tik and the Tok). <p>
 * 
 * Can be initiated with a specific number of cycles to stop after. <p>
 * 
 * Can be set to run indefinitely, till {@link #stop()} invoked.
 * 
 * @author Premjit Adhikary
 *
 */
public class TikTok {
	BeatObservable tik;
	BeatObservable tok;
	int cycles;
	int currentCycle;
	boolean interrupt;
	
	/**
	 * Sets up to run indefinitely, unless stopped externally
	 */
	public TikTok() {
		this(0);
	}
	
	/**
	 * Sets up to stop after "cycles"
	 * 
	 * @param cycles The number of cycles after which beats will stop automatically
	 */
	public TikTok(int cycles) {
		tik = new BeatObservableImpl();
		tok = new BeatObservableImpl();
		this.cycles = cycles;
		this.interrupt = false;
	}
	
	/**
	 * Adds a BeatObserver for tik beat
	 * @param observer
	 */
	public void addTikObserver(BeatObserver observer) {
		tik.registerObserver(observer);
	}
	
	/**
	 * Removes a BeatObserver for tik beat
	 * @param observer
	 */
	public void removeTikObserver(BeatObserver observer) {
		tik.unregisterObserver(observer);
	}
	
	/**
	 * Adds a BeatObserver for tok beat
	 * @param observer
	 */
	public void addTokObserver(BeatObserver observer) {
		tok.registerObserver(observer);
	}
	
	/**
	 * Removes a BeatObserver for tok beat
	 * @param observer
	 */
	public void removeTokObserver(BeatObserver observer) {
		tok.unregisterObserver(observer);
	}
	
	/**
	 * Starts the beats. If cycle is setup it will stop automatically. <br>
	 * Otherwise, it will run till {@link #stop()} to be called. 
	 */
	public void start() {
		for (; (cycles>0 && currentCycle<cycles) || (cycles==0); currentCycle++) {
			logBeat("Tik");
			tik.notifyObservers();
			if (interrupt) break;
			logBeat("Tok");
			tok.notifyObservers();
			if (interrupt) break;
		}
		this.interrupt = true;
	}
	
	/**
	 * Stops the beats.
	 */
	public void stop() {
		this.interrupt = true;
	}
	
	public int getCurrentBeat() {
		return currentCycle;
	}
	
	/**
	 * Check if TikTok is beating or not
	 * @return true if beating
	 */
	public boolean isRunning() {
		return !this.interrupt;
	}
	
	private void logBeat(String beat) {
		KombatLogger.getLogger().log(KombatLogger.LEVEL.LOW, KombatLogger.EVENT_TYPE.BEAT, 
				KombatLogger.mapWithName("Beat"), 
				KombatLogger.mapBuilder().withName(beat).with("Count", ""+currentCycle).build());
	}

}
