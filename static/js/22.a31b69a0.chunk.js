(window.webpackJsonp=window.webpackJsonp||[]).push([[22],{37:function(e,t,a){"use strict";var n=a(0),i=a.n(n),o=a(6),r=a(1),l=(a(38),function(e,t){return e?i.a.createElement(o.c,{className:"btn",to:Object(r.b)(e).path},t):i.a.createElement("span",{className:"btn-disabled"},t)});t.a=function(e){return i.a.createElement("div",{className:"navs"},i.a.createElement("span",{className:"left"},l(e.prev,"Previous")),i.a.createElement("span",{className:"right"},l(e.next,"Next")))}},38:function(e,t,a){},41:function(e,t,a){},42:function(e,t,a){"use strict";function n(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var a=[],n=!0,i=!1,o=void 0;try{for(var r,l=e[Symbol.iterator]();!(n=(r=l.next()).done)&&(a.push(r.value),!t||a.length!==t);n=!0);}catch(s){i=!0,o=s}finally{try{n||null==l.return||l.return()}finally{if(i)throw o}}return a}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance")}()}var i=a(0),o=a.n(i),r=a(8),l={evolution:[{id:"evolutionMediatorCD",path:"/evolution/Evolution-Mediator-ClassDiagram.svg",text:"Mediator Class Diagram"},{id:"evolutionMediatorSD",path:"/evolution/Evolution-Mediator-SequenceDiagram.svg",text:"Mediator Sequence Diagram"},{id:"evolutionActionCommandCD",path:"/evolution/Evolution-ActionCommand-ClassDiagram.svg",text:"Action Command Class Diagram"},{id:"evolutionActionCommandSD",path:"/evolution/Evolution-ActionCommand-SequenceDiagram.svg",text:"Action Command Sequence Diagram"},{id:"evolutionStrategyTemplateCD",path:"/evolution/Evolution-ActionStrategyTemplate-ClassDiagram.svg",text:"Action Strategy and Template Class Diagram"},{id:"evolutionStrategyTemplateSD",path:"/evolution/Evolution-ActionStrategyTemplate-SequenceDiagram.svg",text:"Action Strategy and Template Sequence Diagram"},{id:"evolutionBridgeCD",path:"/evolution/Evolution-StrategyCommandBridge-ClassDiagram.svg",text:"Bridge Class Diagram"},{id:"evolutionObserverCD",path:"/evolution/Evolution-BeatObserver-ClassDiagram.svg",text:"Observer Class Diagram"},{id:"evolutionActionObserverSD",path:"/evolution/Evolution-ActionObserver-SequenceDiagram.svg",text:"Action Observer Sequence Diagram"},{id:"evolutionTikTokSD",path:"/evolution/Evolution-TikTok-SequenceDiagram.svg",text:"Tik Tok Sequence Diagram"},{id:"evolutionActionSD",path:"/evolution/Evolution-Action-SequenceDiagram.svg",text:"Action Sequence Diagram"},{id:"evolutionReactionSD",path:"/evolution/Evolution-Reaction-SequenceDiagram.svg",text:"Reaction Sequence Diagram"},{id:"evolutionDecoratorCD",path:"/evolution/Evolution-Decorator-ClassDiagram.svg",text:"Decorator Class Diagram"},{id:"evolutionDecoratorSD",path:"/evolution/Evolution-Decorator-SequenceDiagram.svg",text:"Decorator Sequence Diagram"},{id:"evolutionAdapterCD",path:"/evolution/Evolution-Adapter-ClassDiagram.svg",text:"Adapter Class Diagram"},{id:"evolutionAdapterSD",path:"/evolution/Evolution-Adapter-SequenceDiagram.svg",text:"Adapter Sequence Diagram"},{id:"evolutionCompositeCD",path:"/evolution/Evolution-Composite-ClassDiagram.svg",text:"Composite Class Diagram"},{id:"evolutionMoveVisitorCD",path:"/evolution/Evolution-MoveVisitor-ClassDiagram.svg",text:"Move Visitor Class Diagram"},{id:"evolutionMoveVisitorSD",path:"/evolution/Evolution-MoveVisitor-SequenceDiagram.svg",text:"Move Visitor Sequence Diagram"},{id:"evolutionFighterVisitorSD",path:"/evolution/Evolution-FighterVisitor-SequenceDiagram.svg",text:"Fighter Visitor Sequence Diagram"}],hierarchy:[{id:"hierarchyMove",path:"/hierarchy/Hierarchy-Move.svg",text:"Class Hierarchy of Moves"},{id:"hierarchyActionCommand",path:"/hierarchy/Hierarchy-ActionCommand.svg",text:"Class Hierarchy of ActionCommand"},{id:"hierarchyReactionCommand",path:"/hierarchy/Hierarchy-ReactionCommand.svg",text:"Class Hierarchy of ReactionCommand"},{id:"hierarchyFighter",path:"/hierarchy/Hierarchy-Fighter.svg",text:"Class Hierarchy of Fighter"}]},s=function(e){return[].concat(Object(r.a)(l.evolution),Object(r.a)(l.hierarchy)).find(function(t){return t.id===e})},c=(a(41),function(e){if(!e)return"large-content";switch(e){case"medium":return"large-content-med";case"large":return"large-content-full";case"small":default:return"large-content"}});t.a=function(e){var t=s(e.imgId),a=n(Object(i.useState)(!0),2),r=a[0],l=a[1];return o.a.createElement("div",{className:"center"},o.a.createElement("div",{className:"regular",onClick:function(){return l(!1)}},o.a.createElement("img",{src:""+t.path,alt:t.text}),o.a.createElement("div",null,t.text)),o.a.createElement("div",{className:"large",style:{display:r?"none":"block"}},o.a.createElement("span",{className:"close",onClick:function(){return l(!0)}},"\xd7"),o.a.createElement("img",{className:c(e.imgSize),alt:t.text,src:""+t.path}),o.a.createElement("div",{className:"caption"},t.text)))}},58:function(e,t,a){"use strict";a.r(t);var n=a(0),i=a.n(n),o=a(37),r=a(42);t.default=function(){return i.a.createElement("div",{className:"main"},i.a.createElement("div",{className:"pageTitle"},"Visitors"),i.a.createElement("p",null,"From my understanding, ",i.a.createElement("strong",null,"Visitor Pattern")," can be used to solve 2 types of problems. There may be more, but the below is as per my limited understanding of this awesome pattern:"),i.a.createElement("ul",null,i.a.createElement("li",null,"Add new operations to existing class without modifying the structures."),i.a.createElement("li",null,"This is going to be a little bit hard to explain. Basically if we have 2 sets of objects where every pair of object has a different outcome, this pattern nails it. The main idea being ",i.a.createElement("em",null,"Double Dispatch"),".")),i.a.createElement("p",null,"The implementation here is mostly solving problems of the second nature."),i.a.createElement("div",{className:"sectionTitle"},"Move Visitor"),i.a.createElement("p",null,"Let's revisit Batman."),i.a.createElement("p",null,"Batman does not have any Super Powers so he uses technology to his favor. BatSuit is capable of resisting most of the type of attacks that comes Batman's way."),i.a.createElement("p",null,"Awesome! ",i.a.createElement("em",null,"But how does that translate into code?")),i.a.createElement("p",null,i.a.createElement("strong",null,"Visitor Pattern")," is the answer!"),i.a.createElement("p",null,"Armor (using ",i.a.createElement("strong",null,"Decorator Pattern"),") takes the hit first instead of Batman. But there are different types of damages to deal with!"),i.a.createElement(r.a,{imgId:"evolutionMoveVisitorCD",imgSize:"large"}),i.a.createElement("p",null,"When ",i.a.createElement("code",null,"Armor.react()")," gets a ",i.a.createElement("code",null,"Move")," it passes a ",i.a.createElement("code",null,"ArmorDamageAbsorber")," to determine the type of damage and accordingly reduce the damage in the ",i.a.createElement("code",null,"Move"),"."),i.a.createElement("p",null,"This is how Batman is able to stand up against so many types of damages."),i.a.createElement(r.a,{imgId:"evolutionMoveVisitorSD",imgSize:"large"}),i.a.createElement("p",null,"In the above diagram, not all Damage types are denoted, but you get the picture!"),i.a.createElement("p",null,i.a.createElement("code",null,"DamageReducer"),", a MoveVisitor implementation, works the same way to enable ",i.a.createElement("code",null,"ReflectDamageSpell")," to reflect different types of Moves as they come in."),i.a.createElement("div",{className:"sectionTitle"},"Fighter Visitor"),i.a.createElement("p",null,"Now let's dissect Cold Damage."),i.a.createElement("p",null,"Cold Damage gives the Chills. So it slows down the opponent. As discussed in an earlier section this is achieved simply by lowering the Dexterity."),i.a.createElement("p",null,i.a.createElement("em",null,"But Mages don't have Dexterity bub! What now?")),i.a.createElement("p",null,"Eureka moment!! Mages only cast spells. And the ability to cast spells depends on how much Mana the caster has in store. So lower Mana."),i.a.createElement("p",null,"Now that we have the solution, ",i.a.createElement("strong",null,"Visitor Pattern")," to implement it!"),i.a.createElement(r.a,{imgId:"evolutionFighterVisitorSD",imgSize:"medium"}),i.a.createElement("p",null,"In a similar manner, ",i.a.createElement("code",null,"BatmanActionStrategy")," utilizes ",i.a.createElement("code",null,"TracerVisitor")," to determine Fighter type and deliver an attack which massively weakens his opponent."),i.a.createElement("p",null,"For example if the opponent is Superman, throw a Kyptonite Pellet at him to reduce the mighty Red to a Punching Bag!"),i.a.createElement("p",null,"If it's Flash we are talking, Stun and Freeze Pellets ought to slow him down to a trickle!"),i.a.createElement("p",null,"For Mages, gas them and burn their Mana!"),i.a.createElement(o.a,{prev:"evolutionCreators",next:"evolutionMemento"}))}}}]);
//# sourceMappingURL=22.a31b69a0.chunk.js.map