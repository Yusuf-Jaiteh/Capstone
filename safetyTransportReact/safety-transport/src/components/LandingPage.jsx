import React from 'react'
import Header from './LandingComponents/Header';
import HeroSection from './LandingComponents/HeroSection';
import Services from './LandingComponents/Services';
import About from './LandingComponents/About';
import Testimonials from './LandingComponents/Testimonials';
import Footer from './LandingComponents/Footer';


function LandingPageContent() {

    return (
        <>
            <div>
                <Header />
                <HeroSection />
                <Services />
                <About />
                <Testimonials />
                <Footer />
            </div>
        </>
        
    )
}

export default LandingPageContent;