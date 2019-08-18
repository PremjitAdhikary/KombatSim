package practice.premjit.patterns.kombatsim.fighters.decorators;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.commands.ActionCommand;
import practice.premjit.patterns.kombatsim.common.Identifiable;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.common.logging.Loggable;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.strategies.ActionStrategy;
import practice.premjit.patterns.kombatsim.strategies.physical.BasicActionStrategy;

/**
 * Weapon has the following characteristics: <p><ul>
 * <li>Adds one or more additional actions of type {@link WeaponCommand}.
 * <li>The said actions can be used in conjunction with original set of actions or the original set can be ignored. 
 * This can be set with {@link #replaceActions()}
 * <li>Has custom {@link ActionStrategy} implementation {@link WeaponActionStrategy}.
 * <li>If replaceActions is true {@link WeaponActionStrategy} chooses among the WeaponCommand. If not, there's a 
 * chance that the original ActionStrategy is invoked for the original ActionCommands
 * </ul>
 * 
 * @author Premjit Adhikary
 *
 */
public class Weapon extends AbstractFighterDecorator {
	private String weaponName;
	private boolean replaceActions;

	public Weapon(AbstractFighter fighter, String weaponName) {
		super(fighter);
		this.weaponName = weaponName;
	}
	
	public void replaceActions() {
		replaceActions = true;
	}
	
	public String weaponName() {
		return weaponName;
	}
	
	public boolean areOriginalActionsReplaced() {
		return replaceActions;
	}
	
	@Override
	public void addAction(ActionCommand action) {
		if (action instanceof WeaponCommand)
			allActions.add(action);
		else
			theFighter.addAction(action);
	}
	
	@Override
	public List<ActionCommand> allActions() {
		return Collections.unmodifiableList(allActions);
	}

	@Override
	public void setActionStrategy(ActionStrategy actionStrategy) {
		this.actionStrategy = actionStrategy;
	}

	@Override
	public void equip() {
		if (isEquipped())
			return;
		equipped();
		this.actionStrategy = new WeaponActionStrategy(this);
	}
	
	public void buildAndAddWeaponCommand(String commandName, Function<AbstractFighter, Move> moveFunction) {
		WeaponCommand action = new WeaponCommand(commandName, moveFunction);
		addAction(action);
	}
	
	class WeaponActionStrategy extends BasicActionStrategy {
		private ActionStrategy originalStrategy;
		
		public WeaponActionStrategy(AbstractFighter fighter) {
			super(fighter);
			originalStrategy = fighter.getActionStrategy();
			theFighter.setActionStrategy(this);
		}

		@Override
		public void perform() {
			if (!replaceActions && Randomizer.hit(50)) {
				originalStrategy.perform();
				return;
			}
			
			super.perform();
		}
		
	}
	
	class WeaponCommand implements ActionCommand, Identifiable {
		private String actionName;
		private int id;
		private Function<AbstractFighter, Move> moveFunction;

		WeaponCommand(String name, Function<AbstractFighter, Move> function) {
			this.actionName = name;
			this.moveFunction = function;
			this.id = Randomizer.generateId();
		}

		@Override
		public String name() {
			return actionName;
		}

		@Override
		public int id() {
			return id;
		}

		@Override
		public boolean canBeExecuted() {
			if (!theFighter.getPrimaryAttribute().isPresent())
				return false;
			Attribute attr = theFighter.getPrimaryAttribute().get();
			if (!AttributeUtility.isVariableAttribute(attr.type()))
				return true;
			return ((VariableAttribute) attr).current() > 0.3;
		}

		@Override
		public void execute() {
			Move move = calculateMove();
			executionCost();
			sendMove(move);
		}
		
		protected Move calculateMove() {
			return moveFunction.apply(theFighter);
		}
		
		private void executionCost() {
			theFighter.getPrimaryAttribute().ifPresent(attr -> {
				if (AttributeUtility.isVariableAttribute(attr.type()))
					((VariableAttribute) attr).incrementCurrent(-(attr.base() * 0.25));
			});
		}
		
		private void sendMove(Move move) {
			KombatLogger.getLogger().log(
					KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.ACTION, theFighter.mapify(), mapify(move));
			theFighter.arena().sendMove(move, Recipient.OPPONENT, theFighter);
		}
		
		public Map<String, String> mapify(Move move) {
			try {
				return KombatLogger.mapBuilder()
						.withName(actionName)
						.with("Weapon", weaponName)
						.with(((Loggable) move).mapify())
						.build();
			} catch (Exception e) {
				return null;
			}
		}
		
	}

}
