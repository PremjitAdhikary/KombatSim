package practice.premjit.patterns.kombatsim.attributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttributeTest {
	Attribute attr;
	
	@BeforeEach
	public void init() {
		attr = new Attribute(AttributeType.STRENGTH, 100);
	}
	
	@Nested
	@DisplayName("when positive values are added, multiplied")
	class WhenPositive {
	
		@Test
		void testBasic() {
			assertEquals(100, attr.base());
			assertEquals(100, attr.net());
		}
	
		@Test
		void testAdd() {
			attr.add(20);
			assertEquals(100, attr.base());
			assertEquals(120, attr.net());
		}
	
		@Test
		void testMultiply() {
			attr.multiply(0.2);
			assertEquals(100, attr.base());
			assertEquals(120, attr.net());
		}
	
		@Test
		void testAddAndMultiply() {
			attr.add(20);
			attr.multiply(0.5);
			assertEquals(100, attr.base());
			assertEquals(170, attr.net());
		}
	
	}
	
	@Nested
	@DisplayName("when negative values are added, multiplied")
	class WhenNegative {
	
		@Test
		void testAdd() {
			attr.add(-20);
			assertEquals(100, attr.base());
			assertEquals(80, attr.net());
		}
	
		@Test
		void testMultiply() {
			attr.multiply(-0.2);
			assertEquals(100, attr.base());
			assertEquals(80, attr.net());
		}
	
		@Test
		void testAddAndMultiply() {
			attr.add(-20);
			attr.multiply(-0.5);
			assertEquals(100, attr.base());
			assertEquals(30, attr.net());
		}
	
	}
	
	@Nested
	@DisplayName("when high negative values are added, multiplied")
	class WhenHighNegative {
	
		@Test
		void testAdd() {
			attr.add(-120);
			assertEquals(100, attr.base());
			assertEquals(0, attr.net());
		}
	
		@Test
		void testMultiply() {
			attr.multiply(-1.2);
			assertEquals(100, attr.base());
			assertEquals(0, attr.net());
		}
	
		@Test
		void testAddAndMultiply() {
			attr.add(-20);
			attr.multiply(-0.8);
			assertEquals(100, attr.base());
			assertEquals(0, attr.net());
		}
	
		@Test
		void testMuipleAdds() {
			attr.add(-120);
			assertEquals(100, attr.base());
			assertEquals(0, attr.net());
			attr.add(-100);
			assertEquals(100, attr.base());
			assertEquals(0, attr.net());
			attr.add(90);
			assertEquals(100, attr.base());
			assertEquals(0, attr.net());
			attr.add(50);
			assertEquals(100, attr.base());
			assertEquals(20, attr.net());
		}
	
		@Test
		void testMultipleMultiplies() {
			attr.multiply(-1.2);
			assertEquals(100, attr.base());
			assertEquals(0, attr.net());
			attr.multiply(-1.0);
			assertEquals(100, attr.base());
			assertEquals(0, attr.net());
			attr.multiply(0.5);
			assertEquals(100, attr.base());
			assertEquals(0, attr.net());
			attr.multiply(0.7);
			assertEquals(100, attr.base());
			assertEquals(0, attr.net());
			attr.multiply(0.2);
			assertEquals(100, attr.base());
			assertEquals(20, attr.net());
		}
	
	}

}
