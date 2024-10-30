import React, { useState } from 'react';

const ContactForm = () => {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        message: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('/api/contact', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });
            if (response.ok) {
                alert('Съобщението е успешно изпратено!');
                setFormData({ name: '', email: '', message: '' }); // Нулиране на формата
            } else {
                alert('Грешка при изпращане на съобщението.');
            }
        } catch (error) {
            console.error('Грешка:', error);
            alert('Грешка при изпращане на съобщението.');
        }
    };

    return (
        <div>
            <h2>Контактна форма</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="name">Име:</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="email">Имейл:</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="message">Съобщение:</label>
                    <textarea
                        id="message"
                        name="message"
                        value={formData.message}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Изпрати</button>
            </form>
        </div>
    );
};

export default ContactForm;
