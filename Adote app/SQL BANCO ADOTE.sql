create database adote_db;
use adote_db;


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
    imagem varchar(3000),
    tipo_id int  not null,
    usuario_id int  not null,
    constraint id primary key(id),
    FOREIGN KEY (tipo_id) REFERENCES tipo(id),
	FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

INSERT INTO usuario(nome, email, senha, telefone1, telefone2) values('Lucas Machado', 'lucas@lucas.com', 'lucas', '(62)9 9600-0000', '(62)9 8285-9663');
INSERT INTO usuario(nome, email, senha, telefone1) values('Matheus', 'matheus@matheus.com', 'matheus', '(62)9 8100-04499');
INSERT INTO usuario(nome, email, senha, telefone1) values('Maria Luiza', 'maria@maria.com', 'maria', '(62)9 9621-0093');

insert into tipo(nome) values('SELECIONE'),('Cachorro'),('Gato'),('Ave'),('Peixe'),('Réptil');

INSERT INTO animal(nome, descricao, sexo, datanascimento, cidade, valordoacao, imagem, tipo_id, usuario_id)  
values('Zeus', 'Ele é pequeno, pelo preto com branco.', 'Macho', '2009-06-28', 'Goiânia', 5.0, 'http://localhost/adote/', 2,  1);
INSERT INTO animal(nome, descricao, sexo, datanascimento, cidade, valordoacao, imagem, tipo_id, usuario_id)  
values('Zeus', 'Ele é pequeno, pelo preto com branco.', 'Macho', '2009-06-28', 'Goiânia', 5.0, 'http://localhost/adote/', 2,  1);
INSERT INTO animal(nome, descricao, sexo, datanascimento, cidade, valordoacao, imagem, tipo_id, usuario_id)  
values('Zeus', 'Ele é pequeno, pelo preto com branco.', 'Macho', '2009-06-28', 'Goiânia', 5.0, 'http://localhost/adote/', 2,  2);


select * from usuario;
select * from tipo;
select * from animal;

select * from tipo where nome like '%gato%';

select ani.*, tip.nome nome from animal ani inner join tipo tip on ani.tipo_id = tip.id where ani.nome like '%%' or ani.sexo like '%%' or ani.cidade like '%%' or tip.nome like '%%' ORDER BY ani.id  ASC;

select ani.*, tip.nome nome, usu.nome from animal ani inner join tipo tip on ani.tipo_id = tip.id inner join usuario usu on ani.usuario_id = usu.id where ani.nome like '%%' or ani.sexo like '%%' or ani.cidade like '%%' or tip.nome like '%%' ORDER BY ani.id  ASC;