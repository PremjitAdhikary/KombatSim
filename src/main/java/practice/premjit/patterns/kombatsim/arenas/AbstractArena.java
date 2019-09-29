package practice.premjit.patterns.kombatsim.arenas;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Map;
import java.util.Optional;

import practice.premjit.patterns.kombatsim.beats.BeatObserver;
import practice.premjit.patterns.kombatsim.beats.TikTok;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.common.logging.Loggable;
import practice.premjit.patterns.kombatsim.common.monitor.MemoryMonitor;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.fighters.Fighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.visitors.MoveCloner;

/**
 * Implements ArenaMediator and contains most of the common logic.
 * 
 * @author Premjit Adhikary
 *
 */
public abstract class AbstractArena implements ArenaMediator, BeatObserver, Loggable {
    protected AbstractFighter champion;
    protected AbstractFighter challenger;
    protected TikTok tiktok;
    protected String name;
    protected MoveCloner moveCloner;
    
    public AbstractArena(String name) {
        this.name = name;
        KombatLogger.getLogger().start();
        moveCloner = new MoveCloner();
    }

    @Override
    public void addFighter(Fighter fighter) {
        if (champion != null && challenger != null)
            throw new UnsupportedOperationException("Only 2 fighters allowed");
        if (champion == null) 
            setChampion(fighter);
        else
            setChallenger(fighter);
    }

    @Override
    public void sendMove(Move move, Recipient recipient, Fighter fighter) {
        switch(recipient) {
        case SELF:
            fighter.react(Optional.ofNullable(move));
            break;
        case OPPONENT:
            opponent(fighter).react(Optional.ofNullable(move));
            break;
        case ALL:
            champion.react(Optional.ofNullable(moveCloner.clone(move)));
            challenger.react(Optional.ofNullable(move));
            break;
        }
    }

    @Override
    public Map<String, String> mapify() {
        return KombatLogger.mapBuilder().withName(name).build();
    }
    
    @Override
    public void update() {
        if (checkFightOver())
            KombatLogger.getLogger().stop();
    }
    
    public void fight() {
        logFightStart();
        tiktok.start();
        System.out.println("MEMORY USAGE: "+MemoryMonitor.getInstance().minMemoryUsed()+"mb - "
                +MemoryMonitor.getInstance().maxMemoryUsed()+"mb");
    }
    
    protected void setChampion(Fighter fighter) {
        champion = (AbstractFighter) fighter;
        tiktok.addTikObserver(champion);
        logFighterAdded(champion, CHAMPION);
        tiktok.addTikObserver(MemoryMonitor.getInstance());
        tiktok.addTikObserver(this);
    }
    
    protected void setChallenger(Fighter fighter) {
        challenger = (AbstractFighter) fighter;
        tiktok.addTokObserver(challenger);
        logFighterAdded(challenger, CHALLENGER);
        tiktok.addTokObserver(MemoryMonitor.getInstance());
        tiktok.addTokObserver(this);
    }
    
    protected boolean checkFightOver() {
        if (champion.isAlive() && challenger.isAlive())
            return false;
        
        tiktok.stop();
        logFightEnd(WIN, KNOCK_OUT);
        logFighterResult(champion, champion.isAlive() ? WON : LOST, CHAMPION);
        logFighterResult(challenger, challenger.isAlive() ? WON : LOST, CHALLENGER);
        return true;
    }
    
    private Fighter opponent(Fighter fighter) {
        return ((AbstractFighter) fighter).equals(champion) ? challenger : champion;
    }
    
    protected void logFightStart() {
        KombatLogger.getLogger().log(
                KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.INFO, 
                this.mapify(), KombatLogger.mapWithName(FIGHT_START));
    }
    
    protected void logFighterAdded(AbstractFighter fighter, String as) {
        KombatLogger.getLogger().log(
                KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.INFO, 
                fighter.mapify(), 
                KombatLogger.mapBuilder().withName(FIGHTER_ADDED).with(AS, as).build());
    }
    
    protected void logFightEnd(String result, String resultBy) {
        KombatLogger.getLogger().log(
                KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.INFO, 
                this.mapify(),
                KombatLogger.mapBuilder()
                    .withName(FIGHT_END)
                    .with(RESULT, result)
                    .with(RESULT_BY, resultBy)
                    .build());
    }
    
    protected void logFighterResult(AbstractFighter fighter, String result, String who) {
        KombatLogger.getLogger().log(
                KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.INFO, 
                fighter.mapify(), 
                KombatLogger.mapBuilder().withName(result).with(AS, who).build());
    }

}
