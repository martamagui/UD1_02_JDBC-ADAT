DROP database Books;
CREATE DATABASE IF NOT EXISTS Books DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci;
USE Books;
CREATE TABLE BooksTable(
	bookID integer auto_increment NOT NULL,
    title varchar (255) NOT NULL,
    author varchar (255) NOT NULL,
    category varchar (255) NOT NULL,
    price double NOT NULL,
    PRIMARY KEY (bookID)
)ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
INSERT INTO BooksTable(title, author, category, price) VALUES
('El Libro Negro del Programador','Rafael Gómez Blanes','Programación',15.77),
('Clean JavaScript: Aprende a aplicar Código Limpio, SOLID y Testing',' Miguel A. Gómez ','Programación',19.97),
('Programando Videojuegos: JavaScript','Iván Fasheh','Programación',19.97),
('C/C++. Curso de programación (Manuales Imprescindibles)','Miguel Angel Acera','Programación',27.31);
