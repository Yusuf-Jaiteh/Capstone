// App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignUp from './components/Signup';
import Login from './components/Login';
import LandingPage from './components/LandingPage';
import CustomerDashboard from './components/LandingComponents/CustomerDashboard';
import DriverDashboard from './components/LandingComponents/DriverDashboard'; // Import DriverDashboard
import { AuthProvider } from './components/AuthContext'; // Import AuthProvider
import './App.css';

function App() {
    return (
        <AuthProvider>
            <Router>
                <div className="App">
                    <Routes>
                        <Route path="/" element={<LandingPage />} />
                        <Route path="/signup" element={<SignUp />} />
                        <Route path="/login" element={<Login />} />
                        <Route path="/customer-dashboard" element={<CustomerDashboard />} />
                        <Route path="/driver-dashboard" element={<DriverDashboard />} /> {/* Add route for DriverDashboard */}
                    </Routes>
                </div>
            </Router>
        </AuthProvider>
    );
}

export default App;
