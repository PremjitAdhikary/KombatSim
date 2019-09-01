import React from 'react';
import Navigators from '../common/Navigators';

const LambdaIntro = () => {
  return (
    <div className="main">
      <div className="pageTitle">What The Function?</div>
      <p>Java now supports Functional Programming.</p>
      <p>My first thought was 'No more OOPS? And what about Design Patterns?</p>
      <p>
        Most important question: If Design Patterns are a thing of the past, how will I impress the 
        interviewer in my next interview?'
      </p>
      <p><em>Panic Attack!!!</em></p>
      <p>
        After some frantic research, <em>enlightenment!!</em> Who says that Design Patterns and 
        Functional Programming can't go hand in hand?
      </p>
      <p>
        The following sections are to highlight all such 'Design Pattern featuring Functional 
        Programming' moments in this project.
      </p>
      <p>
        Most of these are hugely inspired (and some shamelessly copied) from the excellent talks of 
        Venkat Subramaniam. In all seriousness, <em>search and listen to what he has to say!</em>
      </p>
      <Navigators next="lambdaStrategy" />
    </div>
  );
};

export default LambdaIntro;