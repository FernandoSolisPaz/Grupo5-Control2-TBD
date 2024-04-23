-- Población de datos para Type_User
INSERT INTO Userm (name, email, password)
VALUES 
('Juan Perez', 'juan.perez@usach.cl', 'juanp123'),
('Pedro Gatica', 'pedro.gatica@usach.cl', 'pedrog456'),
('Luis Mollano', 'luis.mollano@usach.cl', 'luis789');

INSERT INTO Task (user_id, title, description, date_of_expire, state)
VALUES
((SELECT user_id FROM Userm WHERE name = 'Juan Perez'), 'Hacer DB control 2 TBD', 'Comenzar con diagrama de la Base de Datos', '2024-04-27', 'Pending'),
((SELECT user_id FROM Userm WHERE name = 'Pedro Gatica'), 'Terminar Tingeso', 'Subir todo a Docker', '2024-04-28', 'Pending'),
((SELECT user_id FROM Userm WHERE name = 'Pedro Gatica'), 'Estudiar TDC', 'Practicar con ejercicios resueltos', '2024-04-16', 'Completed'),
((SELECT user_id FROM Userm WHERE name = 'Pedro Gatica'), 'Estudiar MyS', 'Revisar cuaderno con la teoria', '2024-05-01', 'Pending'),
((SELECT user_id FROM Userm WHERE name = 'Luis Mollano'), 'Hacer prueba de Calculo 3', 'Incluir ejercicios con triple integral', '2024-05-04', 'Pending'),
((SELECT user_id FROM Userm WHERE name = 'Luis Mollano'), 'Revisar Control 1', '20/30 revisados', '2024-05-03', 'Pending');


