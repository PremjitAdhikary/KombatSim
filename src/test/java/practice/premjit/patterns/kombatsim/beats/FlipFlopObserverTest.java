package practice.premjit.patterns.kombatsim.beats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.common.FlipFlopable;

@ExtendWith(MockitoExtension.class)
class FlipFlopObserverTest {
	BeatObservable beats;
	@Mock FlipFlopable ffAble;
	FlipFlopObserver ffObserver;
	
	@BeforeEach
	void init() {
		beats = new BeatObservableImpl();
		ffObserver = new FlipFlopObserver(5, beats, ffAble);
	}

	@Test
	void testAfterNoBeats() {
		assertEquals(5, ffObserver.duration);
		assertEquals(0, ffObserver.count);
		verify(ffAble, times(1)).set();
		verify(ffAble, never()).reset();
	}

	@Test
	void testAfter1Beat() {
		beats.notifyObservers();
		verify(ffAble, times(1)).set();
		verify(ffAble, never()).reset();
		assertEquals(1, ffObserver.count);
	}

	@Test
	void testAfter5Beats() {
		for (int i=0; i<5; i++)
			beats.notifyObservers();
		verify(ffAble, times(1)).set();
		verify(ffAble, never()).reset();
		assertEquals(5, ffObserver.count);
	}

	@Test
	void testAfter6AndMoreBeats() {
		for (int i=0; i<6; i++)
			beats.notifyObservers();
		verify(ffAble, times(1)).set();
		verify(ffAble, times(1)).reset();
		assertEquals(6, ffObserver.count);

		beats.notifyObservers();
		assertEquals(6, ffObserver.count);
	}

}
