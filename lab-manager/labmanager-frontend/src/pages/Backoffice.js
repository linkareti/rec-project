import React from 'react';
import { useKeycloak } from '@react-keycloak/web';

function Backoffice() {
  const { initialized } = useKeycloak();

  return <div>Authenticated</div>;
}

export default Backoffice;
