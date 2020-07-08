drop database if exists DBKinal2019;
Create database DBKinal2019;

use DBKinal2019;

Create table Mestros(
	codigoMaestro int auto_increment not null,
    nombreMaestro varchar(150) not null,
    apellidosMaestro varchar(150) not null,
    fechaNacimiento date not null,
    sexo varchar(1) not null,
    titulo varchar(150) not null,
    direccion varchar(250) not null,
    telefono varchar(9) not null,
    primary key PK_codigoMaestro(codigoMaestro)
);


Create table Grados(
	codigoGrado int auto_increment not null, 
    grado varchar(15) not null,
    seccion varchar(5) not null,
    modalidad varchar(50) not null, -- presencial, semi-presencial y virtual
    jornada varchar(15) not null, -- Matutina, Vespertina, Nocturna
    primary key PK_codigoGrado (codigoGrado)
);

Create table Alumnos(
	codigoAlumno int auto_increment not null,
    nombresAlumno varchar(100) not null,
    apellidosAlumno varchar(100) not null,
    sexo varchar(1) not null,
    telefono varchar(8),
    direccion varchar(250) not null,
    email varchar(25) not null,
    nivel varchar(15) not null,
    codigoGrado int not null,
    primary key PK_codigoAlumno (codigoAlumno),
    constraint FK_Alumnos_Grados foreign key
		(codigoGrado) references Grados(codigoGrado)
);

Create table Asignaturas(
	codigoAsignatura int auto_increment not null,
    Asignatura varchar(100)not null,
    codigoGrado int not null,
    primary key PK_codigoAsignatura (codigoAsignatura),
    constraint FK_Asignaturas_Grados foreign key
		(codigoGrado) references Grados(codigoGrado)
);

Create table CalificacionBimestral(
	codigoBimestral int auto_increment not null,
    notaAcumulativa decimal(3,2) not null,
    notaEvaluacion decimal(3,2) not null,
    totalBimestre decimal(3,2) default 0,
    codigoAlumno int not null,
    codigoAsignatura int not null,
    primary key PK_codigoBimestral (codigoBimestral),
    constraint FK_CalificacionBimestral_Alumnos foreign key (codigoAlumno) references Alumnos(codigoAlumno),
    constraint FK_CalificacionBimestral_Asignaturas foreign key (codigoAsignatura) references Asignaturas(codigoAsignatura)    
);

Create table Grados_Maestros(
	codigoGrado int not null,
    codigoMaestro int not null,
    primary key (codigoGrado, codigoMaestro)
);

Create table Maestros_Asignaturas(
	codigoMaestro int not null,
    codigoAsignatura int not null,
    primary key (codigoMaestro, codigoAsignatura)    
);



