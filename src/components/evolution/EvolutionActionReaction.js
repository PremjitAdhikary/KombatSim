import React from 'react';
import ImageHolder from '../common/ImageHolder';
import Navigators from '../common/Navigators';

const EvolutionActionReaction = () => {
  return (
    <div className="main">
      <div className="pageTitle">All together now</div>
      <p>
        Now that we have the basic engine ready, Fighters and Arena in place, on top of that a hit 
        system implemented, lets recap the whole end-to-end flow of a Hit.
      </p>
      <div className="sectionTitle">Action Story</div>
      <p>
        <code>TikTok</code> notifies the <code>Fighter</code>, who in turn notifies the <code>
        ActionObserver</code> of the beat.
      </p>
      <p>
        Based on some property, the <code>ActionObserver</code> decides whether to call <code>
        Fighter.act()</code> or not.
      </p>
      <p>If called, then the method <code>ActionStrategy.perform()</code> is invoked.</p>
      <p>Here an <code>ActionCommand</code> is selected and executed.</p>
      <p>
        On execution of the command, a Move is calculated, again based on some property, and then 
        sent to <code>Arena</code> to pass it on.
      </p>
      <ImageHolder imgId="evolutionActionSD" imgSize="large" />
      <div className="sectionTitle">Reaction Story</div>
      <p>
        Just like any action, the reaction also goes through a similar cycle.
      </p>
      <p>
        When a Move is recieved by the <code>Arena</code>, it passes it on to the opponent by 
        invoking <code>Fighter.react(move)</code>.
      </p>
      <p>
        Every <code>Fighter</code> has its own <code>ReactionStrategy</code> which queries <code>
        ReactionObserver.reactEnabled()</code> to  check if the <code>Fighter</code> can react or 
        not.
      </p>
      <p>
        If yes, then a <code>ReactionCommand</code> is selected from the list of commands available, 
        and executed. Again based on properties, <code>ReactionCommand.execute(move)</code> will 
        handle the move.
      </p>
      <ImageHolder imgId="evolutionReactionSD" imgSize="large" />
      <Navigators prev="evolutionObserver" next="evolutionFighter" />
    </div>
  );
};

export default EvolutionActionReaction;