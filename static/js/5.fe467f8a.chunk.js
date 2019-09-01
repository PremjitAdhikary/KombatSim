(window.webpackJsonp=window.webpackJsonp||[]).push([[5],{37:function(e,t,a){"use strict";var n=a(0),r=a.n(n),i=a(6),o=a(1),l=(a(38),function(e,t){return e?r.a.createElement(i.c,{className:"btn",to:Object(o.b)(e).path},t):r.a.createElement("span",{className:"btn-disabled"},t)});t.a=function(e){return r.a.createElement("div",{className:"navs"},r.a.createElement("span",{className:"left"},l(e.prev,"Previous")),r.a.createElement("span",{className:"right"},l(e.next,"Next")))}},38:function(e,t,a){},39:function(e,t,a){"use strict";var n=a(8),r=a(0),i=a.n(r),o=(a(40),["abstract","assert","boolean","break","byte","case","catch","char","class","const","do","double","else","enum","extends","final","finally","float","for","goto","if","implements","import","instanceof","int","interface","long","native","new","package","private","protected","public","return","short","static","strictfp","super","switch","synchronized","this","throw","throws","transient","try","void","volatile","while","true","false","null"]),l=["Override"],c=function(e){return e=function(e){var t=e,a=t.match(/\/\*[\s\S]*?\*\/|([^:]|^)\/\/.*$/gm);if(a){var r=Object(n.a)(a);r.forEach(function(e){return t=t.replace(e,'<span style="color: lightgray; font-style: italic;">'+e+"</span>")})}return t}(e=function(e){var t=e;return l.forEach(function(e){return t=t.replace(new RegExp("@\\b"+e+"\\b","g"),'<span style="color: lightgray; font-weight: bold;">@'+e+"</span>")}),t}(e=function(e){var t=e;return o.forEach(function(e){return t=t.replace(new RegExp("\\b"+e+"\\b","g"),'<span style="color: yellow; font-weight: bold;">'+e+"</span>")}),t}(e)))};t.a=function(e){var t={__html:"<pre>"+c(e.code)+"</pre>"};return i.a.createElement("div",{dangerouslySetInnerHTML:t})}},40:function(e,t,a){},41:function(e,t,a){},42:function(e,t,a){"use strict";function n(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var a=[],n=!0,r=!1,i=void 0;try{for(var o,l=e[Symbol.iterator]();!(n=(o=l.next()).done)&&(a.push(o.value),!t||a.length!==t);n=!0);}catch(c){r=!0,i=c}finally{try{n||null==l.return||l.return()}finally{if(r)throw i}}return a}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance")}()}var r=a(0),i=a.n(r),o=a(8),l={evolution:[{id:"evolutionMediatorCD",path:"/evolution/Evolution-Mediator-ClassDiagram.svg",text:"Mediator Class Diagram"},{id:"evolutionMediatorSD",path:"/evolution/Evolution-Mediator-SequenceDiagram.svg",text:"Mediator Sequence Diagram"},{id:"evolutionActionCommandCD",path:"/evolution/Evolution-ActionCommand-ClassDiagram.svg",text:"Action Command Class Diagram"},{id:"evolutionActionCommandSD",path:"/evolution/Evolution-ActionCommand-SequenceDiagram.svg",text:"Action Command Sequence Diagram"},{id:"evolutionStrategyTemplateCD",path:"/evolution/Evolution-ActionStrategyTemplate-ClassDiagram.svg",text:"Action Strategy and Template Class Diagram"},{id:"evolutionStrategyTemplateSD",path:"/evolution/Evolution-ActionStrategyTemplate-SequenceDiagram.svg",text:"Action Strategy and Template Sequence Diagram"},{id:"evolutionBridgeCD",path:"/evolution/Evolution-StrategyCommandBridge-ClassDiagram.svg",text:"Bridge Class Diagram"},{id:"evolutionObserverCD",path:"/evolution/Evolution-BeatObserver-ClassDiagram.svg",text:"Observer Class Diagram"},{id:"evolutionActionObserverSD",path:"/evolution/Evolution-ActionObserver-SequenceDiagram.svg",text:"Action Observer Sequence Diagram"},{id:"evolutionTikTokSD",path:"/evolution/Evolution-TikTok-SequenceDiagram.svg",text:"Tik Tok Sequence Diagram"},{id:"evolutionActionSD",path:"/evolution/Evolution-Action-SequenceDiagram.svg",text:"Action Sequence Diagram"},{id:"evolutionReactionSD",path:"/evolution/Evolution-Reaction-SequenceDiagram.svg",text:"Reaction Sequence Diagram"},{id:"evolutionDecoratorCD",path:"/evolution/Evolution-Decorator-ClassDiagram.svg",text:"Decorator Class Diagram"},{id:"evolutionDecoratorSD",path:"/evolution/Evolution-Decorator-SequenceDiagram.svg",text:"Decorator Sequence Diagram"},{id:"evolutionAdapterCD",path:"/evolution/Evolution-Adapter-ClassDiagram.svg",text:"Adapter Class Diagram"},{id:"evolutionAdapterSD",path:"/evolution/Evolution-Adapter-SequenceDiagram.svg",text:"Adapter Sequence Diagram"},{id:"evolutionCompositeCD",path:"/evolution/Evolution-Composite-ClassDiagram.svg",text:"Composite Class Diagram"},{id:"evolutionMoveVisitorCD",path:"/evolution/Evolution-MoveVisitor-ClassDiagram.svg",text:"Move Visitor Class Diagram"},{id:"evolutionMoveVisitorSD",path:"/evolution/Evolution-MoveVisitor-SequenceDiagram.svg",text:"Move Visitor Sequence Diagram"},{id:"evolutionFighterVisitorSD",path:"/evolution/Evolution-FighterVisitor-SequenceDiagram.svg",text:"Fighter Visitor Sequence Diagram"}],hierarchy:[{id:"hierarchyMove",path:"/hierarchy/Hierarchy-Move.svg",text:"Class Hierarchy of Moves"},{id:"hierarchyActionCommand",path:"/hierarchy/Hierarchy-ActionCommand.svg",text:"Class Hierarchy of ActionCommand"},{id:"hierarchyReactionCommand",path:"/hierarchy/Hierarchy-ReactionCommand.svg",text:"Class Hierarchy of ReactionCommand"},{id:"hierarchyFighter",path:"/hierarchy/Hierarchy-Fighter.svg",text:"Class Hierarchy of Fighter"}]},c=function(e){return[].concat(Object(o.a)(l.evolution),Object(o.a)(l.hierarchy)).find(function(t){return t.id===e})},u=(a(41),function(e){if(!e)return"large-content";switch(e){case"medium":return"large-content-med";case"large":return"large-content-full";case"small":default:return"large-content"}});t.a=function(e){var t=c(e.imgId),a=n(Object(r.useState)(!0),2),o=a[0],l=a[1];return i.a.createElement("div",{className:"center"},i.a.createElement("div",{className:"regular",onClick:function(){return l(!1)}},i.a.createElement("img",{src:""+t.path,alt:t.text}),i.a.createElement("div",null,t.text)),i.a.createElement("div",{className:"large",style:{display:o?"none":"block"}},i.a.createElement("span",{className:"close",onClick:function(){return l(!0)}},"\xd7"),i.a.createElement("img",{className:u(e.imgSize),alt:t.text,src:""+t.path}),i.a.createElement("div",{className:"caption"},t.text)))}},57:function(e,t,a){"use strict";a.r(t);var n=a(0),r=a.n(n),i=a(37),o=a(42),l=a(39);t.default=function(){return r.a.createElement("div",{className:"main"},r.a.createElement("div",{className:"pageTitle"},"How To Create So Many Fighters?"),r.a.createElement("p",null,"Till now all the sections talked about were Design Patterns which were of Structural or Behavioral in nature."),r.a.createElement("p",null,"What about the Creational Patterns?"),r.a.createElement("p",null," Before starting on the creation part, let's check what is being created."),r.a.createElement("p",null,"Below image is a view of all possible Fighter Types available."),r.a.createElement(o.a,{imgId:"hierarchyFighter",imgSize:"large"}),r.a.createElement("p",null,"Note that each Fighter type has multiple subtypes.",r.a.createElement("ul",null,r.a.createElement("li",null,"For ",r.a.createElement("code",null,"Amateur"),", there is Bully, Nerd and Captain"),r.a.createElement("li",null,"For ",r.a.createElement("code",null,"Professional"),", there is Boxer, Karateka and Taekwondo"),r.a.createElement("li",null,"For ",r.a.createElement("code",null,"Hero"),", there is Superman, Flash and Batman"),r.a.createElement("li",null,"For ",r.a.createElement("code",null,"Mage"),", there is Elemental and Dark"),r.a.createElement("li",null,"For Enhanced Fighters (",r.a.createElement("code",null,"Professional")," fighters enhanced by Decorators), there is Metal-armed Boxer, Samurai and Ninja"))),r.a.createElement("p",null,"Let's start with ",r.a.createElement("strong",null,"Factory Pattern")," to have dedicated Factories for each of the Fighter types. So there is Amateur Fighter Factory, Professional Fighter Factory, Hero Fighter Factory, Mage Fighter Factory, Enhanced Fighter Factory."),r.a.createElement("p",null,"Each of these factories are ",r.a.createElement("strong",null,"Singleton")," and housing all of them there is the Factory of Factoies ",r.a.createElement("code",null,"FighterFactory")," following the ",r.a.createElement("strong",null,"Abstract Factory Pattern"),"."),r.a.createElement("p",null,"Moreover each of the factories internally uses ",r.a.createElement("strong",null,"Builder Pattern")," to build the fighter subtypes."),r.a.createElement(l.a,{code:'\n\n  public abstract class FighterFactory {\n\n    public static final FighterFactory getFactory(String fighterType) {\n      // based on fightertype (Amateur, Professional, etc) find a factory\n      return fighterFactory;\n    }\n\n    public abstract Fighter getFighter(\n      String fighterSubtype, ArenaMediator arena, String name);\n\n  }\n\n\n\n  public class AmateurFighters extends FighterFactory {\n\n    public static final String FACTORY = "Amateur";\n    public static final String BULLY = "Bully";\n    public static final String NERD = "Nerd";\n    public static final String CAPTAIN = "Captain";\n\n    private AmateurFighters() {\n      // singleton\n    }\n\n    @Override\n    public Fighter getFighter(\n        String fighterSubtype, ArenaMediator arena, String name) {\n      switch (fighterSubtype) {\n        case NERD:\n            return new AmateurFighterBuilder(arena, name, fighterSubtype)\n                        .withLife(20)\n                        .withStrength(10)\n                        .withDexterity(20)\n                        .withKick()\n                        .withEvade()\n                        .build();\n        case BULLY:\n            return new AmateurFighterBuilder(arena, name, fighterSubtype)\n                        .withLife(60)\n                        ...\n                        .build();\n        case CAPTAIN:\n            ...\n      }\n    }\n\n\n    // internal builder\n    private static class AmateurFighterBuilder {\n\n      ArenaMediator arena;\n      String name;\n      String fighterSubtype;\n      double strength, dexterity, life;\n      boolean punch, kick, block, evade;\n      ...\n\n      AmateurFighterBuilder(\n          ArenaMediator arena, String name, String fighterSubtype) {\n        ...\n      }\n\n      AmateurFighterBuilder withDexterity(double dexterity) {\n        this.dexterity = dexterity;\n        return this;\n      }\n\n      ...\n\n      AmateurFighterBuilder withPunch() {\n        punch = true;\n        return this;\n      }\n\n      ...\n\n      Amateur build() {\n        Amateur fighter = new Amateur(name, arena, fighterSubtype);\n        fighter.addDexterity(dexterity);\n        ...\n        \n        if (punch)\n          fighter.addAction(new Punch(fighter));\n\n        ...\n\n        return fighter;\n      }\n\n    }\n\n  }\n\n  // Setting it up\n  Fighter nerd = FighterFactory.getFactory(AmateurFighters.FACTORY)\n                    .getFighter(AmateurFighters.NERD, arena, "Some Nerd");\n\n'}),r.a.createElement(i.a,{prev:"evolutionHits",next:"evolutionVisitors"}))}}}]);
//# sourceMappingURL=5.fe467f8a.chunk.js.map