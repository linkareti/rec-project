import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import HomeNav from '../components/HomeNav';
import { useGlobalContext } from '../context';
import Loading from '../components/Loading';
import LabManagerTabs from '../components/labmanagerstabs/LabManagerTabs';

const Home = () => {
  const { loading } = useGlobalContext();

  if (loading) {
    return (
      <div className='content'>
        <HomeNav />
        <Loading />
      </div>
    );
  }

  return (
    <div className='content'>
      <HomeNav />
      <Container>
        <Row className='mb-3'>
          <Col lg={3}>
            <h1 className='h3 mt-4'>Select a Lab Manager</h1>
            <p className='mb-5 text-secondary'>Enter a Lab Manager to access</p>
          </Col>
          <Col lg={9} className='mt-4'>
            <LabManagerTabs />
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default Home;
