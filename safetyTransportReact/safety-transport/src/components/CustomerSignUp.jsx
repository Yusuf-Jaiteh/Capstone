import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function CustomerSignUp() {
    const authorities = "customer";
    const email = "";
    const gender = "";
    const [dob, setDob] = useState(''); // State for Date of Birth
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState(''); 
    const [lastName, setLastName] = useState('');   
    const [phoneNumber, setPhoneNumber] = useState(''); 
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const registerData = { username, password, authorities }; 

        console.log('Submitting customer data:', registerData); // Debugging

        try {
            const response = await fetch('http://localhost:8080/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(registerData)
            });

            if (response.ok) {
                const result = await response.json();
                console.log('Customer signed up successfully:', result);
                
            } else {
                const errorText = await response.text(); 
                console.error('Failed to sign up customer:', errorText);
            }

            const customerData = { firstName, lastName, phoneNumber, email, dob, gender }; // Include dob here
            console.log('Submitting customer data:', customerData); // Debugging

            const customerResponse = await fetch('http://localhost:8080/api/customer', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(customerData)
            });

            console.log('Customer Response Status:', customerResponse.status); // Log the driver response status

            if (customerResponse.ok && response.ok) {
                const result = await customerResponse.json();
                console.log('Customer details submitted successfully:', result);
                navigate("/login"); 
            } else {
                const errorText = await customerResponse.text(); // Fix variable name
                console.error('Customer Submission Error:', errorText); // Log error response
                setErrorMessage(`Failed to submit customer details: ${errorText}`);
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
                    <label htmlFor="firstName">First Name:</label>
                    <input
                        type="text"
                        id="firstName"
                        name="firstName"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="lastName">Last Name:</label>
                    <input
                        type="text"
                        id="lastName"
                        name="lastName"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        required
                    />
                </div>
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
                <div className="form-group">
                    <label htmlFor="phoneNumber">Phone Number:</label>
                    <input
                        type="tel"
                        id="phoneNumber"
                        name="phoneNumber"
                        value={phoneNumber}
                        onChange={(e) => setPhoneNumber(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="dob">Date of Birth:</label>
                    <input
                        type="date"
                        id="dob"
                        name="dob"
                        value={dob}
                        onChange={(e) => setDob(e.target.value)} // Handle change for dob
                        required
                    />
                </div>
                <button type="submit">Sign Up</button>
            </form>
        </div>
    );
}

export default CustomerSignUp;
