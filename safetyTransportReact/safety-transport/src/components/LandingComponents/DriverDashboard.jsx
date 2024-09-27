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
    const [reviews, setReviews] = useState([]); 
    const [loadingReviews, setLoadingReviews] = useState(false); 
    const [reviewsError, setReviewsError] = useState(null); 
    const [isNotificationOpen, setIsNotificationOpen] = useState(false); 
    

    const ReviewCard = ({ review }) => {
        return (
            <div className="review-card">
                <p><strong>Review By:</strong> {review.customer?.firstName} {review.customer?.lastName}</p>
                <p><strong>Reviewer's Contact:</strong> {review.customer?.phoneNumber}</p>
                <p><strong>Ratings:</strong> {review.rating} / 5</p>
                <p><strong>Comments:</strong> {review.reviewText}</p>
            </div>
        );
    };

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

    const fetchReviews = async () => {
        setLoadingReviews(true);
        setReviewsError(null);

        try {
            console.log('Fetching reviews for driver ID:', userId); 
            const response = await axios.get(`http://localhost:8080/api/reviews/driver/${userId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log('Reviews fetched successfully:', response.data);
            setReviews(response.data);
            setLoadingReviews(false);
            setIsNotificationOpen(true); 

            const customerPromises = response.data.map(async (review) => {
                const customerResponse = await axios.get(`http://localhost:8080/api/customer/${review.customerId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                console.log('customer response:', customerResponse.data);
                return { ...review, customer: customerResponse.data }; // Associate customer details with the review
            });
            const reviewsWithCustomerDetails = await Promise.all(customerPromises);
            setReviews(reviewsWithCustomerDetails);

            console.log('Updated reviews state with customer details:', reviewsWithCustomerDetails);

        } catch (error) {
            console.error('Error fetching reviews:', error);
            setReviewsError(error);
            setLoadingReviews(false);
            setIsNotificationOpen(true); 
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

   
    useEffect(() => {
        if (isNotificationOpen) {
            const timer = setTimeout(() => {
                setIsNotificationOpen(false);
            }, 5000); 

            return () => clearTimeout(timer);
        }
    }, [isNotificationOpen]);

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
                            <li><a href="#view-reviews" onClick={fetchReviews}>View Reviews</a></li> 
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

            
            {isNotificationOpen && (
                <div className="notification">
                    <div className="notification-content">
                        <h2>My Reviews</h2>
                        {loadingReviews && <div>Loading reviews...</div>}
                        {reviewsError && <div>Error: {reviewsError.message}</div>}
                        {!loadingReviews && reviews.length === 0 && <div>No reviews available for you.</div>}
                        <div>
                            {reviews.map(review => (
                                <ReviewCard key={review.reviewId} review={review} />
                            ))}
                        </div>
                        <button onClick={() => setIsNotificationOpen(false)}>Close</button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default DriverDashboard;
