--
-- Файл сгенерирован с помощью SQLiteStudio v3.3.3 в Пт фев 3 21:19:21 2023
--
-- Использованная кодировка текста: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Таблица: Datas
CREATE TABLE Datas (id INTEGER PRIMARY KEY AUTOINCREMENT, note_name STRING, note_content STRING, update_date DATETIME);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
