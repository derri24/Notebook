--
-- ���� ������������ � ������� SQLiteStudio v3.3.3 � �� ��� 3 21:22:09 2023
--
-- �������������� ��������� ������: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- �������: Datas
CREATE TABLE Datas (id INTEGER PRIMARY KEY AUTOINCREMENT, note_name STRING, note_content STRING, update_date DATETIME);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;