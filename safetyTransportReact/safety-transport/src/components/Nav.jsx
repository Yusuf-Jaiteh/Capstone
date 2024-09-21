import React from 'react';
import './Nav.css'; 

function Nav() {
   

    return (
        <div>
            <nav className="nav">
                <img src="/logo.jpg" alt="Company Logo" className="nav-logo" />
            
                <ul className="nav-links">
                    <li><a href="/">Home</a></li>
                    <li><a href="/signup">Sign Up</a></li>
                    <li><a href="/login">Login</a></li>
                </ul>
            </nav>
            
        </div>
    );
}

export default Nav;