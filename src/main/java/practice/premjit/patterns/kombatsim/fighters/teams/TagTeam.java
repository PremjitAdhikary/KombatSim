package practice.premjit.patterns.kombatsim.fighters.teams;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Optional;
import java.util.stream.Stream;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.beats.BeatObserver;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.fighters.Fighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.visitors.FighterVisitor;

public class TagTeam extends AbstractTeam {
    protected TagTeamState first;
    protected TagTeamState currentState;
    protected TagAndSwitch switchObserver;

    public TagTeam(ArenaMediator arena) {
        super("TagTeam", arena);
        proxyArena = new TagTeamArena(this);
        id = Randomizer.generateId();
        switchObserver = new TagAndSwitch();
    }
    
    @Override
    protected AbstractFighter currentFighter() {
        return currentState.fighter();
    }
    
    public void addTeammate(AbstractFighter fighter) {
        fighter.arena(proxyArena);
        fighter.setActionStrategy(new TagTeamActionStrategy(fighter));
        TagTeamState temp = new TagTeamState(fighter);
        if (first == null) {
            first = temp;
            currentState = first;
        } else {
            TagTeamState curr = first;
            while (curr.nextState != null)
                curr = curr.nextState;
            curr.setNext(temp);
        }
        KombatLogger.getLogger().log(
                KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.INFO, 
                fighter.mapify(), 
                KombatLogger.mapBuilder()
                    .withName(TEAMMATE_ADDED)
                    .with(AS, "Tagger")
                    .with(TEAM, this.name)
                    .build());
    }
    
    @Override
    public boolean isAlive() {
        return allStates().anyMatch( s -> s.fighter().isAlive() );
    }

    @Override
    public void react(Optional<Move> move) {
        currentState.fighter().react(move);
        double lifeRatio = currentState.fighter().currentLife() / currentState.fighter().maxLife();
        if (lifeRatio < 0.2  && isAlive())
            switchObserver.tag();
    }

    @Override
    public void update() {
        switchObserver.update();
        allStates().forEach( s -> s.fighter().update() );
    }

    @Override
    public void registerObserver(BeatObserver observer) {
        allStates().forEach( s -> s.fighter().registerObserver(observer) );
    }

    @Override
    public void unregisterObserver(BeatObserver observer) {
        allStates().forEach( s -> s.fighter().unregisterObserver(observer) );
    }

    @Override
    public void notifyObservers() {
        allStates().forEach( s -> s.fighter().notifyObservers() );
    }

    @Override
    public void accept(FighterVisitor visitor) {
        visitor.visit(currentState.fighter());
    }
    
    Stream<TagTeamState> allStates() {
        if (first == null)
            return Stream.empty();
        Stream.Builder<TagTeamState> builder = Stream.builder();
        TagTeamState curr = first;
        while (curr != null) {
            builder.add(curr);
            curr = curr.next();
        }
        return builder.build();
    }
    
    class TagTeamState {
        AbstractFighter fighter;
        TagTeamState nextState;
        
        TagTeamState(AbstractFighter fighter) {
            this.fighter = fighter;
        }
        
        AbstractFighter fighter() {
            return fighter;
        }
        
        void setAsCurrent() {
            if (fighter.isAlive()) {
                currentState = this;
            } else {
                nextInRotation().setAsCurrent();
            }
        }
        
        void setNext(TagTeamState next) {
            nextState = next;
        }
        
        TagTeamState nextInRotation() {
            return nextState == null ? first : nextState;
        }
        
        TagTeamState next() {
            return nextState;
        }
    }
    
    class TagAndSwitch implements BeatObserver {
        int switchAt = 100;
        int beatCount = 0;

        @Override
        public void update() {
            beatCount++;
            if (beatCount >= switchAt) 
                tag();
        }
        
        void tag() {
            currentState.nextInRotation().setAsCurrent();
            beatCount = 0;
            KombatLogger.getLogger().log(
                    KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.INFO, 
                    currentState.fighter().mapify(), 
                    KombatLogger.mapBuilder().withName("Tag And Switch").build());
        }
        
    }
    
    class TagTeamActionStrategy extends AbstractTeamActionStrategy {

        public TagTeamActionStrategy(AbstractFighter fighter) {
            super(fighter);
        }

        @Override
        protected boolean originalStrategyEnabled() {
            return fighter == currentState.fighter();
        }
        
    }
    
    class TagTeamArena extends ProxyArena {

        TagTeamArena(AbstractFighter team) {
            super(team);
        }

        @Override
        protected void selfMove(Move move, Recipient recipient, Fighter fighter) {
            fighter.react(Optional.ofNullable(move));
        }

        @Override
        protected void allMove(Move move, Recipient recipient, Fighter fighter) {
            allStates().forEach( s -> s.fighter().react(Optional.ofNullable(moveCloner.clone(move))) );
            arena.sendMove(move, Recipient.OPPONENT, team);
        }

        @Override
        protected void opponentMove(Move move, Recipient recipient, Fighter fighter) {
            arena.sendMove(move, Recipient.OPPONENT, team);
        }
        
    }

}
