import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './DriverSignUp.css'; // Import your custom CSS file

function DriverSignUp() {
    const authorities = "driver";
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [licenseNumber, setLicenseNumber] = useState('');
    const [carModel, setCarModel] = useState('');
    const [numberPlate, setNumberPlate] = useState('');
    const [dob, setDob] = useState('');
    const [gender, setGender] = useState('');
    const [residentialAddress, setResidentialAddress] = useState('');
    const [yearsOfExperience, setYearsOfExperience] = useState('');
    const [licenseExpiryDate, setLicenseExpiryDate] = useState('');
    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setErrorMessage('');

        // Log all state values for debugging
        console.log('Form Values:', {
            username,
            password,
            firstName,
            lastName,
            email,
            phoneNumber,
            licenseNumber,
            carModel,
            numberPlate,
            dob,
            gender,
            residentialAddress,
            yearsOfExperience,
            licenseExpiryDate,
            authorities
        });

        const registerData = { username, password, authorities };
        console.log('Register Data:', registerData);  // Log the data being sent

        try {
            const registerResponse = await fetch('http://localhost:8080/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(registerData)
            });

            console.log('Register Response Status:', registerResponse.status); // Log the response status

            if (!registerResponse.ok) {
                const errorText = await registerResponse.text();
                console.error('Register Response Error:', errorText); // Log error response
                setErrorMessage(`Failed to register user: ${errorText}`);
                setLoading(false);
                return;
            }

            // If registration is successful
            const driverData = {
                firstName,
                lastName,
                email,
                phoneNumber,
                licenseNumber,
                carModel,
                numberPlate,
                dob,
                gender,
                residentialAddress,
                yearsOfExperience,
                licenseExpiryDate
            };
            console.log('Driver Data:', driverData);  // Log driver data being sent

            const driverResponse = await fetch('http://localhost:8080/api/driver', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(driverData)
            });

            console.log('Driver Response Status:', driverResponse.status); // Log the driver response status

            if (driverResponse.ok) {
                const result = await driverResponse.json();
                console.log('Driver details submitted successfully:', result);
                navigate("/login"); 
            } else {
                const errorText = await driverResponse.text();
                console.error('Driver Submission Error:', errorText); // Log error response
                setErrorMessage(`Failed to submit driver details: ${errorText}`);
            }
        } catch (error) {
            console.error('Submission error:', error); // Log the error
            setErrorMessage(`Error: ${error.message}`);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container my-5">
            <h1 className="text-center mb-4">Driver Sign Up</h1>
            {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}
            <form onSubmit={handleSubmit} className="shadow p-4 rounded bg-light border border-success">
                <div className="row">
                    <div className="col-md-6 mb-3">
                        <div className="form-group">
                            <label htmlFor="username">Username:</label>
                            <input
                                type="text"
                                id="username"
                                className="form-control"
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
                                className="form-control"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="firstName">First Name:</label>
                            <input
                                type="text"
                                id="firstName"
                                className="form-control"
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
                                className="form-control"
                                value={lastName}
                                onChange={(e) => setLastName(e.target.value)}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="email">Email:</label>
                            <input
                                type="email"
                                id="email"
                                className="form-control"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="phoneNumber">Phone Number:</label>
                            <input
                                type="text"
                                id="phoneNumber"
                                className="form-control"
                                value={phoneNumber}
                                onChange={(e) => setPhoneNumber(e.target.value)}
                                required
                            />
                        </div>
                    </div>
                    <div className="col-md-6 mb-3">
                        <div className="form-group">
                            <label htmlFor="licenseNumber">License Number:</label>
                            <input
                                type="text"
                                id="licenseNumber"
                                className="form-control"
                                value={licenseNumber}
                                onChange={(e) => setLicenseNumber(e.target.value)}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="carModel">Car Model:</label>
                            <input
                                type="text"
                                id="carModel"
                                className="form-control"
                                value={carModel}
                                onChange={(e) => setCarModel(e.target.value)}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="numberPlate">Number Plate:</label>
                            <input
                                type="text"
                                id="numberPlate"
                                className="form-control"
                                value={numberPlate}
                                onChange={(e) => setNumberPlate(e.target.value)}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="dob">Date of Birth:</label>
                            <input
                                type="date"
                                id="dob"
                                className="form-control"
                                value={dob}
                                onChange={(e) => setDob(e.target.value)}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="gender">Gender:</label>
                            <select
                                id="gender"
                                className="form-control"
                                value={gender}
                                onChange={(e) => setGender(e.target.value)}
                                required
                            >
                                <option value="">Select Gender</option>
                                <option value="male">Male</option>
                                <option value="female">Female</option>
                                <option value="other">Other</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div className="form-group mb-3">
                    <label htmlFor="residentialAddress">Residential Address:</label>
                    <input
                        type="text"
                        id="residentialAddress"
                        className="form-control"
                        value={residentialAddress}
                        onChange={(e) => setResidentialAddress(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group mb-3">
                    <label htmlFor="yearsOfExperience">Years of Experience:</label>
                    <input
                        type="text"
                        id="yearsOfExperience"
                        className="form-control"
                        value={yearsOfExperience}
                        onChange={(e) => setYearsOfExperience(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group mb-4">
                    <label htmlFor="licenseExpiryDate">License Expiry Date:</label>
                    <input
                        type="date"
                        id="licenseExpiryDate"
                        className="form-control"
                        value={licenseExpiryDate}
                        onChange={(e) => setLicenseExpiryDate(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-primary btn-block" disabled={loading}>
                    {loading ? (
                        <>
                            <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                            Loading...
                        </>
                    ) : (
                        'Sign Up'
                    )}
                </button>
            </form>
        </div>
    );
}

export default DriverSignUp;
