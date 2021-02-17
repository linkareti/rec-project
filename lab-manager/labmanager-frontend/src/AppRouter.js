import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import { useKeycloak } from '@react-keycloak/web';

import { PrivateRoute } from './PrivateRouter';

import Home from './pages/Home';
import About from './pages/About';
import Error from './pages/Error';
import Backoffice from './pages/Backoffice';

function AppRouter() {
  console.log(useKeycloak);
  const { initialized } = useKeycloak();
  if (!initialized) {
    return <h3>Loading ... !!!</h3>;
  }

  return (
    <Router>
      {/** If the same navbar for all  the pages put it here */}
      <Switch>
        <Route exact path='/'>
          <Home></Home>
        </Route>
        <Route path='/about'>
          <About></About>
        </Route>
        <PrivateRoute
          roles={['RealmAdmin']}
          path='/backoffice'
          component={Backoffice}
        />

        {/**Other routes here*/}
        <Route path='*'>
          <Error></Error>
        </Route>
      </Switch>
    </Router>
  );
}

export default AppRouter;
