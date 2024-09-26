import React from 'react';
import './Footer.css';
import { FaPhone, FaEnvelope, FaMapMarkerAlt, FaFacebook, FaTwitter, FaInstagram } from 'react-icons/fa';

function Footer() {
  return (
    <footer className="footer">
      <div className="footer-container">
        <div className="footer-section">
          <h2>Contact Us</h2>
          <p><FaPhone /> +220 234 567 890</p>
          <p><FaEnvelope /> contact@safety_transports.com</p>
          <p><FaMapMarkerAlt /> 123 Main Street, Fajara, The Gambia</p>
        </div>
        <div className="footer-section">
          <h2>Follow Us</h2>
          <div className="social-icons">
            <a href="https://facebook.com" target="_blank" rel="noopener noreferrer"><FaFacebook /></a>
            <a href="https://twitter.com" target="_blank" rel="noopener noreferrer"><FaTwitter /></a>
            <a href="https://instagram.com" target="_blank" rel="noopener noreferrer"><FaInstagram /></a>
          </div>
        </div>
        <div className="footer-section">
          <h2>About Us</h2>
          <p>We are dedicated to providing the best service in the industry. Your safety and satisfaction are our top priorities.</p>
        </div>
      </div>
      <div className="footer-bottom">
        <p>&copy; 2023 Safety Transports. All rights reserved.</p>
      </div>
    </footer>
  );
}

export default Footer;