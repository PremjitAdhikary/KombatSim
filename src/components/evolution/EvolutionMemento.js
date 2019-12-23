import React from 'react';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';

let code = `

  public class MysticalForest implements ArenaMediator, BeatObserver {

    int currentBeat;
    PriorityQueue&lt;AttributeMemento&gt; championStatesQueue;
    PriorityQueue&lt;AttributeMemento&gt; challengerStatesQueue;

    ...

    @Override
    public void update() {
      currentBeat++;
      if (createMementoBeat()) {
        createMemento();
      }
      if (restoreMementoBeat()) {
        restoreMemento();
      }
    }

    // saving state
    void createMemento() {
      AttributeMemento championLifeState = new AttributeMemento(champion.currentLife());
      championStatesQueue.offer(championLifeState);
      AttributeMemento challengerLifeState = new AttributeMemento(challenger.currentLife());
      challengerStatesQueue.offer(championLifeState);
    }

    // restoring state
    void restoreMemento() {
      boolean restoreChampion = randomSelection();
      if (restoreChampion) {
        AttributeMemento championLifeState = championStatesQueue.poll();
        champion.setLife(championLifeState.getState());
      } else {
        AttributeMemento challengerLifeState = challengerStatesQueue.poll();
        challenger.setLife(challengerLifeState.getState());
      }
    }

    static class AttributeMemento {
      Double life;
      AttributeMemento(double value) {
        life = value;
      }
    }

  }

`;

const EvolutionMemento = () => {
  return (
    <div className="main">
      <div className="pageTitle">A Memento</div>
      <p>
        Remember how I started the evolution section with <em>requirements don't appear out of thin 
        air</em> rant? This is going to be a testament to that.
      </p>
      <p>
        A perfect example of how a lazy mind can concoct the weirdest of requirement to satisfy, 
        well the requirement of introducing a particular pattern.
      </p>
      <p>
        The Pattern in question is Memento. So I came up with Mystical Forest ... an arena which is 
        mystical in nature.
      </p>
      <p><em>And what is the nature of this mystic?</em></p>
      <p>At regular intervals snapshots of the life of both the fighters are taken.</p>
      <p>And at certain intervals one of the fighters is chosen and his life restored!!</p>
      <div className="sectionTitle">Sample Code</div>
      <CodeSnippet code={code} />
      <Navigators prev="evolutionVisitors" next="evolutionConclude" />
    </div>
  );
};

export default EvolutionMemento;