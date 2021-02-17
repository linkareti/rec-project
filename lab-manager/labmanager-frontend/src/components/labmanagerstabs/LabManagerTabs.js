import React from 'react';
import { Tabs, Tab } from 'react-bootstrap';
import LabManagersGrid from './LabManagersGrid';
import LabManagersList from './LabManagersList';
import LabManagersMap from './LabManagersMap';
import { useGlobalContext } from '../../context';

function LabManagerTabs() {
  const [key, setKey] = React.useState('grid');
  const { labManagers } = useGlobalContext();
  return (
    <Tabs
      defaultActiveKey='grid'
      id='labmanagers-tabs'
      onSelect={(k) => setKey(k)}
      className='justify-content-end mb-3'
    >
      <Tab eventKey='grid' title='Grid'>
        <LabManagersGrid labManagers={labManagers} />
      </Tab>
      <Tab eventKey='list' title='List'>
        <LabManagersList labManagers={labManagers} />
      </Tab>
      <Tab eventKey='map' title='Map'>
        <LabManagersMap labManagers={labManagers} />
      </Tab>
    </Tabs>
  );
}

export default LabManagerTabs;
