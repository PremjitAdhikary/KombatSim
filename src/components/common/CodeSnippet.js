import React from 'react';
import PropTypes from 'prop-types';
import './CodeSnippet.css';

const keywordColor = 'yellow';
const keywords = [
  'abstract', 'assert', 'boolean', 'break', 'byte', 'case', 'catch', 'char', 'class', 'const',
  'do', 'double', 'else', 'enum', 'extends', 'final', 'finally', 'float', 'for', 'goto', 'if',
  'implements', 'import', 'instanceof', 'int', 'interface', 'long', 'native', 'new', 'package',
  'private', 'protected', 'public', 'return', 'short', 'static', 'strictfp', 'super', 'switch',
  'synchronized', 'this', 'throw', 'throws', 'transient', 'try', 'void', 'volatile', 'while',
  'true', 'false', 'null'
];
const annotationColor = 'lightgray';
const annotations = ['Override'];
const commentColor = 'lightgray';
const closeTag = '</span>';

const processKeyword = (someCode) => {
  let processed = someCode;
  let openTag = '<span style="color: '+keywordColor+'; font-weight: bold;">';
  keywords.forEach(kw => 
    processed = processed.replace(
                            new RegExp('\\b'+kw+'\\b', 'g'), 
                            openTag+kw+closeTag
  ));
  return processed;
};

const processAnnotations = (someCode) => {
  let processed = someCode;
  let openTag = '<span style="color: '+annotationColor+'; font-weight: bold;">';
  annotations.forEach(annot => 
    processed = processed.replace(
                            new RegExp('@\\b'+annot+'\\b', 'g'), 
                            openTag+'@'+annot+closeTag
  ));
  return processed;
};

const processComments = (someCode) => {
  let processed = someCode;
  // picks out single line comments starting with // and enclosed in /* */, 
  // and multiline comments too
  let regexp = /\/\*[\s\S]*?\*\/|([^:]|^)\/\/.*$/gm;
  let matches = processed.match(regexp);
  if (matches) {
    let comments = [...matches];
    let openTag = '<span style="color: '+commentColor+'; font-style: italic;">';
    comments.forEach(
      comment => processed = processed.replace(comment, openTag+comment+closeTag)
    );
  }
  return processed;
};

const parseAndTag = (someCode) => {
  someCode = processKeyword(someCode);
  someCode = processAnnotations(someCode);
  someCode = processComments(someCode);
  return someCode;
};

const CodeSnippet = (props) => {
  let someCode = parseAndTag(props.code);
  const preBlock = { __html: "<pre>" + someCode + "</pre>" };
  return <div dangerouslySetInnerHTML={ preBlock }></div>;
};

CodeSnippet.propTypes = {
  code: PropTypes.string
};

export default CodeSnippet;