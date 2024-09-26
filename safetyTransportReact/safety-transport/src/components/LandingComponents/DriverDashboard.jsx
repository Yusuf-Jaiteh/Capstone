// DriverDashboard.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../AuthContext';
import './DriverDashboard.css';
import Navbar from '../Navbar';

function DriverDashboard() {
    const { userId, token } = useAuth();
    const [appointments, setAppointments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');

    useEffect(() => {
        if (userId) {
            console.log('Driver ID:', userId); 
            fetchAppointments();
        } else {
            setError(new Error("User ID is not available."));
            setLoading(false);
        }
    }, [userId]);

    const fetchAppointments = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/appointment/driver/${userId}`);
            setAppointments(response.data);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching appointments:', error);
            setError(error);
            setLoading(false);
        }
    };

    const handleApprove = async (appointmentId) => {
        const appointmentToApprove = appointments.find(app => app.appointmentId === appointmentId);
        console.log('Appointment to be updated:', appointmentToApprove); 

        try {
            await axios.put(`http://localhost:8080/api/appointment/${appointmentId}`, { appointmentId, ...appointmentToApprove, approved: 1 }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }});
            setAppointments((prevAppointments) =>
                prevAppointments.map((appointment) =>
                    appointment.appointmentId === appointmentId
                        ? { ...appointment, approved: 1 }
                        : appointment
                )
            );
            setSuccessMessage('Appointment approved successfully!');
        } catch (error) {
            console.error('Error approving appointment:', error);
            setError(error);
        }
    };

    useEffect(() => {
        if (successMessage) {
            const timer = setTimeout(() => {
                setSuccessMessage('');
            }, 3000);

            return () => clearTimeout(timer);
        }
    }, [successMessage]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error.message || "Something went wrong"}</div>;
    }

    return (
        <div className="driver-dashboard">
            <Navbar />
            <header className="dashboard-header">
                <h1>Driver Dashboard</h1>
            </header>
            <div className="dashboard-content">
                <div className="dashboard-sidebar">
                    <nav>
                        <ul>
                            <li><a href="#appointments">Appointments</a></li>
                            <li><a href="#profile">Profile</a></li>
                            <li><a href="#settings">Settings</a></li>
                            <li><a href="#view-reviews">View Reviews</a></li>
                        </ul>
                    </nav>
                </div>
                <div className="dashboard-main">
                    <h2>My Appointments</h2>
                    {successMessage && <div className="success-message">{successMessage}</div>}
                    {appointments.length === 0 && (
                        <div className="no-appointments-message">
                            <p>You don't have any appointments yet.</p>
                        </div>
                    )}
                    <ul>
                        {appointments.map(appointment => (
                            <li key={appointment.appointmentId}>
                                <div><strong>Pickup Location:</strong> {appointment.pickUpLocation}</div>
                                <div><strong>Dropoff Location:</strong> {appointment.dropOffLocation}</div>
                                <div><strong>Appointment Date:</strong> {appointment.appointmentDate}</div>
                                <div><strong>Start Time:</strong> {appointment.startTime}</div>
                                <div><strong>End Time:</strong> {appointment.endTime}</div>
                                <div>
                                    <strong>Approved:</strong>
                                    {appointment.approved ? " Yes" : 
                                        <span 
                                            onClick={() => handleApprove(appointment.appointmentId)} 
                                            style={{ cursor: 'pointer', color: 'blue' }}
                                        >
                                            No (click to approve)
                                        </span>
                                    }
                                </div>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default DriverDashboard;
