// AuthContext.js
import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export const useAuth = () => {
    return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userId, setUserId] = useState(null);
    const [token, setToken] = useState(null); // Added token state

    useEffect(() => {
        const storedToken = localStorage.getItem('token');
        const storedUserId = localStorage.getItem('userId');
        setIsLoggedIn(!!storedToken);
        setUserId(storedUserId);
        setToken(storedToken); // Initialize token state
    }, []);

    const login = (token, id) => {
        console.log('Logging in with:', token, id);
        localStorage.setItem('token', token);
        localStorage.setItem('userId', id);
        setIsLoggedIn(true);
        setUserId(id);
        setToken(token); // Update token state
    };

    const logout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('userId');
        setIsLoggedIn(false);
        setUserId(null);
        setToken(null); // Clear token state
    };

    return (
        <AuthContext.Provider value={{ isLoggedIn, userId, token, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};
