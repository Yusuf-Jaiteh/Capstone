import React, { useState } from 'react';
import DriverSignUp from './DriverSignUp';
import CustomerSignUp from './CustomerSignUp';
import './UserTypeSelection.css';

function UserTypeSelection() {
    const [userType, setUserType] = useState(null);

    const handleUserTypeSelection = (type) => {
        setUserType(type);
    };

    if (userType === 'driver') {
        return <DriverSignUp />;
    } else if (userType === 'customer') {
        return <CustomerSignUp />;
    }

    return (
        <div className="user-type-selection">
            <h1>Sign Up</h1>
            <p>Please select your user type:</p>
            <div className="button-group">
                <button className="user-type-button" onClick={() => handleUserTypeSelection('driver')}>Driver</button>
                <button className="user-type-button" onClick={() => handleUserTypeSelection('customer')}>Customer</button>
            </div>
        </div>
    );
}

export default UserTypeSelection;