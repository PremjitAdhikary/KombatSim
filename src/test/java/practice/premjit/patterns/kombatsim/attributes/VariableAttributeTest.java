package practice.premjit.patterns.kombatsim.attributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class VariableAttributeTest {
	VariableAttribute attr;
	
	@BeforeEach
	public void init() {
		attr = new VariableAttribute(AttributeType.LIFE, 100);
	}
	
	@Nested
	@DisplayName("when positive values are added, multiplied")
	class WhenPositive {
	
		@Test
		void testBasic() {
			assertEquals(100, attr.base(), .0001);
			assertEquals(100, attr.current(), .0001);
		}
	
		@Test
		void testIncrement() {
			attr.incrementCurrent(20);
			assertEquals(100, attr.base(), .0001);
			assertEquals(100, attr.current(), .0001);
			attr.incrementCurrent(-20);
			assertEquals(100, attr.base(), .0001);
			assertEquals(80, attr.current(), .0001);
		}
	
		@Test
		void testAdd() {
			attr.add(20);
			assertEquals(100, attr.base(), .0001);
			assertEquals(120, attr.current(), .0001);
		}
	
		@Test
		void testMultiply() {
			attr.multiply(0.2);
			assertEquals(100, attr.base(), .0001);
			assertEquals(120, attr.current(), .0001);
		}
	
		@Test
		void testAddAndMultiply() {
			attr.add(20);
			attr.multiply(0.5);
			assertEquals(100, attr.base(), .0001);
			assertEquals(170, attr.current(), .0001);
		}
	
	}
	
	@Nested
	@DisplayName("when negative values are added, multiplied")
	class WhenNegative {
	
		@Test
		void testAdd() {
			attr.add(-20);
			assertEquals(100, attr.base(), .0001);
			assertEquals(80, attr.current(), .0001);
		}
	
		@Test
		void testMultiply() {
			attr.multiply(-0.2);
			assertEquals(100, attr.base(), .0001);
			assertEquals(80, attr.current(), .0001);
		}
	
		@Test
		void testAddAndMultiply() {
			attr.add(-20);
			attr.multiply(-0.5);
			assertEquals(100, attr.base(), .0001);
			assertEquals(30, attr.current(), .0001);
		}
	
	}
	
	@Nested
	@DisplayName("other cases")
	class AdditionalCases {
	
		@Test
		void testPositiveAddCase1() {
			attr.incrementCurrent(-20);
			attr.add(10);
			assertEquals(100, attr.base(), .0001);
			assertEquals(110, attr.net(), .0001);
			assertEquals(90, attr.current(), .0001);
		}
	
		@Test
		void testPositiveAddCase2() {
			attr.incrementCurrent(-10);
			attr.add(20);
			assertEquals(100, attr.base(), .0001);
			assertEquals(120, attr.net(), .0001);
			assertEquals(110, attr.current(), .0001);
		}
	
		@Test
		void testNegativeAddCase1() {
			attr.incrementCurrent(-20);
			attr.add(-10);
			assertEquals(100, attr.base(), .0001);
			assertEquals(90, attr.net(), .0001);
			assertEquals(70, attr.current(), .0001);
		}
	
		@Test
		void testNegativeAddCase2() {
			attr.incrementCurrent(-10);
			attr.add(-20);
			assertEquals(100, attr.base(), .0001);
			assertEquals(80, attr.net(), .0001);
			assertEquals(70, attr.current(), .0001);
		}
	
		@Test
		void testNegativeAddCase3() {
			attr.incrementCurrent(-90);
			attr.add(-20);
			assertEquals(100, attr.base(), .0001);
			assertEquals(80, attr.net(), .0001);
			assertEquals(0, attr.current(), .0001);
		}
	
		@Test
		void testPositiveMultiplyCase1() {
			attr.incrementCurrent(-20);
			attr.multiply(.1);
			assertEquals(100, attr.base(), .0001);
			assertEquals(110, attr.net(), .0001);
			assertEquals(90, attr.current(), .0001);
		}
	
		@Test
		void testPositiveMultiplyCase2() {
			attr.incrementCurrent(-10);
			attr.multiply(.2);
			assertEquals(100, attr.base(), .0001);
			assertEquals(120, attr.net(), .0001);
			assertEquals(110, attr.current(), .0001);
		}
	
		@Test
		void testNegativeMultiplyCase1() {
			attr.incrementCurrent(-20);
			attr.multiply(-.1);
			assertEquals(100, attr.base(), .0001);
			assertEquals(90, attr.net(), .0001);
			assertEquals(70, attr.current(), .0001);
		}
	
		@Test
		void testNegativeMultiplyCase2() {
			attr.incrementCurrent(-10);
			attr.multiply(-.2);
			assertEquals(100, attr.base(), .0001);
			assertEquals(80, attr.net(), .0001);
			assertEquals(70, attr.current(), .0001);
		}
	
		@Test
		void testNegativeMultiplyCase3() {
			attr.incrementCurrent(-90);
			attr.multiply(-.2);
			assertEquals(100, attr.base(), .0001);
			assertEquals(80, attr.net(), .0001);
			assertEquals(0, attr.current(), .0001);
		}
		
	}

}
