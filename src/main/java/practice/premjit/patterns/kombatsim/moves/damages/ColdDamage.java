package practice.premjit.patterns.kombatsim.moves.damages;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Map;
import java.util.function.Consumer;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.beats.BeatObserver;
import practice.premjit.patterns.kombatsim.beats.FlipFlopObserver;
import practice.premjit.patterns.kombatsim.common.FlipFlopable;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.fighters.Amateur;
import practice.premjit.patterns.kombatsim.fighters.Hero;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.fighters.Professional;
import practice.premjit.patterns.kombatsim.visitors.FighterVisitor;
import practice.premjit.patterns.kombatsim.visitors.MoveVisitor;

/**
 * Along with base damage, this slows down the opponent. The slow down affect is
 * based on opponent fighter class.<br>
 * <br>
 * 
 * For example, most fighters are dexterity based, which means their actions are
 * a function of their dexterity. For them their dexterity is lowered for a
 * certain duration (which slows them down). <br>
 * <br>
 * 
 * Mages are an exception as they do not have dexterity and one of the
 * attributes that they depend on to cast spell is enough mana. So for them,
 * their mana pool is diminished for a certain duration. <br>
 * <br>
 * 
 * Visitor pattern {@link ColdDamage.ColdFighterVisitor} is applied to determine
 * whether to affect dexterity or mana for reduction and restoration. <br>
 * <br>
 * 
 * Cold Deterioration can stack over another cold deterioration if the cold
 * damages overlap <br>
 * <br>
 * 
 * @author Premjit Adhikary
 *
 */
public class ColdDamage extends BaseDamage implements FlipFlopable, Cloneable {
    public static final String TYPE = "Cold";
    private static final double MANA_MULTIPLIER = 1.25;
    protected int coldDuration;
    protected double deteriorationPercentage;
    protected AbstractFighter fighter;
    BeatObserver timer;

    private ColdDamage(double damageAmount, int coldDuration, double deteriorationPercentage) {
        super(damageAmount);
        this.coldDuration = coldDuration;
        this.deteriorationPercentage = deteriorationPercentage;
        this.damageType = TYPE;
    }
    
    public double deteriorationPercentage() {
        return this.deteriorationPercentage;
    }
    
    public void incrementDeteriorationPercentageBy(double incr) {
        this.deteriorationPercentage += incr;
    }

    @Override
    public void affect(AbstractFighter fighter) {
        super.affect(fighter);
        this.fighter = fighter;
        timer = new FlipFlopObserver(this.coldDuration, this.fighter, this);
    }

    @Override
    public Map<String, String> mapify() {
        return KombatLogger.mapBuilder()
                .withPartial(super.mapify())
                .with(DURATION, Integer.toString(coldDuration))
                .with(DETERIORATION_PERCENTAGE, Double.toString(deteriorationPercentage))
                .buildPartial();
    }

    @Override
    public void set() {
        fighter.accept(new ColdFighterVisitor(ColdFighterVisitor.FREEZE));
    }

    @Override
    public void reset() {
        fighter.accept(new ColdFighterVisitor(ColdFighterVisitor.THAW));
    }

    @Override
    public void accept(MoveVisitor visitor) {
        visitor.visit(this);
    }
    
    private class ColdFighterVisitor implements FighterVisitor {
        static final int FREEZE = -1;
        static final int THAW = 1;
        int multiplier;
        
        ColdFighterVisitor(int m) {
            multiplier = m;
        }

        @Override
        public void visit(AbstractFighter a) {
            // concretes to decide
        }

        @Override
        public void visit(Amateur a) {
            detoriateDexterity();
        }

        @Override
        public void visit(Professional p) {
            detoriateDexterity();
        }

        @Override
        public void visit(Hero h) {
            detoriateDexterity();
        }

        @Override
        public void visit(Mage m) {
            double percentage = deteriorationPercentage * MANA_MULTIPLIER > 80 ? 
                    80 : deteriorationPercentage * MANA_MULTIPLIER;
            fighter.getAttribute(AttributeType.MANA).ifPresent(mana -> {
                double toDetoriate = mana.base() * percentage / 100 * multiplier;
                mana.add(toDetoriate);
            });
        }
        
        private void detoriateDexterity() {
            fighter.getAttribute(AttributeType.DEXTERITY).ifPresent(dex -> {
                double toDetoriate = dex.base() * deteriorationPercentage / 100 * multiplier;
                dex.add(toDetoriate);
            });
        }
        
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public static ColdDamage create(Consumer<ColdDamageBuilder> block) {
        ColdDamageBuilder builder = new ColdDamageBuilder();
        block.accept(builder);
        return builder.build();
    }
    
    public static class ColdDamageBuilder {
        double min;
        double percentage;
        int duration;
        boolean indirect;
        private ColdDamageBuilder() {}
        
        public ColdDamageBuilder min(double m) {
            this.min = m;
            return this;
        }
        
        public ColdDamageBuilder deterioration(double p) {
            this.percentage = p;
            return this;
        }
        
        public ColdDamageBuilder duration(int d) {
            this.duration = d;
            return this;
        }
        
        public ColdDamageBuilder indirect() {
            this.indirect = true;
            return this;
        }
        
        private ColdDamage build() {
            ColdDamage d = new ColdDamage(Randomizer.randomDoubleInRange(min, min+5), duration, percentage);
            if (this.indirect) d.setIndirect();
            return d;
        }
    }

}
