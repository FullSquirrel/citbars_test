CREATE DATABASE "c:\mnadyukov\server\DB\TEST.FDB" USER 'SYSDBA' PASSWORD 'masterkey' SET NAMES 'WIN1251' DEFAULT CHARACTER SET WIN1251;

CREATE TABLE Dogovori(
	Nomer BIGINT
		NOT NULL,
	DataDogovora DATE
		NOT NULL,
	DataObnovleniya DATE
		NOT NULL,
	PRIMARY KEY (Nomer)
);

INSERT INTO Dogovori (Nomer,DataDogovora,DataObnovleniya) VALUES (1,'2020.05.15','2020.07.25');
INSERT INTO Dogovori (Nomer,DataDogovora,DataObnovleniya) VALUES (2,'2020.08.02','2020.08.25');
INSERT INTO Dogovori (Nomer,DataDogovora,DataObnovleniya) VALUES (3,'2020.09.10','2020.10.17');
INSERT INTO Dogovori (Nomer,DataDogovora,DataObnovleniya) VALUES (4,'2020.11.23','2020.12.20');
INSERT INTO Dogovori (Nomer,DataDogovora,DataObnovleniya) VALUES (5,'2020.12.01','2021.02.07');
INSERT INTO Dogovori (Nomer,DataDogovora,DataObnovleniya) VALUES (6,'2020.12.18','2021.03.08');
INSERT INTO Dogovori (Nomer,DataDogovora,DataObnovleniya) VALUES (7,'2021.01.24','2021.03.09');
INSERT INTO Dogovori (Nomer,DataDogovora,DataObnovleniya) VALUES (8,'2021.03.11','2021.06.30');
INSERT INTO Dogovori (Nomer,DataDogovora,DataObnovleniya) VALUES (9,'2021.04.23','2021.05.29');
INSERT INTO Dogovori (Nomer,DataDogovora,DataObnovleniya) VALUES (10,'2021.06.12','2021.09.26');