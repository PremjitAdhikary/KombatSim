(window.webpackJsonp=window.webpackJsonp||[]).push([[7],{37:function(e,t,a){"use strict";var n=a(0),o=a.n(n),r=a(6),i=a(1),l=(a(38),function(e,t){return e?o.a.createElement(r.c,{className:"btn",to:Object(i.b)(e).path},t):o.a.createElement("span",{className:"btn-disabled"},t)});t.a=function(e){return o.a.createElement("div",{className:"navs"},o.a.createElement("span",{className:"left"},l(e.prev,"Previous")),o.a.createElement("span",{className:"right"},l(e.next,"Next")))}},38:function(e,t,a){},39:function(e,t,a){"use strict";var n=a(8),o=a(0),r=a.n(o),i=(a(40),["abstract","assert","boolean","break","byte","case","catch","char","class","const","do","double","else","enum","extends","final","finally","float","for","goto","if","implements","import","instanceof","int","interface","long","native","new","package","private","protected","public","return","short","static","strictfp","super","switch","synchronized","this","throw","throws","transient","try","void","volatile","while","true","false","null"]),l=["Override"],c=function(e){return e=function(e){var t=e,a=t.match(/\/\*[\s\S]*?\*\/|([^:]|^)\/\/.*$/gm);if(a){var o=Object(n.a)(a);o.forEach(function(e){return t=t.replace(e,'<span style="color: lightgray; font-style: italic;">'+e+"</span>")})}return t}(e=function(e){var t=e;return l.forEach(function(e){return t=t.replace(new RegExp("@\\b"+e+"\\b","g"),'<span style="color: lightgray; font-weight: bold;">@'+e+"</span>")}),t}(e=function(e){var t=e;return i.forEach(function(e){return t=t.replace(new RegExp("\\b"+e+"\\b","g"),'<span style="color: yellow; font-weight: bold;">'+e+"</span>")}),t}(e)))};t.a=function(e){var t={__html:"<pre>"+c(e.code)+"</pre>"};return r.a.createElement("div",{dangerouslySetInnerHTML:t})}},40:function(e,t,a){},41:function(e,t,a){},42:function(e,t,a){"use strict";function n(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var a=[],n=!0,o=!1,r=void 0;try{for(var i,l=e[Symbol.iterator]();!(n=(i=l.next()).done)&&(a.push(i.value),!t||a.length!==t);n=!0);}catch(c){o=!0,r=c}finally{try{n||null==l.return||l.return()}finally{if(o)throw r}}return a}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance")}()}var o=a(0),r=a.n(o),i=a(8),l={evolution:[{id:"evolutionMediatorCD",path:"/evolution/Evolution-Mediator-ClassDiagram.svg",text:"Mediator Class Diagram"},{id:"evolutionMediatorSD",path:"/evolution/Evolution-Mediator-SequenceDiagram.svg",text:"Mediator Sequence Diagram"},{id:"evolutionActionCommandCD",path:"/evolution/Evolution-ActionCommand-ClassDiagram.svg",text:"Action Command Class Diagram"},{id:"evolutionActionCommandSD",path:"/evolution/Evolution-ActionCommand-SequenceDiagram.svg",text:"Action Command Sequence Diagram"},{id:"evolutionStrategyTemplateCD",path:"/evolution/Evolution-ActionStrategyTemplate-ClassDiagram.svg",text:"Action Strategy and Template Class Diagram"},{id:"evolutionStrategyTemplateSD",path:"/evolution/Evolution-ActionStrategyTemplate-SequenceDiagram.svg",text:"Action Strategy and Template Sequence Diagram"},{id:"evolutionBridgeCD",path:"/evolution/Evolution-StrategyCommandBridge-ClassDiagram.svg",text:"Bridge Class Diagram"},{id:"evolutionObserverCD",path:"/evolution/Evolution-BeatObserver-ClassDiagram.svg",text:"Observer Class Diagram"},{id:"evolutionActionObserverSD",path:"/evolution/Evolution-ActionObserver-SequenceDiagram.svg",text:"Action Observer Sequence Diagram"},{id:"evolutionTikTokSD",path:"/evolution/Evolution-TikTok-SequenceDiagram.svg",text:"Tik Tok Sequence Diagram"},{id:"evolutionActionSD",path:"/evolution/Evolution-Action-SequenceDiagram.svg",text:"Action Sequence Diagram"},{id:"evolutionReactionSD",path:"/evolution/Evolution-Reaction-SequenceDiagram.svg",text:"Reaction Sequence Diagram"},{id:"evolutionDecoratorCD",path:"/evolution/Evolution-Decorator-ClassDiagram.svg",text:"Decorator Class Diagram"},{id:"evolutionDecoratorSD",path:"/evolution/Evolution-Decorator-SequenceDiagram.svg",text:"Decorator Sequence Diagram"},{id:"evolutionAdapterCD",path:"/evolution/Evolution-Adapter-ClassDiagram.svg",text:"Adapter Class Diagram"},{id:"evolutionAdapterSD",path:"/evolution/Evolution-Adapter-SequenceDiagram.svg",text:"Adapter Sequence Diagram"},{id:"evolutionCompositeCD",path:"/evolution/Evolution-Composite-ClassDiagram.svg",text:"Composite Class Diagram"},{id:"evolutionMoveVisitorCD",path:"/evolution/Evolution-MoveVisitor-ClassDiagram.svg",text:"Move Visitor Class Diagram"},{id:"evolutionMoveVisitorSD",path:"/evolution/Evolution-MoveVisitor-SequenceDiagram.svg",text:"Move Visitor Sequence Diagram"},{id:"evolutionFighterVisitorSD",path:"/evolution/Evolution-FighterVisitor-SequenceDiagram.svg",text:"Fighter Visitor Sequence Diagram"}],hierarchy:[{id:"hierarchyMove",path:"/hierarchy/Hierarchy-Move.svg",text:"Class Hierarchy of Moves"},{id:"hierarchyActionCommand",path:"/hierarchy/Hierarchy-ActionCommand.svg",text:"Class Hierarchy of ActionCommand"},{id:"hierarchyReactionCommand",path:"/hierarchy/Hierarchy-ReactionCommand.svg",text:"Class Hierarchy of ReactionCommand"},{id:"hierarchyFighter",path:"/hierarchy/Hierarchy-Fighter.svg",text:"Class Hierarchy of Fighter"}]},c=function(e){return[].concat(Object(i.a)(l.evolution),Object(i.a)(l.hierarchy)).find(function(t){return t.id===e})},s=(a(41),function(e){if(!e)return"large-content";switch(e){case"medium":return"large-content-med";case"large":return"large-content-full";case"small":default:return"large-content"}});t.a=function(e){var t=c(e.imgId),a=n(Object(o.useState)(!0),2),i=a[0],l=a[1];return r.a.createElement("div",{className:"center"},r.a.createElement("div",{className:"regular",onClick:function(){return l(!1)}},r.a.createElement("img",{src:"/KombatSim"+t.path,alt:t.text}),r.a.createElement("div",null,t.text)),r.a.createElement("div",{className:"large",style:{display:i?"none":"block"}},r.a.createElement("span",{className:"close",onClick:function(){return l(!0)}},"\xd7"),r.a.createElement("img",{className:s(e.imgSize),alt:t.text,src:"/KombatSim"+t.path}),r.a.createElement("div",{className:"caption"},t.text)))}},54:function(e,t,a){"use strict";a.r(t);var n=a(0),o=a.n(n),r=a(37),i=a(39),l=a(42);t.default=function(){return o.a.createElement("div",{className:"main"},o.a.createElement("div",{className:"pageTitle"},"Heroes"),o.a.createElement("p",null,"Fighters now ",o.a.createElement("em",null,"can actually")," stare each other to death!"),o.a.createElement("p",null,"With the additional type of damages beyond the physical type, now is the time to introduce the next level of fighters."),o.a.createElement("p",null,"Heroes have far higher ",o.a.createElement("em",null,"Strength")," and ",o.a.createElement("em",null,"Dexterity")," than average fighters."),o.a.createElement("p",null,"They can Punch, Kick, Block and Evade but are simply far more efficient and effective at those Actions and Reactions."),o.a.createElement("p",null,o.a.createElement("em",null,"And they have Super Powers!!!")),o.a.createElement("p",null,"Just like Professionals have an extra attribute ",o.a.createElement("em",null,"Stamina"),", Heroes have an attribute called ",o.a.createElement("em",null,"Mojo"),". Mojo allows Heroes to do Mojo based Actions."),o.a.createElement("p",null,"Superman has Heat Vision (Fire Damage) and Freeze Breath (Cold Damage) while Flash has Arc Lightning (Shock Damage)."),o.a.createElement("p",null,o.a.createElement("em",null,"In case you didn't get it, stare to death refers to Heat Vision.")),o.a.createElement("div",{className:"sectionTitle"},"Sample Code for Super Powers"),o.a.createElement(i.a,{code:"\n\n  public abstract class MojoBasedAction implements ActionCommand {\n    Hero fighter;\n    double mojoCost;\n    ...\n\n    @Override\n    public void execute() {\n      chargeMojo();\n      Move move = calculateMove();\n      sendMove(move);\n    }\n\n    protected void chargeMojo() {\n      this.fighter.getMojo().incrementMojo(-mojoCost);\n    }\n\n    protected void sendMove(Move move) {\n      // send move to the arena\n    }\n\n    protected abstract Move calculateMove();\n\n  }\n\n  public class HeatVision extends MojoBasedAction {\n    \n    @Override\n    protected Move calculateMove() {\n      return new FireDamage(damage, duration, burnDamage);\n    }\n\n  }\n\n  public class FreezeBreath extends MojoBasedAction {\n    \n    @Override\n    protected Move calculateMove() {\n      return new ColdDamage(damage, duration, reduceDexterity);\n    }\n\n  }\n\n  public class ArcLightning extends MojoBasedAction {\n    \n    @Override\n    protected Move calculateMove() {\n      return new ShockDamage(damage, duration);\n    }\n\n  }\n\n"}),o.a.createElement("p",null,"How can we forget Batman? Batman has no SuperPowers but what he has is technology and a whole lot of willpower."),o.a.createElement("p",null,"To mimic willpower, a new Reaction, Endure, is introduced."),o.a.createElement("p",null,"Endure is based on ",o.a.createElement("strong",null,"Composite Pattern"),"."),o.a.createElement("p",null,"Internally it has both Block and Evade. Depending on the situation, Batman chooses either one of those."),o.a.createElement("p",null,"Beyond that Endure also gives Batman a safety net where in case the damage dealt is fatal in nature, Batman's Mojo is charged and Life is spared. Obviously there is a limit to this otherwise Batman will never die!"),o.a.createElement(l.a,{imgId:"evolutionCompositeCD"}),o.a.createElement("p",null,"Because Batman is ",o.a.createElement("em",null,"not exactly")," super, his ",o.a.createElement("em",null,"Strength")," and ",o.a.createElement("em",null,"Dexterity"),"is comparable to a Professional. Batman uses technology to compensate this. He dons an Armor, deploys Projectiles."),o.a.createElement("p",null,"That's ",o.a.createElement("strong",null,"Decorator Pattern")," for you in action."),o.a.createElement("p",null,"The enhanced Batman is able to stand up against the gods like Superman and Flash."),o.a.createElement("p",null,"Flash has another Super Power, Haste. As the name sounds, it makes the already fast Flash even faster for a short duration. The 'how' is revealed in the next section."),o.a.createElement(r.a,{prev:"evolutionDamages",next:"evolutionMagic"}))}}}]);
//# sourceMappingURL=7.fe897234.chunk.js.map