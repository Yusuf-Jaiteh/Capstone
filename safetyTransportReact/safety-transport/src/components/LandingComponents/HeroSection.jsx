import React, { useState, useEffect } from 'react';
import './HeroSection.css';

const slides = [
  {
    image: './this2.jpg',
    text: 'Experience the best ride of your life',
  },
  {
    image: './this.jpg',
    text: 'Safety and comfort are our priorities',
  },
  {
    image: './this1.jpg',
    text: 'Travel with confidence and ease',
  },
];

function HeroSection() {
  const [currentSlide, setCurrentSlide] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentSlide((prevSlide) => (prevSlide + 1) % slides.length);
    }, 5000); // Change slide every 5 seconds
    return () => clearInterval(interval);
  }, []);

  return (
    <div
      className="hero-container"
      style={{ 
        backgroundImage: `url(${slides[currentSlide].image})`, 
        height: '80vh', 
        width: '100%', 
        backgroundSize: 'cover', 
        backgroundPosition: 'center' 
      }}
    >
      <div className="hero-text">
        <h1>{slides[currentSlide].text}</h1>
      </div>
    </div>
  );
}

export default HeroSection;