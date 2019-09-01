package practice.premjit.patterns.kombatsim.beats;

public interface BeatObservable {
	
	void registerObserver(BeatObserver observer);
	
	void unregisterObserver(BeatObserver observer);
	
	void notifyObservers();

}
