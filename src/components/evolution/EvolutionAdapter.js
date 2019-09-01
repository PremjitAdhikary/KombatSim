import React from 'react';
import ImageHolder from '../common/ImageHolder';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';

let code = `

  public class ProxyArena implements ArenaMediator {
    AbstractFighter team;

    ProxyArena(AbstractFighter team) {
      this.team = team;
    }

    @Override
    public void sendMove(Move move) {
      this.team.arena().sendMove(move);
    }

    ...
  }

  public class OffenderActionStrategy implements ActionStrategy {
    protected AbstractFighter fighter;
    protected ActionStrategy originalStrategy;

    public OffenderActionStrategy(AbstractFighter fighter) {
      this.fighter = fighter;
      this.originalStrategy = fighter.getActionStrategy();
    }

    @Override
    public void perform() {
      if (fighterIsOffender()) {
        originalStrategy.perform();
      }
    }

  }

  public class OffenderDefenderTeam extends ConcreteFighter {
    ConcreteFighter offender;
    ConcreteFighter defender;
    ProxyArena proxy;

    public OffenderDefenderTeam(ArenaMediator arena, 
        ConcreteFighter offender, ConcreteFighter defender) {
      super(arena);
      this.offender = offender;
      this.defender = defender;
      this.proxyArena = new new ProxyArena(this);
      // set proxy arena into team members
      this.offender.arena(proxyArena);
      this.defender.arena(proxyArena);
      // set OffenderActionStrategy into team members
      this.offender.setActionStrategy(new OffenderActionStrategy(this.offender));
      this.defender.setActionStrategy(new OffenderActionStrategy(this.defender));
    }

    @Override
    public void react(Move move) {
      this.defender.react(move);
    }

  }

  // setting it up
  ArenaMediator arena = new ConcreteArenaMediator();
  ConcreteFighter fighterA = new ConcreteFighter(arena);
  ConcreteFighter fighterB = new ConcreteFighter(arena);
  OffenderDefenderTeam team = new OffenderDefenderTeam(arena, fighterA, fighterB);

`;

const EvolutionAdapter = () => {
  return (
    <div className="main">
      <div className="pageTitle">Clash Of Teams</div>
      <p>
        Now that  we have Amateurs, Professionals and even Enhanced Fighters I planned to put them 
        in Team fights.
      </p>
      <p>My idea was of a simple two-man team. One fighter just attacks, the other just defends.</p>
      <p>
        So two teams fighting each other means I will have four fighters in the Arena. <em>But the
        Arena only accepts 2 fighters!</em>
      </p>
      <p>
        Enter <strong>Adapter Pattern</strong>. It will act as a single fighter interface to the 
        Arena while internally house two fighters and would delegate the <code>act()</code>/<code>
        react()</code> accordingly. 
      </p>
      <ImageHolder imgId="evolutionAdapterCD" imgSize="medium" />
      <p>
        The actual plumbing required little more than just implement <strong>Adapter Pattern
        </strong>.
      </p>
      <p>
        You see, while the <code>react()</code> redirection was easy to set up, recall that <code>
        act()</code> is invoked by a <code>ActionObserver</code> on individual Fighter based on 
        their Dexterity.
      </p>
      <p><em>Need to suppress the action of the Defender.</em></p>
      <p>
        For this the <code>ActionStrategy</code> of the team members were wrapped to only keep the 
        Offender Strategy enabled. <strong>Strategy Pattern</strong> <em>yo!</em>
      </p>
      <p>There was another hiccup in the <code>act()</code> component.</p>
      <p><em>The Hit message to the  right recipient.</em></p>
      <p>
        Because there were multiple fighters on the arena now, <em>how does the arena know which 
        recipient to send it to?</em>
      </p>
      <p>This was resolved using <strong>Proxy Pattern</strong>.</p>
      <p>
        A ProxyArena would take the hit message from a team member, then send it to the actual Arena 
        as a hit that has come from the team rather than the specific fighter.
      </p>
      <ImageHolder imgId="evolutionAdapterSD" imgSize="medium" />
      <div className="sectionTitle">Sample Code</div>
      <CodeSnippet code={code} />
      <p>Next ... TagTeam!</p>
      <p>
        Unlike the OffenderDefenderTeam, TagTeam can have as many fighters in the team as you want.
      </p>
      <p>
        The idea is that among all fighters only one fighter is active at any given moment. At regular 
        intervals the fighter keeps changing in a round robin fashion.
      </p>
      <p>
        To keep track of the active fighter in the team, we use the <strong>State Pattern</strong>.
      </p>
      <Navigators prev="evolutionDecorator" next="evolutionDamages" />
    </div>
  );
};

export default EvolutionAdapter;