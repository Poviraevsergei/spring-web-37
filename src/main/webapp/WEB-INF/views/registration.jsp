<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация пользователя</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }

        .form-container {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #555;
        }

        input[type="text"],
        input[type="password"],
        input[type="email"],
        input[type="number"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }

        input[type="text"]:focus,
        input[type="password"]:focus,
        input[type="email"]:focus,
        input[type="number"]:focus,
        select:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 5px rgba(76, 175, 80, 0.3);
        }

        .submit-btn {
            width: 100%;
            padding: 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .submit-btn:hover {
            background-color: #45a049;
        }

        .form-section {
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 1px solid #eee;
        }

        .form-section-title {
            font-size: 18px;
            color: #333;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 2px solid #4CAF50;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Регистрация нового пользователя</h2>

    <form action="/security/registration" method="post">

        <div class="form-section">
            <div class="form-section-title">Учетные данные</div>

            <div class="form-group">
                <label for="username">Имя пользователя *</label>
                <input type="text" id="username" name="username"
                       minlength="3" maxlength="50"
                       placeholder="Введите имя пользователя">
            </div>

            <div class="form-group">
                <label for="password">Пароль *</label>
                <input type="password" id="password" name="password"
                       required minlength="6"
                       placeholder="Введите пароль (минимум 6 символов)">
            </div>

            <div class="form-group">
                <label for="confirmPassword">Подтвердите пароль *</label>
                <input type="password" id="confirmPassword" name="confirmPassword"
                       required minlength="6"
                       placeholder="Повторите пароль">
            </div>
        </div>

        <div class="form-section">
            <div class="form-section-title">Персональная информация</div>

            <div class="form-group">
                <label for="firstName">Имя *</label>
                <input type="text" id="firstName" name="firstName"
                       maxlength="50"
                       placeholder="Введите ваше имя">
            </div>

            <div class="form-group">
                <label for="lastName">Фамилия *</label>
                <input type="text" id="lastName" name="lastName"
                       maxlength="50"
                       placeholder="Введите вашу фамилию">
            </div>

            <div class="form-group">
                <label for="email">Email *</label>
                <input type="email" id="email" name="email"
                       maxlength="100"
                       placeholder="example@domain.com">
            </div>

            <div class="form-group">
                <label for="age">Возраст</label>
                <input type="number" id="age" name="age" min="0" max="150"
                       placeholder="Введите ваш возраст">
            </div>
        </div>

        <div class="form-group">
            <button type="submit" class="submit-btn">Зарегистрироваться</button>
        </div>

        <div style="text-align: center; margin-top: 20px; color: #666;">
            Поля, отмеченные *, обязательны для заполнения
        </div>
    </form>
</div>

<script>
    // JavaScript для проверки совпадения паролей
    document.querySelector('form').addEventListener('submit', function(event) {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        if (password !== confirmPassword) {
            event.preventDefault();
            alert('Пароли не совпадают! Пожалуйста, проверьте введенные пароли.');
            document.getElementById('confirmPassword').focus();
        }

        // Проверка возраста
        const ageInput = document.getElementById('age');
        const age = parseInt(ageInput.value);
        if (ageInput.value && (age < 0 || age > 150)) {
            event.preventDefault();
            alert('Возраст должен быть от 0 до 150 лет');
            ageInput.focus();
        }
    });

    // Динамическая проверка паролей
    document.getElementById('confirmPassword').addEventListener('input', function() {
        const password = document.getElementById('password').value;
        const confirmPassword = this.value;
        const errorDiv = this.nextElementSibling;

        if (confirmPassword && password !== confirmPassword) {
            if (!errorDiv || !errorDiv.classList.contains('error-message')) {
                const newError = document.createElement('div');
                newError.className = 'error-message';
                newError.textContent = 'Пароли не совпадают';
                this.parentNode.insertBefore(newError, this.nextSibling);
            }
        } else if (errorDiv && errorDiv.classList.contains('error-message')) {
            errorDiv.remove();
        }
    });
</script>
</body>
</html>