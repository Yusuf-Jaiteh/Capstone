import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../AuthContext';
import './CustomerDashboard.css';
import Navbar from '../Navbar';

function CustomerDashboard() {
    const { userId, token } = useAuth();
    const [appointments, setAppointments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [editMode, setEditMode] = useState(false);
    const [newAppointment, setNewAppointment] = useState({
        pickUpLocation: '',
        dropOffLocation: '',
        appointmentDate: '',
        startTime: '',
        endTime: '',
        driverId: '',
        customerId: userId,
        approved: 0
    });
    const [drivers, setDrivers] = useState([]);
    const [successMessage, setSuccessMessage] = useState('');
    const [selectedAppointmentId, setSelectedAppointmentId] = useState(null);
    const [showConfirm, setShowConfirm] = useState(false); // State for confirmation modal
    const [appointmentToConfirm, setAppointmentToConfirm] = useState(null); // State to hold appointment details for confirmation

    useEffect(() => {
        if (userId) {
            axios.get(`http://localhost:8080/api/appointment/customer/${userId}`)
                .then(response => {
                    if (Array.isArray(response.data)) {
                        setAppointments(response.data);
                    } else {
                        setAppointments([]);
                    }
                    setLoading(false);
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                    setError(error);
                    setLoading(false);
                });
        } else {
            setError(new Error("User ID is not available."));
            setLoading(false);
        }
        fetchDrivers();
    }, [userId]);

    const fetchDrivers = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/driver');
            setDrivers(response.data);
            console.log('Drivers fetched:', response.data);
        } catch (error) {
            console.error('Error fetching drivers:', error);
        }
    };

    const handleChange = (e) => {
        setNewAppointment({
            ...newAppointment,
            [e.target.name]: e.target.value,
        });
    };

    const fetchAppointments = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/appointment/customer/${userId}`)
            setAppointments(response.data);
        } catch (error) {
            console.error('Error fetching appointments:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setAppointmentToConfirm(newAppointment); // Set the appointment to confirm
        setShowConfirm(true); // Show the confirmation modal
    };

    const confirmSubmit = async () => {
        const token = localStorage.getItem('token');
        if (!token) {
            console.error('Token not found in localStorage.');
            return;
        }

        try {
            if (editMode && selectedAppointmentId) {
                const appointmentWithId = { ...appointmentToConfirm, appointmentId: selectedAppointmentId }; // Add the appointment ID
                const response = await axios.put(`http://localhost:8080/api/appointment/${selectedAppointmentId}`, appointmentWithId, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });
                console.log('Appointment updated:', response.data);
            } else {
                const response = await axios.post('http://localhost:8080/api/appointment', appointmentToConfirm, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });
                console.log('Appointment added:', response.data);
            }

            fetchAppointments();
            setShowForm(false);
            setEditMode(false);
            setSuccessMessage(editMode ? 'Appointment updated successfully!' : 'Appointment added successfully!');
            setTimeout(() => {
                setSuccessMessage('');
            }, 3000);
        } catch (error) {
            console.error('Error adding/updating appointment:', error.response || error);
        } finally {
            setShowConfirm(false); // Close the confirmation modal
        }
    };

    const handleCancel = async (appointmentId) => {
        try {
            const token = localStorage.getItem('token');
            if (!token) {
                console.error('Token not found in localStorage.');
                return;
            }

            // Find the appointment to cancel
            const appointmentToCancel = appointments.find(app => app.appointmentId === appointmentId);
            if (!appointmentToCancel) {
                console.error('Appointment not found.');
                return;
            }

            await axios.delete(`http://localhost:8080/api/appointment/${appointmentId}`, {
                headers: { 'Authorization': `Bearer ${token}` },
                data: appointmentToCancel // Send the appointment object as required
            });
            console.log('Appointment cancelled');
            fetchAppointments();
            setSuccessMessage('Appointment cancelled successfully!');
            setTimeout(() => {
                setSuccessMessage('');
            }, 3000);
        } catch (error) {
            console.error('Error cancelling appointment:', error);
        }
    };

    const handleEdit = (appointment) => {
        setNewAppointment({
            pickUpLocation: appointment.pickUpLocation,
            dropOffLocation: appointment.dropOffLocation,
            appointmentDate: appointment.appointmentDate,
            startTime: appointment.startTime,
            endTime: appointment.endTime,
            driverId: appointment.driverId,
            customerId: userId,
            approved: appointment.approved
        });
        setSelectedAppointmentId(appointment.appointmentId);
        setShowForm(true);
        setEditMode(true);
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error.message || "Something went wrong"}</div>;
    }

    return (
        <div className="customer-dashboard">
            <Navbar /> 
            <header className="dashboard-header">
                <h1>Customer Dashboard</h1>
            </header>
            <div className="dashboard-content">
                <aside className="dashboard-sidebar">
                    <nav>
                        <ul>
                            <li><a href="#appointments">Appointments</a></li>
                            <li><a href="#profile">Profile</a></li>
                            <li><a href="#settings">Settings</a></li>
                            <li><a href="#" onClick={() => setShowForm(true)}>Add Appointment</a></li>
                        </ul>
                    </nav>
                </aside>
                <main className="dashboard-main">
                    <section className="appointments-section" id="appointments">
                        <h2>My Appointments</h2>
                        
                        {successMessage && <div className="success-message">{successMessage}</div>}

                        
                        {appointments.length === 0 && !loading && !error && (
                            <div className="no-appointments-message">
                                <p>You don't have any appointments yet. Please add one.</p>
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
                                    <div><strong>Approved:</strong> {appointment.approved ? "Yes" : "No"}</div>
                                    <button onClick={() => handleEdit(appointment)}>Edit</button>
                                    <button onClick={() => handleCancel(appointment.appointmentId)}>Cancel</button>
                                </li>
                            ))}
                        </ul>
                    </section>
                </main>
            </div>

            
            {/* Appointment Form Modal */}
{showForm && (
    <div className="form-modal">
        <div className="modal-content">
            <span className="close" onClick={() => setShowForm(false)}>&times;</span>
            <h2>{editMode ? 'Edit Appointment' : 'Add New Appointment'}</h2>
            <form onSubmit={handleSubmit} className="appointment-form">
                <label>
                    Pickup Location:
                    <input type="text" name="pickUpLocation" value={newAppointment.pickUpLocation} onChange={handleChange} required />
                </label>
                <label>
                    Dropoff Location:
                    <input type="text" name="dropOffLocation" value={newAppointment.dropOffLocation} onChange={handleChange} required />
                </label>
                <label>
                    Appointment Date:
                    <input type="date" name="appointmentDate" value={newAppointment.appointmentDate} onChange={handleChange} required />
                </label>
                <label>
                    Start Time:
                    <input type="time" name="startTime" value={newAppointment.startTime} onChange={handleChange} required />
                </label>
                <label>
                    End Time:
                    <input type="time" name="endTime" value={newAppointment.endTime} onChange={handleChange} required />
                </label>
                <label>
                    Driver:
                    <select name="driverId" value={newAppointment.driverId} onChange={handleChange} required>
                        <option value="">Select a driver</option>
                        {drivers.map(driver => (
                            <option key={driver.driverId} value={driver.driverId}>{driver.firstName}</option>
                        ))}
                    </select>
                </label>
                <button type="submit">{editMode ? 'Update' : 'Add'}</button>
            </form>
        </div>
    </div>
)}


            {/* Confirmation Modal */}
            {showConfirm && (
                <div className="confirm-modal">
                    <div className="modal-content">
                        <span className="close" onClick={() => setShowConfirm(false)}>&times;</span>
                        <h2>Confirm {editMode ? 'Update' : 'Add'} Appointment</h2>
                        <p>Are you sure you want to {editMode ? 'update' : 'add'} the following appointment?</p>
                        <div>
                            <strong>Pickup Location:</strong> {appointmentToConfirm.pickUpLocation}<br />
                            <strong>Dropoff Location:</strong> {appointmentToConfirm.dropOffLocation}<br />
                            <strong>Appointment Date:</strong> {appointmentToConfirm.appointmentDate}<br />
                            <strong>Start Time:</strong> {appointmentToConfirm.startTime}<br />
                            <strong>End Time:</strong> {appointmentToConfirm.endTime}<br />
                        </div>
                        <button onClick={confirmSubmit}>{editMode ? 'Update Appointment' : 'Add Appointment'}</button>
                        <button className="cancel" onClick={() => setShowConfirm(false)}>Cancel</button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default CustomerDashboard;
