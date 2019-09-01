import React from 'react';
import Navigators from '../common/Navigators';
import ImageHolder from '../common/ImageHolder';

const EvolutionHits = () => {
  return (
    <div className="main">
      <div className="pageTitle">Everything Surrounding A Hit</div>
      <p><em>What is a Hit?</em></p>
      <p>
        An <code>ActionCommand</code> executed by a Fighter which results in a <code>Move
        </code> passed to the opponent (or on self) for the later to react with a <code>
        ReactionCommand</code>
      </p>
      <p>On this page, here we have all possible Commands and Moves for a proper Hit.</p>
      <div className="sectionTitle">All Possible Actions</div>
      <p>
        Let's start with <code>ActionCommand</code>. The image below has all of them including 
        the recently discussed Spells.
      </p>
      <ImageHolder imgId="hierarchyActionCommand" imgSize="large" />
      <div className="sectionTitle">All Possible Reactions</div>
      <p>
        Of course <code>ReactionCommand</code> can't be far behind.
      </p>
      <ImageHolder imgId="hierarchyReactionCommand" imgSize="large" />
      <div className="sectionTitle">All Possible Moves - Damages and Buffs</div>
      <p>
        Here we have all the possible Moves and its SubClasses. But what is that Tracer? Will 
        be answered in a future section.
      </p>
      <ImageHolder imgId="hierarchyMove" imgSize="large" />
      <Navigators prev="evolutionMagic" next="evolutionCreators" />
    </div>
  );
};

export default EvolutionHits;