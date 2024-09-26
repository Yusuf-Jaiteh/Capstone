import React from 'react';
import './About.css';

function About() {
    return (
    <section id="about">
        <div className="about-container">
            <h1>About Us</h1>
            <p>
                Welcome to our company! We are dedicated to providing the best service in the industry.
                Our team of professionals is committed to excellence and customer satisfaction.
            </p>
            <p>
                Our mission is to deliver high-quality products and services that meet the needs of our clients.
                We believe in innovation, integrity, and teamwork.
            </p>
            <p>
                <strong>Your safety is our top priority.</strong> We understand the importance of feeling secure when boarding a vehicle from unknown drivers. 
                Our rigorous vetting process ensures that all drivers meet our high standards for safety and reliability.
            </p>
            <p>
                We use advanced technology to monitor and track all rides, providing you with real-time updates and peace of mind. 
                Our customer support team is available 24/7 to assist you with any concerns or questions.
            </p>
            <img src="/logo.jpg" alt="Company Logo" className="about-logo" />
        </div>
    </section>
    );
}

export default About;