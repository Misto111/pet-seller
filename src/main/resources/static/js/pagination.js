document.addEventListener('DOMContentLoaded', function () {
    // Получаване на бутоните
    const previousButton = document.querySelector('.pagination .custom-button[href="#"]:nth-child(1)');
    const nextButton = document.querySelector('.pagination .custom-button[href="#"]:nth-child(2")');

    // Скриване на стрелките, ако са налични
    const arrows = document.querySelectorAll('.pagination .page-item:not(.disabled) .page-link span');
    arrows.forEach(arrow => arrow.parentElement.style.display = 'none'); // Скрива стрелките

    // Добавяне на обработчици на събития
    previousButton.addEventListener('click', function (e) {
        e.preventDefault(); // Предотвратява стандартното поведение на линка
        navigate('previous'); // Извиква функцията за навигация назад
    });

    nextButton.addEventListener('click', function (e) {
        e.preventDefault(); // Предотвратява стандартното поведение на линка
        navigate('next'); // Извиква функцията за навигация напред
    });

    function navigate(direction) {
        if (direction === 'next') {
            console.log('Navigating to next page');
            // Логика за навигация напред
            // Например, можете да използвате window.location.href или API за извличане на ново съдържание
        } else if (direction === 'previous') {
            console.log('Navigating to previous page');
            // Логика за навигация назад
            // Например, можете да използвате window.location.href или API за извличане на ново съдържание
        }
    }
});
