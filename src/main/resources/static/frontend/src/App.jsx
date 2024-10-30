import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './components/Home'; // Примерен компонент за началната страница
import ContactForm from './components/ContactForm'; // Импорт на ContactForm
import Navbar from './components/Navbar'; // Импорт на Navbar
import './components/Navbar.css'; // Импорт на CSS стила за Navbar

const App = () => {
    return (
        <Router>
            <Navbar />
            <div>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/contact" element={<ContactForm />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;
