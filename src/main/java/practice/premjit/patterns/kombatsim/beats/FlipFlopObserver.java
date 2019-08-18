package practice.premjit.patterns.kombatsim.beats;

import practice.premjit.patterns.kombatsim.common.FlipFlopable;

/**
 * A simple switch which sets a {@link FlipFlopable} at start and then resets it after the configured duration.
 * 
 * @author Premjit Adhikary
 *
 */
public class FlipFlopObserver implements BeatObserver {
	int duration;
	int count;
	BeatObservable observable;
	FlipFlopable flipFlop;

	public FlipFlopObserver(int duration, BeatObservable observable, FlipFlopable flipFlop) {
		this.duration = duration;
		this.flipFlop = flipFlop;
		this.observable = observable;
		this.observable.registerObserver(this);
		flipFlop.set();
	}

	@Override
	public void update() {
		if (count > duration)
			return;
		
		if (count == duration) {
			flipFlop.reset();
			observable.unregisterObserver(this);
		}
		count++;
	}

}
