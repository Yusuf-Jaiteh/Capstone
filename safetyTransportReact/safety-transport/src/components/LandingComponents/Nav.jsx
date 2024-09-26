import React from 'react';
import { Link } from 'react-router-dom';
import './Nav.css';

function Nav() {
    return (
        <nav className="nav">
            <a href="/"><img src="/logo.jpg" alt="Company Logo" className="nav-logo" /></a>
            <div className="nav-center">
                <ul className="nav-links">
                    <li><a href="#home" className="nav-link">Home</a></li>
                    <li><a href="#services" className="nav-link">Services</a></li>
                    <li><a href="#about" className="nav-link">About</a></li>
                    <li><a href="#testimonials" className="nav-link">Testimonials</a></li>
                </ul>
            </div>
            <ul className="nav-right">
                <li><Link to="/signup" className="btn-signup">Sign Up</Link></li>
                <li><Link to="/login" className="btn-signin">Sign In</Link></li>
            </ul>
        </nav>
    );
}

export default Nav;