export const allImages = {
  evolution : [
    {
      id:"evolutionMediatorCD",
      path:"/evolution/Evolution-Mediator-ClassDiagram.svg",
      text:"Mediator Class Diagram"
    },
    {
      id:"evolutionMediatorSD",
      path:"/evolution/Evolution-Mediator-SequenceDiagram.svg",
      text:"Mediator Sequence Diagram"
    },
    {
      id:"evolutionActionCommandCD",
      path:"/evolution/Evolution-ActionCommand-ClassDiagram.svg",
      text:"Action Command Class Diagram"
    },
    {
      id:"evolutionActionCommandSD",
      path:"/evolution/Evolution-ActionCommand-SequenceDiagram.svg",
      text:"Action Command Sequence Diagram"
    },
    {
      id:"evolutionStrategyTemplateCD",
      path:"/evolution/Evolution-ActionStrategyTemplate-ClassDiagram.svg",
      text:"Action Strategy and Template Class Diagram"
    },
    {
      id:"evolutionStrategyTemplateSD",
      path:"/evolution/Evolution-ActionStrategyTemplate-SequenceDiagram.svg",
      text:"Action Strategy and Template Sequence Diagram"
    },
    {
      id:"evolutionBridgeCD",
      path:"/evolution/Evolution-StrategyCommandBridge-ClassDiagram.svg",
      text:"Bridge Class Diagram"
    },
    {
      id:"evolutionObserverCD",
      path:"/evolution/Evolution-BeatObserver-ClassDiagram.svg",
      text:"Observer Class Diagram"
    },
    {
      id:"evolutionActionObserverSD",
      path:"/evolution/Evolution-ActionObserver-SequenceDiagram.svg",
      text:"Action Observer Sequence Diagram"
    },
    {
      id:"evolutionTikTokSD",
      path:"/evolution/Evolution-TikTok-SequenceDiagram.svg",
      text:"Tik Tok Sequence Diagram"
    },
    {
      id:"evolutionActionSD",
      path:"/evolution/Evolution-Action-SequenceDiagram.svg",
      text:"Action Sequence Diagram"
    },
    {
      id:"evolutionReactionSD",
      path:"/evolution/Evolution-Reaction-SequenceDiagram.svg",
      text:"Reaction Sequence Diagram"
    },
    {
      id:"evolutionDecoratorCD",
      path:"/evolution/Evolution-Decorator-ClassDiagram.svg",
      text:"Decorator Class Diagram"
    },
    {
      id:"evolutionDecoratorSD",
      path:"/evolution/Evolution-Decorator-SequenceDiagram.svg",
      text:"Decorator Sequence Diagram"
    },
    {
      id:"evolutionAdapterCD",
      path:"/evolution/Evolution-Adapter-ClassDiagram.svg",
      text:"Adapter Class Diagram"
    },
    {
      id:"evolutionAdapterSD",
      path:"/evolution/Evolution-Adapter-SequenceDiagram.svg",
      text:"Adapter Sequence Diagram"
    },
    {
      id:"evolutionCompositeCD",
      path:"/evolution/Evolution-Composite-ClassDiagram.svg",
      text:"Composite Class Diagram"
    },
    {
      id:"evolutionMoveVisitorCD",
      path:"/evolution/Evolution-MoveVisitor-ClassDiagram.svg",
      text:"Move Visitor Class Diagram"
    },
    {
      id:"evolutionMoveVisitorSD",
      path:"/evolution/Evolution-MoveVisitor-SequenceDiagram.svg",
      text:"Move Visitor Sequence Diagram"
    },
    {
      id:"evolutionFighterVisitorSD",
      path:"/evolution/Evolution-FighterVisitor-SequenceDiagram.svg",
      text:"Fighter Visitor Sequence Diagram"
    }
  ],
  hierarchy : [
    {
      id:"hierarchyMove",
      path:"/hierarchy/Hierarchy-Move.svg",
      text:"Class Hierarchy of Moves"
    },
    {
      id:"hierarchyActionCommand",
      path:"/hierarchy/Hierarchy-ActionCommand.svg",
      text:"Class Hierarchy of ActionCommand"
    },
    {
      id:"hierarchyReactionCommand",
      path:"/hierarchy/Hierarchy-ReactionCommand.svg",
      text:"Class Hierarchy of ReactionCommand"
    },
    {
      id:"hierarchyFighter",
      path:"/hierarchy/Hierarchy-Fighter.svg",
      text:"Class Hierarchy of Fighter"
    }
  ]
}

export const getImage = (image) => {
  return [...allImages.evolution, ...allImages.hierarchy].find(i => i.id === image);
}

export default {
  allImages, getImage
}