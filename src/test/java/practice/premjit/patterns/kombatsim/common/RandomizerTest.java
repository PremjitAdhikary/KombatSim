package practice.premjit.patterns.kombatsim.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class RandomizerTest {

	@Test
	void testInteger_0() {
		assertEquals(0, Randomizer.randomInteger(0));
	}

	@RepeatedTest(10)
	void testInteger_2() {
		int actual = Randomizer.randomInteger(2);
		assertTrue(actual == 0 || actual == 1 || actual == 2);
	}

	@RepeatedTest(10)
	void testInteger_0_1() {
		int actual = Randomizer.randomIntegerInRange(0,1);
		assertTrue(actual == 0 || actual == 1);
	}

	@Test
	void testDouble_0() {
		assertEquals(0, Randomizer.randomDouble(0));
	}

	@RepeatedTest(20)
	void testDouble_0_1() {
		double actual = Randomizer.randomDoubleInRange(0,1);
		assertTrue(actual >= 0 && actual <= 1);
	}

	@RepeatedTest(20)
	void testDouble_10_20() {
		double actual = Randomizer.randomDoubleInRange(10,20);
		assertTrue(actual >= 10 && actual <= 20);
	}

	@Test
	void testHit_0() {
		assertFalse(Randomizer.hit(0));
	}

	@Test
	void testHit_100() {
		assertTrue(Randomizer.hit(100));
	}

}
