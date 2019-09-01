import React from 'react';
import PropTypes from 'prop-types';
import  {getRoute} from '../../const/Routes';

const ExternalLink = (props) => {
  let link = getRoute(props.link);
  return (
    <a 
      href={link.path} 
      rel="noopener noreferrer" 
      target="_blank">{link.name}</a>
  );
};

ExternalLink.propTypes = {
  link: PropTypes.string
};

export default ExternalLink;