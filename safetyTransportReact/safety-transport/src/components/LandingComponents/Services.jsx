import React from 'react';
import './Services.css';
import { FaTruck, FaShippingFast, FaBoxOpen } from 'react-icons/fa';

function Services() {
    return (
    <section id="services">
        <div className="services-container">
            <h1 className="services-title">Our Services</h1>
            <p className="services-description">Our services include the following:</p>
            <div className="services-list">
                <div className="service-item">
                    <FaTruck className="service-icon" />
                    <h2>Transportation</h2>
                    <p>Reliable and efficient transportation services.</p>
                </div>
                <div className="service-item">
                    <FaShippingFast className="service-icon" />
                    <h2>Logistics</h2>
                    <p>Comprehensive logistics solutions for your business.</p>
                </div>
                <div className="service-item">
                    <FaBoxOpen className="service-icon" />
                    <h2>Delivery</h2>
                    <p>Fast and secure delivery services.</p>
                </div>
            </div>
        </div>
    </section>
    );
}

export default Services;