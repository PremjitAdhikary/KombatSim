package practice.premjit.patterns.kombatsim.commands.hero;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.commands.AllActions;
import practice.premjit.patterns.kombatsim.fighters.Hero;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectAttribute;
import practice.premjit.patterns.kombatsim.moves.damages.ColdDamage;
import practice.premjit.patterns.kombatsim.moves.damages.FireDamage;
import practice.premjit.patterns.kombatsim.moves.damages.ShockDamage;

public final class MojoActions {
	private static final Map<String, Function<Hero, MojoBasedAction>> actionMap = new HashMap<>();
	
	// flash powers
	
	private static Function<Hero, MojoBasedAction> haste = hero -> 
		MojoBasedAction.create( action -> 
			action
				.fighter(hero)
				.name(AllActions.HASTE.value())
				.move( () -> 
					AffectAttribute.create( buff -> 
						buff.affectAttribute(AttributeType.DEXTERITY).duration(15).multiplicand(3)
					)
				)
				.affect(Recipient.SELF)
				.mojoCost(95)
		);
	
	private static Function<Hero, MojoBasedAction> arcLightning = hero -> 
		MojoBasedAction.create( action -> 
			action
				.fighter(hero)
				.name(AllActions.ARC_LIGHTNING.value())
				.move( () -> ShockDamage.create( shock -> shock.max(50).duration(25) ) )
				.mojoCost(90)
		);
	
	// superman powers
		
	private static Function<Hero, MojoBasedAction> heatVision = hero -> 
		MojoBasedAction.create( action -> 
			action
				.fighter(hero)
				.name(AllActions.HEAT_VISION.value())
				.move( () -> 
					FireDamage.create( fire -> 
						fire.min(100).max(100).damage(20).duration(4) 
					) 
				)
				.mojoCost(160)
		);
		
	private static Function<Hero, MojoBasedAction> freezeBreath = hero -> 
		MojoBasedAction.create( action -> 
			action
				.fighter(hero)
				.name(AllActions.FREEZE_BREATH.value())
				.move( () -> ColdDamage.create( cold -> cold.min(15).deterioration(20).duration(45) ) )
				.mojoCost(150)
		);
	
	static {
		actionMap.put(AllActions.HASTE.value(), haste);
		actionMap.put(AllActions.ARC_LIGHTNING.value(), arcLightning);
		actionMap.put(AllActions.HEAT_VISION.value(), heatVision);
		actionMap.put(AllActions.FREEZE_BREATH.value(), freezeBreath);
	}
	
	private MojoActions() { }
	
	public static MojoBasedAction getAction(AllActions action, Hero hero) {
		if (actionMap.containsKey(action.value())) {
			return actionMap.get(action.value()).apply(hero);
		}
		throw new IllegalArgumentException("Invalid Mojo Action: "+action.value());
	}

}
