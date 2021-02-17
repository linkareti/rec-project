import React, { useState, useContext, useEffect, useCallback } from 'react';
import { data } from './data';

const url = 'http://localhost:8080/api';
const AppContext = React.createContext();

const languages = ['English', 'Espanõl', 'Français', 'Português'];

const AppProvider = ({ children }) => {
  const [authenticatedUser, setAuthenticatedUser] = useState(null);
  const [language, setLanguage] = useState('English');
  const [loading, setLoading] = useState(true);
  const [labManagers, setLabManagers] = useState([]);

  const fetchLabs = async () => {
    setLoading(true);
    try {
      const response = await fetch(url);
      const newData = await response.json();
      setLoading(false);
      setLabManagers(newData);
    } catch (error) {
      console.log(error);
      setLoading(false);
      setLabManagers(data.labs);
    }
  };

  useEffect(() => {
    fetchLabs();
  }, []);

  return (
    <AppContext.Provider
      value={{
        authenticatedUser,
        setAuthenticatedUser,
        languages,
        language,
        setLanguage,
        loading,
        labManagers,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};

export const useGlobalContext = () => {
  return useContext(AppContext);
};

export { AppContext, AppProvider };
