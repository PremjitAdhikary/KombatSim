package practice.premjit.patterns.kombatsim.beats;

import practice.premjit.patterns.kombatsim.fighters.Mage;

/**
 * Nothing special, just informs the mage of every beat. The actual rhythm for
 * mages to perform action is determined by
 * {@link practice.premjit.patterns.kombatsim.strategies.magic.SpellBook
 * SpellBook}.
 * 
 * @author Premjit Adhikary
 *
 */
public class MageActionObserver implements BeatObserver {
    protected Mage mage;

    @Override
    public void update() {
        mage.act();
    }

    public void setMage(Mage mage) {
        this.mage = mage;
    }

}
