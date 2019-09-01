package practice.premjit.patterns.kombatsim.beats;

import java.util.ArrayList;
import java.util.List;

/**
 * Minimalist implementation of BeatObservable.
 * 
 * @author Premjit Adhikary
 *
 */
public class BeatObservableImpl implements BeatObservable {
	List<BeatObserver> observers;
	
	public BeatObservableImpl() {
		observers = new ArrayList<>();
	}

	@Override
	public void registerObserver(BeatObserver observer) {
		observers.add(observer);
	}

	@Override
	public void unregisterObserver(BeatObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (BeatObserver observer : observers.toArray(new BeatObserver[0])) {
			observer.update();
		}
	}

}
