package practice.premjit.patterns.kombatsim.commands.hero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.commands.physical.AbstractPhysicalReactionCommand;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;

@ExtendWith(MockitoExtension.class)
class EndureTest {
	@Mock AbstractFighter mockFighter;
	@Mock AbstractPhysicalReactionCommand reaction;
	Endure endure;
	PhysicalDamage damage;
	
	@BeforeEach
	void init() {
		KombatLogger.getLogger().disableLogging();
		damage = PhysicalDamage.create(d -> d.min(20).max(20));
		endure = new Endure(mockFighter, false);
	}
	
	@Test
	@DisplayName("when no damage")
	void testWhenNoDamage() {
		assertFalse(endure.canBeExecuted(Optional.empty()));
	}
	
	@Test
	@DisplayName("when fighter with no mojo")
	void testWhenFighterWithNoMojo() {
		when(mockFighter.getAttribute(AttributeType.MOJO)).thenReturn(Optional.empty());
		assertFalse(endure.canBeExecuted(Optional.of(damage)));
	}
	
	@TestInstance(Lifecycle.PER_CLASS)
	@Nested
	@DisplayName("when fighter with mojo")
	class WhenFighterWithMojo {
		VariableAttribute mojo;
		VariableAttribute life;
		Endure spyEndure;
		
		@BeforeEach
		void init() {
			mojo = AttributeUtility.buildMojo(100, 90);
			spyEndure = spy(endure);
			lenient().when(mockFighter.getAttribute(AttributeType.MOJO)).thenReturn(Optional.of(mojo));
			doReturn(reaction).when(spyEndure).command();
		}
		
		@Test
		void testCanBeExecuted() {
			when(reaction.canBeExecuted(any(Optional.class))).thenReturn(true);
			assertTrue(spyEndure.canBeExecuted(Optional.of(damage)));
		}

		@ParameterizedTest
		@MethodSource("provideArgumentsForTestReduceDamage")
		void testReduceDamage(double reduceLife, double damageAmount, double mojoCurrent) {
			life = AttributeUtility.buildLife(100);
			life.incrementCurrent(-reduceLife);
			
			when(mockFighter.currentLife()).thenReturn(life.current());
			doNothing().when(reaction).reduceDamage(any(PhysicalDamage.class));
			
			spyEndure.reduceDamage(damage);
			
			assertEquals(damageAmount, damage.amount());
			assertEquals(mojoCurrent, mojo.current());
		}
		
		Stream<Arguments> provideArgumentsForTestReduceDamage() {
			return Stream.of(
					Arguments.of(0.0, 20.0, 90.0),
					Arguments.of(50.0, 20.0, 90.0),
					Arguments.of(80.0, 0.0, 70.0)
				);
		}

		@ParameterizedTest
		@MethodSource("provideArgumentsForTestExecute")
		void testExecute(double reduceLife, double lifeCurrent, double mojoCurrent) {
			life = AttributeUtility.buildLife(100);
			life.incrementCurrent(-reduceLife);
			
			lenient().when(mockFighter.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(life));
			when(mockFighter.currentLife()).thenReturn(life.current());
			doNothing().when(reaction).reduceDamage(any(PhysicalDamage.class));
			
			spyEndure.execute(Optional.of(damage));
			
			assertEquals(lifeCurrent, life.current());
			assertEquals(mojoCurrent, mojo.current());
		}
		
		Stream<Arguments> provideArgumentsForTestExecute() {
			return Stream.of(
					Arguments.of(0.0, 80.0, 90.0),
					Arguments.of(50.0, 30.0, 90.0),
					Arguments.of(80.0, 20.0, 70.0)
				);
		}
		
	}

}
