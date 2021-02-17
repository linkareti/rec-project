import React from 'react';
import { Card, Row, Col } from 'react-bootstrap';

function LabManagersGrid({ labManagers }) {
  return (
    <Row>
      {labManagers.map((labManager) => (
        <Col lg={4} key={labManager.id}>
          <LabManagerCard {...labManager} />
        </Col>
      ))}
    </Row>
  );
}

function LabManagerCard({ id, name, picture, country, city }) {
  return (
    <Card>
      <Card.Img variant='top' src={picture} />
      <Card.Body>
        <Card.Title as='a'>
          <h5>{name}</h5>
        </Card.Title>
        <Card.Text>
          {city}, {country}
        </Card.Text>
      </Card.Body>
    </Card>
  );
}

export default LabManagersGrid;
