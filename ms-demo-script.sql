create database demo
create table if not exists "user" (
id int primary key generated always as identity,
username varchar(50) unique not null,
password varchar(50) not null,
name varchar(50) not null,
lastname varchar(50) not null
)

create table if not exists project (
id int primary key generated always as identity,
title varchar(100) not null,
description varchar(250) not null,
permalink varchar(250) unique not null
)

create table if not exists "area" (
id int primary key generated always as identity,
name varchar(100) not null,
description varchar(250)
)

create table if not exists project_area (
  project_id integer references project(id),
  area_id integer references area(id),
  primary key (project_id, area_id)
)

insert into "user" (username, password, name, lastname) values ('mblas', '12345', 'Miguel', 'Blas')
insert into "area" (name, description) values ('Chile', 'new area')
insert into "area" (name, description) values ('Peru', 'new area')
insert into project_area (project_id, area_id) values (1, 1)
insert into project_area (project_id, area_id) values (2, 1)
insert into project_area (project_id, area_id) values (2, 2)

select * from project_area



