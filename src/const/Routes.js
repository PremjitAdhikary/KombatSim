const githubPrefix = 
'https://github.com/PremjitAdhikary/KombatSim/blob/master/src/main/java/practice/premjit/patterns/kombatsim/';

export const allRoutes = {
  evolution : [
    {id:"evolutionIntro",path:"/evolution/intro",name:"Introduction"},
    {id:"evolutionMediator",path:"/evolution/mediator",name:"A Basic Engine"},
    {id:"evolutionCommand",path:"/evolution/command",name:"A Hit"},
    {id:"evolutionStrategy",path:"/evolution/strategy",name:"Hit Strategy"},
    {id:"evolutionObserver",path:"/evolution/observer",name:"But When?"},
    {id:"evolutionActionReaction",path:"/evolution/together",name:"All Together"},
    {id:"evolutionFighter",path:"/evolution/fighter",name:"A Fighter"},
    {id:"evolutionDecorator",path:"/evolution/decorator",name:"Enhanced Fighters"},
    {id:"evolutionAdapter",path:"/evolution/adapter",name:"Teams"},
    {id:"evolutionDamages",path:"/evolution/damages",name:"Beyond Physical"},
    {id:"evolutionHeroes",path:"/evolution/heroes",name:"Heroes"},
    {id:"evolutionMagic",path:"/evolution/magic",name:"Magic?"},
    {id:"evolutionHits",path:"/evolution/hits",name:"Commands & Moves"},
    {id:"evolutionCreators",path:"/evolution/creators",name:"Creators"},
    {id:"evolutionVisitors",path:"/evolution/visitors",name:"Visitors"},
    {id:"evolutionMemento",path:"/evolution/memento",name:"One Moment"}
  ],
  lambda : [
    {id:"lambdaIntro",path:"/lambda/intro",name:"Introduction"},
    {id:"lambdaStrategy",path:"/lambda/strategy",name:"Lambda, Behave!"},
    {id:"lambdaCommand",path:"/lambda/command",name:"Command Lambda"},
    {id:"lambdaDecorator",path:"/lambda/decorator",name:"Compose/Decorate"},
    {id:"lambdaLoaner",path:"/lambda/loaner",name:"A Loan Builder"},
    {id:"lambdaBuilder",path:"/lambda/builder",name:"Build Restrictions"},
    {id:"lambdaMore",path:"/lambda/more",name:"What Else?"}
  ],
  patterns : [
    {id:"patternIntro",path:"/patterns/intro",name:"Introduction"},
    {id:"patternCreational",path:"/patterns/creational",name:"Creational"},
    {id:"patternStructural",path:"/patterns/structural",name:"Structural"},
    {id:"patternBehavorial",path:"/patterns/behavorial",name:"Behavorial"}
  ],
  common : [
    {id:"home",path:"/",name:"Home"}
  ],
  external : [
    // arena
    {
      id:"ArenaMediator",name:"ArenaMediator",path:githubPrefix+"arenas/ArenaMediator.java"
    },
    {
      id:"AbstractArena",name:"AbstractArena",path:githubPrefix+"arenas/AbstractArena.java"
    },
    {
      id:"MysticalForest",name:"MysticalForest",path:githubPrefix+"arenas/MysticalForest.java"
    },
    {
      id:"ArenaFactory",name:"ArenaFactory",path:githubPrefix+"arenas/ArenaFactory.java"
    },
    {
      id:"ProxyArena",name:"ProxyArena",path:githubPrefix+"fighters/teams/ProxyArena.java"
    },
    {
      id:"OffenderDefenderArena",name:"OffenderDefenderTeam.OffenderDefenderArena",path:githubPrefix+"fighters/teams/OffenderDefenderTeam.java"
    },
    {
      id:"TagTeamArena",name:"TagTeam.TagTeamArena",path:githubPrefix+"fighters/teams/TagTeam.java"
    },
    // factory
    {
      id:"FighterFactory",name:"FighterFactory",path:githubPrefix+"fighters/factory/FighterFactory.java"
    },
    {
      id:"AmateurFighters",name:"AmateurFighters",path:githubPrefix+"fighters/factory/AmateurFighters.java"
    },
    {
      id:"AmateurFighterBuilder",name:"AmateurFighters.AmateurFighterBuilder",path:githubPrefix+"fighters/factory/AmateurFighters.java"
    },
    {
      id:"ProfessionalFighters",name:"ProfessionalFighters",path:githubPrefix+"fighters/factory/ProfessionalFighters.java"
    },
    {
      id:"ProfessionalFighterBuilder",name:"ProfessionalFighters.ProfessionalFighterBuilder",path:githubPrefix+"fighters/factory/ProfessionalFighters.java"
    },
    {
      id:"EnhancedFighters",name:"EnhancedFighters",path:githubPrefix+"fighters/factory/EnhancedFighters.java"
    },
    {
      id:"EnhancedFighterBuilder",name:"EnhancedFighters.EnhancedFighterBuilder",path:githubPrefix+"fighters/factory/EnhancedFighters.java"
    },
    {
      id:"Heroes",name:"Heroes",path:githubPrefix+"fighters/factory/Heroes.java"
    },
    {
      id:"HeroBuilder",name:"Heroes.HeroBuilder",path:githubPrefix+"fighters/factory/Heroes.java"
    },
    {
      id:"Mages",name:"Mages",path:githubPrefix+"fighters/factory/Mages.java"
    },
    {
      id:"MageBuilder",name:"Mages.MageBuilder",path:githubPrefix+"fighters/factory/Mages.java"
    },
    // fighter
    {
      id:"Fighter",name:"Fighter",path:githubPrefix+"fighters/Fighter.java"
    },
    {
      id:"AbstractFighter",name:"AbstractFighter",path:githubPrefix+"fighters/AbstractFighter.java"
    },
    // adapter
    {
      id:"AbstractTeam",name:"AbstractTeam",path:githubPrefix+"fighters/teams/AbstractTeam.java"
    },
    {
      id:"OffenderDefenderTeam",name:"OffenderDefenderTeam",path:githubPrefix+"fighters/teams/OffenderDefenderTeam.java"
    },
    {
      id:"TagTeam",name:"TagTeam",path:githubPrefix+"fighters/teams/TagTeam.java"
    },
    // decorator
    {
      id:"AttributeEnhancer",name:"AttributeEnhancer",path:githubPrefix+"fighters/decorators/AttributeEnhancer.java"
    },
    {
      id:"AttributeEnhancerBuilder",name:"AttributeEnhancer.AttributEnhancerBuilder",path:githubPrefix+"fighters/decorators/AttributeEnhancer.java"
    },
    {
      id:"Armor",name:"Armor",path:githubPrefix+"fighters/decorators/Armor.java"
    },
    {
      id:"ArmorBuilder",name:"Armor.ArmorBuilder",path:githubPrefix+"fighters/decorators/Armor.java"
    },
    {
      id:"Weapon",name:"Weapon",path:githubPrefix+"fighters/decorators/Weapon.java"
    },
    {
      id:"WeaponBuilder",name:"Weapon.WeaponBuilder",path:githubPrefix+"fighters/decorators/Weapon.java"
    },
    {
      id:"Projectiles",name:"Projectiles",path:githubPrefix+"fighters/decorators/Projectiles.java"
    },
    {
      id:"ProjectilesBuilder",name:"Projectiles.ProjectilesBuilder",path:githubPrefix+"fighters/decorators/Projectiles.java"
    },
    // move
    {
      id:"PhysicalDamageBuilder",name:"PhysicalDamage.PhysicalDamageBuilder",path:githubPrefix+"moves/damages/PhysicalDamage.java"
    },
    {
      id:"FireDamageBuilder",name:"FireDamage.FireDamageBuilder",path:githubPrefix+"moves/damages/FireDamage.java"
    },
    {
      id:"ColdDamageBuilder",name:"ColdDamage.ColdDamageBuilder",path:githubPrefix+"moves/damages/ColdDamage.java"
    },
    {
      id:"ShockDamageBuilder",name:"ShockDamage.ShockDamageBuilder",path:githubPrefix+"moves/damages/ShockDamage.java"
    },
    {
      id:"AffectAttributeBuilder",name:"AffectAttribute.AffectAttributeBuilder",path:githubPrefix+"moves/buffs/AffectAttribute.java"
    },
    {
      id:"AffectVariableAttributeBuilder",name:"AffectVariableAttribute.AffectVariableAttributeBuilder",path:githubPrefix+"moves/buffs/AffectVariableAttribute.java"
    },
    // command
    {
      id:"ActionCommand",name:"ActionCommand",path:githubPrefix+"commands/ActionCommand.java"
    },
    {
      id:"ReactionCommand",name:"ReactionCommand",path:githubPrefix+"commands/ReactionCommand.java"
    },
    {
      id:"Endure",name:"Endure",path:githubPrefix+"commands/hero/Endure.java"
    },
    {
      id:"MojoActionBuilder",name:"MojoBasedAction.MojoActionBuilder",path:githubPrefix+"commands/hero/MojoBasedAction.java"
    },
    {
      id:"ActionSpellBuilder",name:"ActionSpell.ActionSpellBuilder",path:githubPrefix+"commands/magic/ActionSpell.java"
    },
    {
      id:"RelfectDamageSpellBuilder",name:"RelfectDamageSpell.RelfectDamageSpellBuilder",path:githubPrefix+"commands/magic/RelfectDamageSpell.java"
    },
    // strategy
    {
      id:"ActionStrategy",name:"ActionStrategy",path:githubPrefix+"strategies/ActionStrategy.java"
    },
    {
      id:"AbstractFighterActionStrategy",name:"AbstractFighterActionStrategy",path:githubPrefix+"strategies/AbstractFighterActionStrategy.java"
    },
    {
      id:"BatmanActionStrategy",name:"BatmanActionStrategy",path:githubPrefix+"strategies/hero/BatmanActionStrategy.java"
    },
    {
      id:"ReactionStrategy",name:"ReactionStrategy",path:githubPrefix+"strategies/ReactionStrategy.java"
    },
    {
      id:"AbstractFighterReactionStrategy",name:"AbstractFighterReactionStrategy",path:githubPrefix+"strategies/AbstractFighterReactionStrategy.java"
    },
    {
      id:"SpellBook",name:"SpellBook",path:githubPrefix+"strategies/magic/SpellBook.java"
    },
    // observer
    {
      id:"BeatObserver",name:"BeatObserver",path:githubPrefix+"beats/BeatObserver.java"
    },
    {
      id:"BeatObservable",name:"BeatObservable",path:githubPrefix+"beats/BeatObservable.java"
    },
    {
      id:"DexterityBasedActionObserver",name:"DexterityBasedActionObserver",path:githubPrefix+"beats/DexterityBasedActionObserver.java"
    },
    {
      id:"FlipFlopObserver",name:"FlipFlopObserver",path:githubPrefix+"beats/FlipFlopObserver.java"
    },
    {
      id:"VariableAttributeModifier",name:"VariableAttributeModifier",path:githubPrefix+"beats/VariableAttributeModifier.java"
    },
    {
      id:"BeatObservableImpl",name:"BeatObservableImpl",path:githubPrefix+"beats/BeatObservableImpl.java"
    },
    {
      id:"TikTok",name:"TikTok",path:githubPrefix+"beats/TikTok.java"
    },
    // visitor
    {
      id:"MoveVisitor",name:"MoveVisitor",path:githubPrefix+"visitors/MoveVisitor.java"
    },
    {
      id:"ArmorDamageAbsorber",name:"Armor.ArmorDamageAbsorber",path:githubPrefix+"fighters/decorators/Armor.java"
    },
    {
      id:"DamageReducer",name:"ReflectDamageSpell.DamageReducer",path:githubPrefix+"commands/magic/ReflectDamageSpell.java"
    },
    {
      id:"FighterVisitor",name:"FighterVisitor",path:githubPrefix+"visitors/FighterVisitor.java"
    },
    {
      id:"ColdFighterVisitor",name:"ColdDamage.ColdFighterVisitor",path:githubPrefix+"moves/damages/ColdDamage.java"
    },
    {
      id:"TracerVisitor",name:"BatmanActionStrategy.TracerVisitor",path:githubPrefix+"strategies/hero/BatmanActionStrategy.java"
    }
  ]
}

export const getRoute = (route) => {
  return [...allRoutes.evolution, ...allRoutes.lambda, ...allRoutes.patterns, 
    ...allRoutes.common, ...allRoutes.external]
    .find(r => r.id === route);
}

export default {
  allRoutes, getRoute
}