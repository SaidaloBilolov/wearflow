-- =====================================================
-- WearFlow Logistics - Ma'lumotlar Bazasi (SQL)
-- Neon.tech PostgreSQL uchun
-- =====================================================

-- Avval eski jadvallarni o'chiramiz (qaytadan ishlatish uchun)
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS users;

-- =====================================================
-- 1. USERS jadvali - Foydalanuvchilar
-- =====================================================
CREATE TABLE users (
    id       SERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role     VARCHAR(10)  NOT NULL CHECK (role IN ('ADMIN', 'USER'))
);

-- Test foydalanuvchilarni qo'shamiz
-- MUHIM: Haqiqiy loyihada parollar hash qilinishi kerak!
INSERT INTO users (username, password, role) VALUES
    ('admin',    'admin123',    'ADMIN'),
    ('omborchi', 'omborchi123', 'USER'),
    ('sardor',   'sardor123',   'USER');

-- =====================================================
-- 2. INVENTORY jadvali - Mahsulotlar ombori
-- =====================================================
CREATE TABLE inventory (
    id            SERIAL PRIMARY KEY,
    mahsulot_nomi VARCHAR(150) NOT NULL,
    miqdori       INTEGER      NOT NULL DEFAULT 0 CHECK (miqdori >= 0),
    ombor_turi    VARCHAR(100) NOT NULL
);

-- Namunali mahsulotlar ma'lumotlari
INSERT INTO inventory (mahsulot_nomi, miqdori, ombor_turi) VALUES
    ('Erkaklar ko''ylagi (M, Oq)',          150, 'Asosiy Ombor'),
    ('Erkaklar ko''ylagi (L, Ko''k)',        98,  'Asosiy Ombor'),
    ('Ayollar bluzasi (S, Qizil)',           45,  'Asosiy Ombor'),
    ('Ayollar bluzasi (M, Yashil)',          8,   'Toshkent Filiali'),
    ('Bolalar futbolkasi (6-8 yosh)',        200, 'Toshkent Filiali'),
    ('Jins shim (Erkak, 32/32)',             75,  'Samarqand Filiali'),
    ('Jins shim (Ayol, 28/30)',              0,   'Samarqand Filiali'),
    ('Sport kiyimi to''plami (L)',           30,  'Asosiy Ombor'),
    ('Qishki palto (Erkak, XL)',             12,  'Toshkent Filiali'),
    ('Qishki palto (Ayol, M)',               5,   'Samarqand Filiali');
