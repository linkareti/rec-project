import React from 'react';
import {
  Navbar,
  Nav,
  NavDropdown,
  Form,
  FormControl,
  Button,
} from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { useGlobalContext } from '../context';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGlobe } from '@fortawesome/free-solid-svg-icons';
import { useKeycloak } from '@react-keycloak/web';

function HomeNav() {
  const { keycloak } = useKeycloak();

  const { language, languages, setLanguage } = useGlobalContext();
  const title = (
    <>
      {language} <FontAwesomeIcon icon={faGlobe} />
    </>
  );
  return (
    <Navbar bg='white' variant='light' expand={true} className='mb-4 shadow'>
      <Link to='/' className='navbar-brand js-scroll-trigger'>
        <strong>Lab Managers</strong>
      </Link>

      <Nav className='ml-auto mr-5'>
        <NavDropdown title={title} id='language-nav-dropdown'>
          {languages.map((language, index) => {
            return (
              <NavDropdown.Item
                key={index}
                onClick={() => setLanguage(language)}
              >
                {language}
              </NavDropdown.Item>
            );
          })}
        </NavDropdown>
      </Nav>
      <LoginLogoutButton keycloak={keycloak} />
    </Navbar>
  );
}

const LoginLogoutButton = ({ keycloak }) => {
  const handleLogin = () => {
    keycloak.login();
  };

  const handleLogout = () => {
    keycloak.logout();
  };

  return (
    <>
      {!keycloak.authenticated && <Button onClick={handleLogin}>Login</Button>}
      {keycloak.authenticated && <Button onClick={handleLogout}>Logout</Button>}
    </>
  );
};

export default HomeNav;
