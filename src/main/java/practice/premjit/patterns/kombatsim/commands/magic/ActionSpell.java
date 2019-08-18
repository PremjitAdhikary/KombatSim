package practice.premjit.patterns.kombatsim.commands.magic;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.commands.ActionCommand;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.common.logging.Loggable;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.strategies.magic.SpellBook;

/**
 * ActionSpells can be offensive for you opponent or it can give the caster some advantage. It all depends on the type 
 * of {@link #moveSupplier} and {@link #recipient}. <p>
 * 
 * As stated earlier, after every cast, the Spell goes through a cool down. It implements the following Apis:<p><ul>
 * <li>{@link #borrowFromSpellBook()} to invoke {@link SpellBook#borrowSpell(ActionSpell) 
 * book.borrowSpell(ActionCommand)}
 * <li>{@link #returnToSpellBook()} to invoke {@link SpellBook#returnSpell(ActionSpell) 
 * book.returnSpell(ActionCommand)}
 * </ul>
 * 
 * @author Premjit Adhikary
 *
 */
public class ActionSpell extends AbstractSpell implements ActionCommand {
	protected Recipient recipient;
	protected Supplier<Move> moveSupplier;
	protected Predicate<Mage> customCondition;

	public ActionSpell(Mage mage, String name, SpellBook book, Supplier<Move> moveSupplier, int cooldownDuration) {
		super(mage, cooldownDuration, book);
		this.name = name;
		this.moveSupplier = moveSupplier;
		recipient = Recipient.OPPONENT;
		startCooldown(); // by default spell not ready
		customCondition = f -> true; // by default return true
	}

	@Override
	public boolean canBeExecuted() {
		return isReady() && mageHasMana() && customCondition.test(this.mage);
	}

	@Override
	public void execute() {
		chargeMana();
		startCooldown();
		sendMove();
	}
	
	public void setRecipient(Recipient r) {
		this.recipient = r;
	}

	public void setCustomCondition(Predicate<Mage> customCondition) {
		this.customCondition = customCondition;
	}
	
	public Map<String, String> mapify(Move move) {
		try {
			return KombatLogger.mapBuilder()
					.withName(name)
					.with(((Loggable) move).mapify())
					.build();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected void borrowFromSpellBook() {
		book.borrowSpell(this);
	}

	@Override
	protected void returnToSpellBook() {
		book.returnSpell(this);
	}

	private void chargeMana() {
		mage.getAttribute(AttributeType.MANA).ifPresent(
				mana -> ((VariableAttribute) mana).incrementCurrent(-manaCost));
	}

	private void sendMove() {
		Move move = moveSupplier.get();
		KombatLogger.getLogger().log(
				KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.ACTION, mage.mapify(), mapify(move));
		mage.arena().sendMove(move, recipient, mage);
	}

}
