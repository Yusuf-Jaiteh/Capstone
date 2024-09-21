import React from 'react'
import Header from './header';
import HeroSection from './HeroSection';
import Services from './Services';
import About from './About';
import Testimonials from './Testimonials';
import Contact from './Contact';

function LandingPageContent() {

    return (
        <>
            <div>
                <Header />
                <HeroSection />
                <Services />
                <About />
                <Testimonials />
                <Contact />
            </div>
        </>
        
    )
}

export default LandingPageContent;