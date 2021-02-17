import React from 'react';
import { useKeycloak } from '@react-keycloak/web';
import { Redirect, useParams } from 'react-router-dom';

function Login() {
  const { keycloak } = useKeycloak();
  const { redirectPath } = useParams();
  console.log(redirectPath);

  if (keycloak.authenticated) {
    localStorage.setItem('keycloak', keycloak.toJSON);
    return <Redirect to={`/${redirectPath}`} />;
  }
  keycloak.login();

  return <div></div>;
}

export default Login;
