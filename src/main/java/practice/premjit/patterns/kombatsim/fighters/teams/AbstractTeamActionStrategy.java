package practice.premjit.patterns.kombatsim.fighters.teams;

import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.strategies.ActionStrategy;

public abstract class AbstractTeamActionStrategy implements ActionStrategy {
    protected AbstractFighter fighter;
    protected ActionStrategy originalStrategy;
    
    public AbstractTeamActionStrategy(AbstractFighter fighter) {
        this.fighter = fighter;
        this.originalStrategy = fighter.getActionStrategy();
    }

    @Override
    public void perform() {
        if (originalStrategyEnabled()) {
            originalStrategy.perform();
        }
    }
    
    protected abstract boolean originalStrategyEnabled();

}
