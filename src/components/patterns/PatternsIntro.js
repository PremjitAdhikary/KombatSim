import React from 'react';
import Navigators from '../common/Navigators';

const PatternsIntro = () => {
  return (
    <div className="main">
      <div className="pageTitle">All Patterns In Project</div>
      <p>This is a Glossary of all Patterns implemented (or not) in the project</p>
      <p>
        For you to have easy access to the actual code where the specific pattern is implemented 
        in the project, links are provided.
      </p>
      <Navigators next="patternCreational" />
    </div>
  );
};

export default PatternsIntro;