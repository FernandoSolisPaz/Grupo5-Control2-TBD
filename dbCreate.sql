-- Tabla Userm
CREATE TABLE Userm (
    User_id SERIAL NOT NULL,
    name CHAR(50) NOT NULL,
    email CHAR(50) NOT NULL,
	password CHAR(50) NOT NULL,
    PRIMARY KEY (User_id)
);

-- Tabla Task
CREATE TABLE Task (
	Task_id SERIAL NOT NULL,
	user_id INT NOT NULL,
	title CHAR(50) NOT NULL,
	description CHAR(200),
	date_of_expire CHAR(10) NOT NULL,
	state CHAR(10) NOT NULL,
	PRIMARY KEY (Task_id)
);
