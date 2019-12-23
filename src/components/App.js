import React, { lazy, Suspense } from 'react';
import {HashRouter, Route, Switch} from 'react-router-dom';
import Header from './common/Header';
import Side from './common/Side'; 
import Home from './common/Home';
import Footer from './common/Footer';
import  {getRoute} from '../const/Routes';
import './App.css';

const EvolutionIntro = lazy(() => import("./evolution/EvolutionIntro"));
const EvolutionMediator = lazy(() => import("./evolution/EvolutionMediator"));
const EvolutionCommand = lazy(() => import("./evolution/EvolutionCommand"));
const EvolutionStrategy = lazy(() => import("./evolution/EvolutionStrategy"));
const EvolutionObserver = lazy(() => import("./evolution/EvolutionObserver"));
const EvolutionActionReaction = lazy(() => import("./evolution/EvolutionActionReaction"));
const EvolutionFighter = lazy(() => import("./evolution/EvolutionFighter"));
const EvolutionDecorator = lazy(() => import("./evolution/EvolutionDecorator"));
const EvolutionAdapter = lazy(() => import("./evolution/EvolutionAdapter"));
const EvolutionDamages = lazy(() => import("./evolution/EvolutionDamages"));
const EvolutionHeroes = lazy(() => import("./evolution/EvolutionHeroes"));
const EvolutionMagic = lazy(() => import("./evolution/EvolutionMagic"));
const EvolutionHits = lazy(() => import("./evolution/EvolutionHits"));
const EvolutionCreators = lazy(() => import("./evolution/EvolutionCreators"));
const EvolutionVisitors = lazy(() => import("./evolution/EvolutionVisitors"));
const EvolutionMemento = lazy(() => import("./evolution/EvolutionMemento"));
const EvolutionConclude = lazy(() => import("./evolution/EvolutionConclude"));

const LambdaIntro = lazy(() => import("./lambda/LambdaIntro"));
const LambdaStrategy = lazy(() => import("./lambda/LambdaStrategy"));
const LambdaCommand = lazy(() => import("./lambda/LambdaCommand"));
const LambdaDecorator = lazy(() => import("./lambda/LambdaDecorator"));
const LambdaLoaner = lazy(() => import("./lambda/LambdaLoaner"));
const LambdaBuilder = lazy(() => import("./lambda/LambdaBuilder"));
const LambdaMore = lazy(() => import("./lambda/LambdaMore"));

const PatternsIntro = lazy(() => import("./patterns/PatternsIntro"));
const Creational = lazy(() => import("./patterns/Creational"));
const Structural = lazy(() => import("./patterns/Structural"));
const Behavorial = lazy(() => import("./patterns/Behavorial"));

const App = () => {
  return (
    <div>
      <HashRouter>
        <div id="container">
          <Header />
          <Side />
          <Switch>
            <Route path={getRoute("home").path} exact component={Home} />

            <Route path={getRoute("evolutionIntro").path} exact component={WaitingComponent(EvolutionIntro)} />
            <Route path={getRoute("evolutionMediator").path} exact component={WaitingComponent(EvolutionMediator)} />
            <Route path={getRoute("evolutionCommand").path} exact component={WaitingComponent(EvolutionCommand)} />
            <Route path={getRoute("evolutionStrategy").path} exact component={WaitingComponent(EvolutionStrategy)} />
            <Route path={getRoute("evolutionObserver").path} exact component={WaitingComponent(EvolutionObserver)} />
            <Route path={getRoute("evolutionActionReaction").path} exact component={WaitingComponent(EvolutionActionReaction)} />
            <Route path={getRoute("evolutionFighter").path} exact component={WaitingComponent(EvolutionFighter)} />
            <Route path={getRoute("evolutionDecorator").path} exact component={WaitingComponent(EvolutionDecorator)} />
            <Route path={getRoute("evolutionAdapter").path} exact component={WaitingComponent(EvolutionAdapter)} />
            <Route path={getRoute("evolutionDamages").path} exact component={WaitingComponent(EvolutionDamages)} />
            <Route path={getRoute("evolutionHeroes").path} exact component={WaitingComponent(EvolutionHeroes)} />
            <Route path={getRoute("evolutionMagic").path} exact component={WaitingComponent(EvolutionMagic)} />
            <Route path={getRoute("evolutionHits").path} exact component={WaitingComponent(EvolutionHits)} />
            <Route path={getRoute("evolutionCreators").path} exact component={WaitingComponent(EvolutionCreators)} />
            <Route path={getRoute("evolutionVisitors").path} exact component={WaitingComponent(EvolutionVisitors)} />
            <Route path={getRoute("evolutionMemento").path} exact component={WaitingComponent(EvolutionMemento)} />
            <Route path={getRoute("evolutionConclude").path} exact component={WaitingComponent(EvolutionConclude)} />
            
            <Route path={getRoute("lambdaIntro").path} exact component={WaitingComponent(LambdaIntro)} />
            <Route path={getRoute("lambdaStrategy").path} exact component={WaitingComponent(LambdaStrategy)} />
            <Route path={getRoute("lambdaCommand").path} exact component={WaitingComponent(LambdaCommand)} />
            <Route path={getRoute("lambdaDecorator").path} exact component={WaitingComponent(LambdaDecorator)} />
            <Route path={getRoute("lambdaLoaner").path} exact component={WaitingComponent(LambdaLoaner)} />
            <Route path={getRoute("lambdaBuilder").path} exact component={WaitingComponent(LambdaBuilder)} />
            <Route path={getRoute("lambdaMore").path} exact component={WaitingComponent(LambdaMore)} />

            <Route path={getRoute("patternIntro").path} exact component={WaitingComponent(PatternsIntro)} />
            <Route path={getRoute("patternCreational").path} exact component={WaitingComponent(Creational)} />
            <Route path={getRoute("patternStructural").path} exact component={WaitingComponent(Structural)} />
            <Route path={getRoute("patternBehavorial").path} exact component={WaitingComponent(Behavorial)} />
          </Switch>
          <Footer/>
        </div>
      </HashRouter>
    </div>
  );
};

function WaitingComponent(Component) {
  return props => (
    <Suspense fallback={<div>Loading...</div>}>
      <Component {...props} />
    </Suspense>
  );
}

export default App;