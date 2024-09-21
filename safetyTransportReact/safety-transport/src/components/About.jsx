import React from 'react';
import './About.css';


function About() {
    return (
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
            <img src="/path/to/logo.jpg" alt="Company Logo" className="about-logo" />
        </div>
    );
}

export default About;