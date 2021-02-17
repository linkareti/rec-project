import React from 'react';
import { Col, Container, Row, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import HomeNav from '../components/HomeNav';

const Error = () => {
  return (
    <div className='content'>
      <HomeNav />
      <div className='page-wrap d-flex flex-row align-items-center'>
        <Container>
          <Row className='justify-content-center'>
            <Col className='col-md-12 text-center'>
              <span className='display-1 d-block'>404</span>
              <div className='mb-4 lead'>
                The page you are looking for was not found.
              </div>
              <Link to='/' className='btn btn-link'>
                Back to Home
              </Link>
            </Col>
          </Row>
        </Container>
      </div>
    </div>
  );
};

export default Error;
