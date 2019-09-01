(window.webpackJsonp=window.webpackJsonp||[]).push([[21],{37:function(e,t,a){"use strict";var i=a(0),n=a.n(i),o=a(6),r=a(1),l=(a(38),function(e,t){return e?n.a.createElement(o.c,{className:"btn",to:Object(r.b)(e).path},t):n.a.createElement("span",{className:"btn-disabled"},t)});t.a=function(e){return n.a.createElement("div",{className:"navs"},n.a.createElement("span",{className:"left"},l(e.prev,"Previous")),n.a.createElement("span",{className:"right"},l(e.next,"Next")))}},38:function(e,t,a){},41:function(e,t,a){},42:function(e,t,a){"use strict";function i(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var a=[],i=!0,n=!1,o=void 0;try{for(var r,l=e[Symbol.iterator]();!(i=(r=l.next()).done)&&(a.push(r.value),!t||a.length!==t);i=!0);}catch(c){n=!0,o=c}finally{try{i||null==l.return||l.return()}finally{if(n)throw o}}return a}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance")}()}var n=a(0),o=a.n(n),r=a(8),l={evolution:[{id:"evolutionMediatorCD",path:"/evolution/Evolution-Mediator-ClassDiagram.svg",text:"Mediator Class Diagram"},{id:"evolutionMediatorSD",path:"/evolution/Evolution-Mediator-SequenceDiagram.svg",text:"Mediator Sequence Diagram"},{id:"evolutionActionCommandCD",path:"/evolution/Evolution-ActionCommand-ClassDiagram.svg",text:"Action Command Class Diagram"},{id:"evolutionActionCommandSD",path:"/evolution/Evolution-ActionCommand-SequenceDiagram.svg",text:"Action Command Sequence Diagram"},{id:"evolutionStrategyTemplateCD",path:"/evolution/Evolution-ActionStrategyTemplate-ClassDiagram.svg",text:"Action Strategy and Template Class Diagram"},{id:"evolutionStrategyTemplateSD",path:"/evolution/Evolution-ActionStrategyTemplate-SequenceDiagram.svg",text:"Action Strategy and Template Sequence Diagram"},{id:"evolutionBridgeCD",path:"/evolution/Evolution-StrategyCommandBridge-ClassDiagram.svg",text:"Bridge Class Diagram"},{id:"evolutionObserverCD",path:"/evolution/Evolution-BeatObserver-ClassDiagram.svg",text:"Observer Class Diagram"},{id:"evolutionActionObserverSD",path:"/evolution/Evolution-ActionObserver-SequenceDiagram.svg",text:"Action Observer Sequence Diagram"},{id:"evolutionTikTokSD",path:"/evolution/Evolution-TikTok-SequenceDiagram.svg",text:"Tik Tok Sequence Diagram"},{id:"evolutionActionSD",path:"/evolution/Evolution-Action-SequenceDiagram.svg",text:"Action Sequence Diagram"},{id:"evolutionReactionSD",path:"/evolution/Evolution-Reaction-SequenceDiagram.svg",text:"Reaction Sequence Diagram"},{id:"evolutionDecoratorCD",path:"/evolution/Evolution-Decorator-ClassDiagram.svg",text:"Decorator Class Diagram"},{id:"evolutionDecoratorSD",path:"/evolution/Evolution-Decorator-SequenceDiagram.svg",text:"Decorator Sequence Diagram"},{id:"evolutionAdapterCD",path:"/evolution/Evolution-Adapter-ClassDiagram.svg",text:"Adapter Class Diagram"},{id:"evolutionAdapterSD",path:"/evolution/Evolution-Adapter-SequenceDiagram.svg",text:"Adapter Sequence Diagram"},{id:"evolutionCompositeCD",path:"/evolution/Evolution-Composite-ClassDiagram.svg",text:"Composite Class Diagram"},{id:"evolutionMoveVisitorCD",path:"/evolution/Evolution-MoveVisitor-ClassDiagram.svg",text:"Move Visitor Class Diagram"},{id:"evolutionMoveVisitorSD",path:"/evolution/Evolution-MoveVisitor-SequenceDiagram.svg",text:"Move Visitor Sequence Diagram"},{id:"evolutionFighterVisitorSD",path:"/evolution/Evolution-FighterVisitor-SequenceDiagram.svg",text:"Fighter Visitor Sequence Diagram"}],hierarchy:[{id:"hierarchyMove",path:"/hierarchy/Hierarchy-Move.svg",text:"Class Hierarchy of Moves"},{id:"hierarchyActionCommand",path:"/hierarchy/Hierarchy-ActionCommand.svg",text:"Class Hierarchy of ActionCommand"},{id:"hierarchyReactionCommand",path:"/hierarchy/Hierarchy-ReactionCommand.svg",text:"Class Hierarchy of ReactionCommand"},{id:"hierarchyFighter",path:"/hierarchy/Hierarchy-Fighter.svg",text:"Class Hierarchy of Fighter"}]},c=function(e){return[].concat(Object(r.a)(l.evolution),Object(r.a)(l.hierarchy)).find(function(t){return t.id===e})},s=(a(41),function(e){if(!e)return"large-content";switch(e){case"medium":return"large-content-med";case"large":return"large-content-full";case"small":default:return"large-content"}});t.a=function(e){var t=c(e.imgId),a=i(Object(n.useState)(!0),2),r=a[0],l=a[1];return o.a.createElement("div",{className:"center"},o.a.createElement("div",{className:"regular",onClick:function(){return l(!1)}},o.a.createElement("img",{src:""+t.path,alt:t.text}),o.a.createElement("div",null,t.text)),o.a.createElement("div",{className:"large",style:{display:r?"none":"block"}},o.a.createElement("span",{className:"close",onClick:function(){return l(!0)}},"\xd7"),o.a.createElement("img",{className:s(e.imgSize),alt:t.text,src:""+t.path}),o.a.createElement("div",{className:"caption"},t.text)))}},56:function(e,t,a){"use strict";a.r(t);var i=a(0),n=a.n(i),o=a(37),r=a(42);t.default=function(){return n.a.createElement("div",{className:"main"},n.a.createElement("div",{className:"pageTitle"},"Everything Surrounding A Hit"),n.a.createElement("p",null,n.a.createElement("em",null,"What is a Hit?")),n.a.createElement("p",null,"An ",n.a.createElement("code",null,"ActionCommand")," executed by a Fighter which results in a ",n.a.createElement("code",null,"Move")," passed to the opponent (or on self) for the later to react with a ",n.a.createElement("code",null,"ReactionCommand")),n.a.createElement("p",null,"On this page, here we have all possible Commands and Moves for a proper Hit."),n.a.createElement("div",{className:"sectionTitle"},"All Possible Actions"),n.a.createElement("p",null,"Let's start with ",n.a.createElement("code",null,"ActionCommand"),". The image below has all of them including the recently discussed Spells."),n.a.createElement(r.a,{imgId:"hierarchyActionCommand",imgSize:"large"}),n.a.createElement("div",{className:"sectionTitle"},"All Possible Reactions"),n.a.createElement("p",null,"Of course ",n.a.createElement("code",null,"ReactionCommand")," can't be far behind."),n.a.createElement(r.a,{imgId:"hierarchyReactionCommand",imgSize:"large"}),n.a.createElement("div",{className:"sectionTitle"},"All Possible Moves - Damages and Buffs"),n.a.createElement("p",null,"Here we have all the possible Moves and its SubClasses. But what is that Tracer? Will be answered in a future section."),n.a.createElement(r.a,{imgId:"hierarchyMove",imgSize:"large"}),n.a.createElement(o.a,{prev:"evolutionMagic",next:"evolutionCreators"}))}}}]);
//# sourceMappingURL=21.802ce93f.chunk.js.map