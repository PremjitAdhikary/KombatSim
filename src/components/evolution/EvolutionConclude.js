import React from 'react';
import Navigators from '../common/Navigators';

const EvolutionConclude = () => {
  return (
    <div className="main">
      <div className="pageTitle">To Conclude ...</div>
      <p>
        In no way am I claiming this to be perfect. There is always room for improvement. My aim was 
        to have a shot at incorporating as many patterns as I can. That has been achieved.
      </p>
      <p>
        There are code instances here that may look like overkill and one might feel that the same 
        could have been simplified. But again, let me reiterate, the idea was to incorporate as 
        many design patterns as I can. For the sake of learning, I was ok with the trade-off.
      </p>
      <div className="sectionTitle">Fight</div>
      <p>
        One of the ways to output a simulated fight that I had put in was to record it out in a Json 
        file. I have worked on another pet-project where I use that same json as an input to replay 
        recorded fight.
      </p>
      <p>I had simulated and uploaded a number of fights on the same. Enjoy!!</p>
      <p>
        <a 
        href="https://premjitadhikary.github.io/Mesmerize/simulated-combat/" 
        rel="noopener noreferrer" 
        target="_blank">Mesmerize</a>
      </p>
      <Navigators prev="evolutionMemento" />
    </div>
  );
};

export default EvolutionConclude;