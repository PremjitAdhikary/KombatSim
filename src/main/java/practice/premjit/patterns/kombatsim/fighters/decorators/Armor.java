package practice.premjit.patterns.kombatsim.fighters.decorators;

import java.util.Optional;
import java.util.function.Consumer;

import practice.premjit.patterns.kombatsim.commands.ReactionCommand;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectAttribute;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectVariableAttribute;
import practice.premjit.patterns.kombatsim.moves.buffs.AttributeSteal;
import practice.premjit.patterns.kombatsim.moves.buffs.Buff;
import practice.premjit.patterns.kombatsim.moves.damages.BaseDamage;
import practice.premjit.patterns.kombatsim.moves.damages.ColdDamage;
import practice.premjit.patterns.kombatsim.moves.damages.FireDamage;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;
import practice.premjit.patterns.kombatsim.moves.damages.ShockDamage;
import practice.premjit.patterns.kombatsim.strategies.ReactionStrategy;
import practice.premjit.patterns.kombatsim.strategies.physical.BasicReactionStrategy;
import practice.premjit.patterns.kombatsim.visitors.MoveVisitor;

/**
 * Armor absorbs incoming damage based on {@link #damageReductionMultiplier}.
 * Armor has apis to enable specific damage absorption:
 * <p>
 * <ul>
 * <li>{@link #enablePhysicalDamageReduction()}
 * <li>{@link #enableFireDamageReduction()}
 * <li>{@link #enableColdDamageReduction()}
 * <li>{@link #enableShockDamageReduction()}
 * </ul>
 * 
 * {@link #damageAbsorber} works to absorb the damage on enabled reductions.
 * <p>
 * 
 * @author Premjit Adhikary
 *
 */
public class Armor extends AbstractFighterDecorator {
    private double baseArmorLife;
    private double armorLife;
    private double damageReductionMultiplier;
    private boolean physicalDamageReductionEnabled;
    private boolean fireDamageReductionEnabled;
    private boolean coldDamageReductionEnabled;
    private boolean shockDamageReductionEnabled;
    private ArmorDamageAbsorber damageAbsorber;

    private Armor(AbstractFighter fighter, double armorLife, double damageReductionMultiplier) {
        super(fighter);
        this.baseArmorLife = armorLife;
        this.armorLife = armorLife;
        this.damageReductionMultiplier = damageReductionMultiplier;
    }

    @Override
    public void react(Optional<Move> move) {
        if (!isEquipped())
            move.ifPresent( m -> m.affect(this) );
        reactionStrategy.perform(move);
        theFighter.react(move);
    }

    @Override
    public ReactionStrategy getReactionStrategy() {
        return this.reactionStrategy;
    }

    @Override
    public void setReactionStrategy(ReactionStrategy reactionPerformer) {
        this.reactionStrategy = reactionPerformer;
    }

    @Override
    public void equip() {
        if (isEquipped())
            return;
        equipped();
        this.damageAbsorber = new ArmorDamageAbsorber();
        this.reactionStrategy = new ArmorReactionStrategy(this);
    }

    public double getArmorLife() {
        return armorLife;
    }

    public void reduceArmorLife(double decrease) {
        this.armorLife = (decrease > this.armorLife ? 0 : this.armorLife - decrease);
    }

    public double getdamageReductionMultiplier() {
        return damageReductionMultiplier;
    }
    
    class ArmorReactionStrategy extends BasicReactionStrategy {

        public ArmorReactionStrategy(AbstractFighter fighter) {
            super(fighter);
        }

        @Override
        protected boolean execute(Optional<ReactionCommand> reaction, Optional<Move> move) {
            if (move.isPresent())
                move.get().accept(damageAbsorber);
            return true;
        }
        
    }
    
    /**
     * ArmorDamageAbsorber implements Visitor pattern to determine whether to absorb damage or not
     * 
     * @author Premjit Adhikary
     *
     */
    class ArmorDamageAbsorber implements MoveVisitor {

        @Override
        public void visit(Move m) { }

