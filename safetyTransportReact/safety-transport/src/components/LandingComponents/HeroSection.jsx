import React, { useState, useEffect } from 'react';
import './HeroSection.css';

const slides = [
  {
    image: '.gg/hero1.png',
    text: 'Experience the best ride of your life',
  },
  {
    image: './hero2.png',
    text: 'Safety and comfort are our priorities',
  },
  {
    image: './hero3.png',
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
      style={{ backgroundImage: `url(${slides[currentSlide].image})` }}
    >
      <div className="hero-text">
        <h1>{slides[currentSlide].text}</h1>
      </div>
    </div>
  );
}

export default HeroSection;