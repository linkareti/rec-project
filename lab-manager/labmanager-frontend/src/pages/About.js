import React from 'react';
import { Container, Row } from 'react-bootstrap';
import HomeNav from '../components/HomeNav';

const About = () => {
  return (
    <div className='content'>
      <HomeNav />
      <Container className='justify-content-center'>
        <h2>About</h2>
      </Container>
    </div>
  );
};

export default About;