        @Override
        public void visit(PhysicalDamage p) {
            if (physicalDamageReductionEnabled)
                reduceDamage(p);
        }

        @Override
        public void visit(FireDamage f) {
            if (fireDamageReductionEnabled)
                reduceDamage(f);
        }

        @Override
        public void visit(ColdDamage c) {
            if (coldDamageReductionEnabled)
                reduceDamage(c);
        }

        @Override
        public void visit(ShockDamage s) {
            if (shockDamageReductionEnabled)
                reduceDamage(s);
        }

        @Override
        public void visit(Buff b) { }

        @Override
        public void visit(AffectAttribute aa) { }

        @Override
        public void visit(AffectVariableAttribute ave) { }

        @Override
        public void visit(AttributeSteal as) { }
        
        private void reduceDamage(BaseDamage baseDamage) {
            if (!armorHasLife()) 
                return;
            
            double damageReduction = baseDamage.amount() * getdamageReductionMultiplier();
            reduceArmorLife(damageReduction * 0.5);
            baseDamage.incrementBy(-damageReduction);
            KombatLogger.getLogger().log(
                    KombatLogger.LEVEL.MEDIUM, KombatLogger.EVENT_TYPE.UPDATE, 
                    theFighter.mapify(), 
                    KombatLogger.mapBuilder()
                        .withName("Armor")
                        .with("Damage Reduced", String.valueOf(damageReduction))
                        .with("Armor Base", String.valueOf(baseArmorLife))
                        .with("Armor Current", String.valueOf(armorLife))
                        .build());
        }
        
        private boolean armorHasLife() {
            return getArmorLife() > 0;
        }
        
    }
    
    // For Type safety Builder
    
    public static Armor create(Consumer<ArmorFighterBuilder> block) {
        ArmorBuilder builder = new ArmorBuilder();
        block.accept(builder);
        return builder.build();
    }
    
    public static interface ArmorFighterBuilder {
        ArmorLifeBuilder toProtect(AbstractFighter fighter);
    }
    
    public static interface ArmorLifeBuilder {
        ArmorReductionBuilder armorLife(double life);
    }
    
    public static interface ArmorReductionBuilder {
        ArmorOptionsBuilder damageReductionMultiplier(double reduce);
    }
    
    public static interface ArmorOptionsBuilder {
        ArmorOptionsBuilder enablePhysicalDamageReduction();
        ArmorOptionsBuilder enableFireDamageReduction();
        ArmorOptionsBuilder enableColdDamageReduction();
        ArmorOptionsBuilder enableShockDamageReduction();
    }
    
    static class ArmorBuilder implements ArmorFighterBuilder, ArmorLifeBuilder, 
            ArmorReductionBuilder, ArmorOptionsBuilder {
        private AbstractFighter fighter;
        private double life;
        
        private Armor armor;
        
        private ArmorBuilder() { }

        @Override
        public ArmorLifeBuilder toProtect(AbstractFighter fighter) {
            this.fighter = fighter;
            return this;
        }

        @Override
        public ArmorReductionBuilder armorLife(double life) {
            this.life = life;
            return this;
        }

        @Override
        public ArmorOptionsBuilder damageReductionMultiplier(double reduce) {
            armor = new Armor(this.fighter, this.life, reduce);
            return this;
        }

        @Override
        public ArmorOptionsBuilder enablePhysicalDamageReduction() {
            armor.physicalDamageReductionEnabled = true;
            return this;
        }

        @Override
        public ArmorOptionsBuilder enableFireDamageReduction() {
            armor.fireDamageReductionEnabled = true;
            return this;
        }

        @Override
        public ArmorOptionsBuilder enableColdDamageReduction() {
            armor.coldDamageReductionEnabled = true;
            return this;
        }

        @Override
        public ArmorOptionsBuilder enableShockDamageReduction() {
            armor.shockDamageReductionEnabled = true;
            return this;
        }
        
        private Armor build() {
            if (armor == null)
                throw new IllegalArgumentException("Required properties are not set");
            armor.equip();
            return armor;
        }
    }

}
