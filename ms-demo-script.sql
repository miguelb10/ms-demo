create database demo
create table if not exists "user" (
id integer primary key generated always as identity,
username varchar(50) unique not null,
password varchar(50) not null,
name varchar(50) not null,
lastname varchar(50) not null
)

create table if not exists project (
id integer primary key generated always as identity,
title varchar(100) not null,
description varchar(250) not null,
permalink varchar(250) unique not null
)

insert into "user" (username, password, name, lastname) values ('mblas', '12345', 'Miguel', 'Blas')

select * from project

