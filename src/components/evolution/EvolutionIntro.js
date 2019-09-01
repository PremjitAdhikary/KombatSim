import React from 'react';
import Navigators from '../common/Navigators';

const EvolutionIntro = () => {
  return (
    <div className="main">
      <div className="pageTitle">Evolution Of Kombat Sim</div>
      <p>So how did I go about it?</p>
      <p><em>Answers, find you will in the sections that follow!</em></p>
      <p>In short - Solve problems one at a time using Design Patterns.</p>
      <p>
        While I did manage to use a lot of patterns in this, a few still eluded. Maybe sometime in the 
        future, I can come up with a requirement which requires me to add those elusive patterns <em>
        (While the very thought of coming up with requirements is enticing, they don't appear out of 
        thin air - effort is expected for which time is limited... Meh, whatever)</em>. 
      </p>
      <p>The original idea came to me back in 2017.</p>
      <p>
        Started with the basics and kept adding complexities to it. Continuous refactoring went 
        with it.
      </p>
      <p>
        At one point, I took whatever I had built and scrapped it! Started again from scratch.
      </p>
      <p>
        The learnings from the previous monstrosity that I created came in handy. I had a far 
        better approach.
      </p>
      <div className="sectionTitle">Fair warning</div>
      <p>
        This is not a tutorial for <em>Design Patterns</em>. There are many many excellent resources 
        out there for your perusal.
      </p>
      <p>
        Also all the sample code presented in the following segments are just that, <em>sample code
        </em>. The actual implementation (even class names) may vary.
      </p>
      <Navigators next="evolutionMediator" />
    </div>
  );
};

export default EvolutionIntro;