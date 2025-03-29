-- Вставка тестовых данных

-- Пользователи (пароль 'password' захешированный с bcrypt)
INSERT INTO users (username, email, password_hash) VALUES
('user1', 'user1@example.com', '$2a$10$rQpXvuyBP6BU9CYJKCm0oOFvhBYbeh2vXTa3rsKJ9.8FnNV0hvKhm'),
('user2', 'user2@example.com', '$2a$10$rQpXvuyBP6BU9CYJKCm0oOFvhBYbeh2vXTa3rsKJ9.8FnNV0hvKhm');

-- Категории
INSERT INTO categories (name, color) VALUES
('Продуктивное', 'зеленый'),
('Нейтральное', 'серый'),
('Развлекательное', 'красный');

-- Активности
INSERT INTO activities (user_id, category_id, timestamp, description) VALUES
(1, 1, NOW() - INTERVAL '1 day', 'Работа над проектом'),
(1, 2, NOW() - INTERVAL '5 hours', 'Обед'),
(1, 3, NOW() - INTERVAL '2 hours', 'Просмотр фильма'),
(2, 1, NOW() - INTERVAL '2 days', 'Учеба'),
(2, 2, NOW() - INTERVAL '8 hours', 'Совещание'),
(2, 3, NOW() - INTERVAL '4 hours', 'Игры'); 