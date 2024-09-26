import React from 'react';
import './Testimonials.css';

function Testimonials() {
    const testimonials = [
        {
            name: "John Doe",
            text: "The service was exceptional! I felt safe and comfortable throughout the ride.",
            image: "/logo.jpg"
        },
        {
            name: "Jane Smith",
            text: "On time and very professional. I highly recommend this service to everyone.",
            image: "/logo.jpg"
        },
        {
            name: "Michael Johnson",
            text: "Best ride ever! The driver was courteous and the vehicle was clean.",
            image: "/logo.jpg"
        },
        {
            name: "Emily Davis",
            text: "I was initially hesitant, but the safety measures in place made me feel secure.",
            image: "/logo.jpg"
        }
    ];

    return (
    <section id="testimonials">
        <div className="testimonials">
            <h1>Testimonials</h1>
            <p>Our customers love us!</p>
            <div className="testimonials-list">
                {testimonials.map((testimonial, index) => (
                    <div key={index} className="testimonial-item">
                        <img src={testimonial.image} alt={testimonial.name} className="testimonial-image" />
                        <p className="testimonial-text">"{testimonial.text}"</p>
                        <p className="testimonial-name">- {testimonial.name}</p>
                    </div>
                ))}
            </div>
        </div>
    </section>
    );
}

export default Testimonials;