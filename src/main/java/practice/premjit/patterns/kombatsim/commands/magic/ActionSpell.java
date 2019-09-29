package practice.premjit.patterns.kombatsim.commands.magic;

import java.util.Map;
import java.util.function.Consumer;
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
 * ActionSpells can be offensive for you opponent or it can give the caster some
 * advantage. It all depends on the type of {@link #moveSupplier} and
 * {@link #recipient}.
 * <p>
 * 
 * As stated earlier, after every cast, the Spell goes through a cool down. It
 * implements the following Apis:
 * <p>
 * <ul>
 * <li>{@link #borrowFromSpellBook()} to invoke
 * {@link SpellBook#borrowSpell(ActionSpell) book.borrowSpell(ActionCommand)}
 * <li>{@link #returnToSpellBook()} to invoke
 * {@link SpellBook#returnSpell(ActionSpell) book.returnSpell(ActionCommand)}
 * </ul>
 * 
 * @author Premjit Adhikary
 *
 */
public class ActionSpell extends AbstractSpell implements ActionCommand {
    protected Recipient recipient;
    protected Supplier<Move> moveSupplier;
    protected Predicate<Mage> customCondition;

    private ActionSpell(Mage mage, String name, SpellBook book, Supplier<Move> moveSupplier, int cooldownDuration) {
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
    
    public Map<String, String> mapify(Move move) {
        try {
            return KombatLogger.mapBuilder()
                    .withName(name)
                    .withPartial(((Loggable) move).mapify())
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
    
    // For Type safety Builder
    
    public static ActionSpell create(Consumer<ActionSpellMageBuilder> block) {
        ActionSpellBuilder builder = new ActionSpellBuilder();
        block.accept(builder);
        return builder.build();
    }
    
    public interface ActionSpellMageBuilder {
        ActionSpellNameBuilder mage(Mage mage);
    }

    public interface ActionSpellNameBuilder {
        ActionSpellBookBuilder name(String name);
    }

    public interface ActionSpellBookBuilder {
        ActionSpellMoveBuilder book(SpellBook book);
    }

    public interface ActionSpellMoveBuilder {
        ActionSpellCooldownBuilder move(Supplier<Move> move);
    }
    
    public interface ActionSpellCooldownBuilder {
        ActionSpellOptionsBuilder cooldown(int cooldown);
    }
    
    public interface ActionSpellOptionsBuilder {
        ActionSpellOptionsBuilder affect(Recipient r);
        ActionSpellOptionsBuilder manaCost(double cost);
        ActionSpellOptionsBuilder executeCondition(Predicate<Mage> condition);
    }
    
    public static class ActionSpellBuilder 
            implements ActionSpellMageBuilder, ActionSpellNameBuilder, ActionSpellBookBuilder, 
            ActionSpellMoveBuilder, ActionSpellCooldownBuilder, ActionSpellOptionsBuilder {
        private Mage fighter;
        private String name;
        private SpellBook book;
        private Supplier<Move> moveSupplier;
        private ActionSpell spell;
        
        private ActionSpellBuilder() { }

        @Override
        public ActionSpellNameBuilder mage(Mage mage) {
            this.fighter = mage;
            return this;
        }

        @Override
        public ActionSpellBookBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public ActionSpellMoveBuilder book(SpellBook book) {
            this.book = book;
            return this;
        }

        @Override
        public ActionSpellCooldownBuilder move(Supplier<Move> move) {
            this.moveSupplier = move;
            return this;
        }

        @Override
        public ActionSpellOptionsBuilder cooldown(int cooldown) {
            this.spell = new ActionSpell(this.fighter, this.name, this.book, this.moveSupplier, cooldown);
            return this;
        }
        
        /**
         * Optional. By Default Recipient.OPPONENT
         * 
         * @param r
         * @return
         */
        @Override
        public ActionSpellOptionsBuilder affect(Recipient r) {
            this.spell.recipient = r;
            return this;
        }
        
        /**
         * Optional. By Default 1
         * 
         * @param cost
         * @return
         */
        @Override
        public ActionSpellOptionsBuilder manaCost(double cost) {
            this.spell.manaCost = cost;
            return this;
        }
        
        /**
         * In case there is an additional condition required to check if the spell can be executed. <br>
         * Optional. By Default: f -> true
         * 
         * @param cost
         * @return
         */
        @Override
        public ActionSpellOptionsBuilder executeCondition(Predicate<Mage> condition) {
            this.spell.customCondition= condition;
            return this;
        }
        
        private ActionSpell build() {
            if (spell == null)
                throw new IllegalArgumentException("Required properties are not set");
            return spell;
        }
        
    }

}
