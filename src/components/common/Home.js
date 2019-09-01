import React from 'react';

const Home = () => {
  const imgPath = 'https://github.com/PremjitAdhikary.png';
  const imgStyle = {
    width: '10%',
    float: 'left',
    border: '1px solid darkgrey',
    borderRadius: '4px'
  };
  const paraStyle = {
    float: 'right',
    width: '88%'
  };
  
  return (
    <div className="main">
      <div className="pageTitle">What is Kombat Sim Anyway?</div>
      <p><em>Fighters from different styles and origins challenge each other in kombat.</em></p>
      <p>This project is to simulate such a kombat.</p>
      <p>
        <em> Mortal Kombat</em> and <em>Diablo</em> are two games which have kept me hooked for 
        I-don't-even-know-how-many hours. They tremendously inspired this project.
      </p>
      <div className="sectionTitle">Why Kombat Sim</div>
      <p>
        At its core, it's my humble attempt at figuring out Design Patterns. My aim - use as 
        many patterns as possible to realize this project.
      </p>
      <div>
        <img  alt="Me" style={imgStyle} src={imgPath} />
        <div style={paraStyle}>
          <div className="sectionTitle">About Author</div>
          <p>
            Premjit Adhikary is a software developer with more than a decade of experience in the IT 
            industry. At night and weekends, if he is not engaged somewhere else, he doubles as an 
            independent webmaster, algorithmist and digital artist.
          </p>
        </div>
      </div>
    </div>
  );
};

export default Home;