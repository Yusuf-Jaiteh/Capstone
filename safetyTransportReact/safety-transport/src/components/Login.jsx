import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext';
import './Login.css';

function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const { login } = useAuth(); // Get the login function from AuthContext
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const loginData = { username, password };

        try {
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(loginData),
            });

            const result = await response.json();
            console.log('API Response:', result);

            if (response.ok) {
                // Extract token, userId, and role from the response
                const { jwt, userId, role } = result; // Ensure that role is fetched from the API response
                console.log('User ID:', userId);
                console.log('User Role:', role); // Log the user role

                // Call the login function to store the token, userId, and role
                login(jwt, userId, role);
                
                // Redirect based on role
                if (role === 'customer') {
                    navigate('/customer-dashboard');
                } else if (role === 'driver') {
                    navigate('/driver-dashboard');
                }
            } else {
                setErrorMessage('Failed to login. Please check your credentials.');
            }
        } catch (error) {
            console.error('Error:', error);
            setErrorMessage('An error occurred during login.');
        }
    };

    return (
        <div className="login-container">
            <h1>Login</h1>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        name="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Login</button>
            </form>
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            <Link to="/signup">Don't have an account? Sign up</Link>
        </div>
    );
}

export default Login;
