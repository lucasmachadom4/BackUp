create database adota;
use adota;

create table tipo(
	id int not null auto_increment,
    nome varchar(64) not null,   
    constraint id primary key(id)
);

create table usuario(
	id int not null auto_increment,
    nome varchar(64) not null,
    email varchar(80) not null,
    senha varchar(64) not null,
    telefone1 varchar(16) not null,
    telefone2 varchar(16),  
    constraint id primary key(id)   
);

create table animal(
	id int not null auto_increment,
    nome varchar(64) not null,
    descricao varchar(200),
    sexo varchar(9) not null,
    datanascimento date not null,
    cidade varchar(64) not null,
    valordoacao real,
    tipo_id int,
    usuario_id int,
    constraint id primary key(id),
    FOREIGN KEY (tipo_id) REFERENCES tipo(id),
	FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

insert into tipo(nome) values('Cachorro'),('Gato'),('Ave'),('Peixe');
select * from animal;