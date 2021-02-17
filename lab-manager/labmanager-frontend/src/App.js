import React from 'react';
import { ReactKeycloakProvider } from '@react-keycloak/web';
import keycloak from './keycloak';
import { AppProvider } from './context';

import AppRouter from './AppRouter';

import './App.css';

function App() {
  return (
    <ReactKeycloakProvider authClient={keycloak}>
      <AppProvider>
        <AppRouter />
      </AppProvider>
    </ReactKeycloakProvider>
  );
}

export default App;
