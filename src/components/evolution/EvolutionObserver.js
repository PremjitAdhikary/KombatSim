import React from 'react';
import ImageHolder from '../common/ImageHolder';
import Navigators from '../common/Navigators';
import CodeSnippet from '../common/CodeSnippet';

let code = `

  public interface BeatObserver {
    void update();
  }

  public interface BeatObservable {
    void notifyObservers();
    void registerObserver(BeatObserver observer);
    void unregisterObserver(BeatObserver observer);
  }

  public class ActionObserver implements BeatObserver {
    Fighter fighter;

    @Override
    public void update() {
      if (fighterCanActOnThisBeat) { // some condition based on some attribute
        this.fighter.act();
      }
    }
  }

  public class AConcreteFighter implements Fighter, BeatObserver, BeatObservable {
    ConcreteObservable observable; // ActionObserver gets registered here
    ActionStrategy actionStrategy;
    ...

    @Override
    public void update() {
      this.notifyObservers();
    }

    @Override
    public void notifyObservers() {
      this.observable.notifyObservers();
    }

    @Override
    public void registerObserver(BeatObserver observer) {
      this.observable.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(BeatObserver observer) {
      this.observable.unregisterObserver(observer);
    }

    @Override
    public void act() {
      this.actionStrategy.perform();
    }
    ...

  }

`;

const EvolutionObserver = () => {
  return (
    <div className="main">
      <div className="pageTitle">So When Does A Fighter Hit?</div>
      <p>
        To make two fighters fight, I had set up a one-two-one-two loop for them to hit each 
        other turn by turn.
      </p>
      <p><em>That was the original version which I scrapped.</em></p>
      <p>I wanted the fighters to have their own <em>rhythm</em>.</p>
      <p>What I needed was some sort of stimuli to which the fighters would react to.</p>
      <p>Solution? <strong>Observer Pattern</strong>.</p>
      <ImageHolder imgId="evolutionObserverCD" imgSize="medium" />
      <blockquote>
        I had scrapped my first attempt at KombatSim just for the lack of rhythm. I had already 
        written more than 50 classes - and JUnits for all of them. That's a lot of effort put in a 
        pet project. <em>And still I scrapped</em>. But now with what I have in place with Observers 
        and Observables, I am able to achieve so much more than what I originally had thought. So 
        many different effects, which would have been impossible with the original design.
      </blockquote>
      <p>
        The Fighter is also an Observable who notifies his Observers (one of them being our <code>
        ActionObserver</code>).
      </p>
      <p>
        The <code>ActionObserver</code> in turn checks if the fighter can <code>act()</code> or not. 
        This decision is based on the Fighters agility (one of the properties).
      </p>
      <p>If he can, the fighter hits, else waits for the next <code>update()</code>.</p>
      <p>This is what gives the rhythm to the fighter.</p>
      <ImageHolder imgId="evolutionActionObserverSD" />
      <p>Ok, so that was just half the story.</p>
      <p>
        Even though the Fighter is an Observable, the question remains how does the fighter know 
        when to <code>notifyObservers()</code>?
      </p>
      <p>
        Readers with acute observation may have observed that in the Sequence Diagram above, the 
        'Found Message' to our Observable Fighter is an <code>update()</code>, which is an Observer 
        api.
      </p>
      <p>Yes, Fighter acts both as an Observer as well as an Observable.</p>
      <p>
        When a Fighter gets added to an Arena, he is also added as an Observer to an internal Beat 
        System which every Arena has.
      </p>
      <p>The Fighters are added to two different BeatObservables.</p>
      <p>When the fight starts, those two Observables notify the Fighters in an alternate fashion.</p>
      <p>
        This system is realized in the <code>TikTok</code> class which is depicted in the Sequence 
        Diagram below.
      </p>
      <ImageHolder imgId="evolutionTikTokSD" imgSize="medium" />
      <div className="sectionTitle">Sample Code</div>
      <p>
        Here goes the <code>TikTok</code> code.
      </p>
      <CodeSnippet code={code} />
      <Navigators prev="evolutionStrategy" next="evolutionActionReaction" />
    </div>
  );
};

export default EvolutionObserver;