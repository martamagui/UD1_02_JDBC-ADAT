DROP database Books;
CREATE DATABASE IF NOT EXISTS Books DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci;
USE Books;
CREATE TABLE BooksTable(
    Titulo varchar (255) NOT NULL,
    Autor varchar (255),
    Categoria varchar (255),
    Precio varchar (255),
    PRIMARY KEY (Titulo)
)ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
INSERT INTO BooksTable(Titulo, Autor, Categoria, Precio) VALUES
('El Libro Negro del Programador','Rafael Gómez Blanes','Programación','15.77 €'),
('Clean JavaScript: Aprende a aplicar Código Limpio, SOLID y Testing',' Miguel A. Gómez ','Programación','19.97 €'),
('Programando Videojuegos: JavaScript','Iván Fasheh','Programación','19.45  €'),
('C/C++. Curso de programación (Manuales Imprescindibles)','Miguel Angel Acera','Programación','27.31 €');

select * from BooksTable;


