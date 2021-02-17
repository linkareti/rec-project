import React from 'react';
import { ListGroup, Card } from 'react-bootstrap';

function LabManagersList({ labManagers }) {
  return (
    <ListGroup>
      {labManagers.map((labManager) => {
        const { id, name, city, country } = labManager;
        return (
          <ListGroup.Item key={id} className='d-flex justify-content-between'>
            <a href={`/labmanager/${id}`}>
              <h5 className='card-title'>{name}</h5>
            </a>
            <p>
              {city}, {country}
            </p>
          </ListGroup.Item>
        );
      })}
    </ListGroup>
  );
}

export default LabManagersList;
