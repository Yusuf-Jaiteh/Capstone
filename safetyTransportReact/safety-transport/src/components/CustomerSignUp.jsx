import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function CustomerSignUp() {
    const authorities = "customer";
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const customerData = { username, password , authorities };

        console.log('Submitting customer data:', customerData); // Debugging

        try {
            const response = await fetch('http://localhost:8080/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(customerData)
            });

            if (response.ok) {
                const result = await response.json();
                console.log('Customer signed up successfully:', result);
                navigate("/login"); 
            } else {
                const errorText = await response.text(); // Get error text from response
                console.error('Failed to sign up customer:', errorText);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <div>
            <h1>Customer Sign Up</h1>
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
                <button type="submit">Sign Up</button>
            </form>
        </div>
    );
}

export default CustomerSignUp;