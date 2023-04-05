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

create table if not exists capacity (
id int primary key generated always as identity,
unit varchar(100) not null,
value varchar(250)
)

create table if not exists project_area (
  project_id integer references project(id),
  area_id integer references area(id),
  primary key (project_id, area_id)
)

create table if not exists project_capacity (
  project_id integer references project(id),
  capacity_id integer references capacity(id),
  primary key (project_id, capacity_id)
)

create table if not exists company (
id int primary key generated always as identity,
title varchar(100) not null,
description varchar(250) not null,
permalink varchar(250) unique not null
)

create table if not exists contact (
id int primary key generated always as identity,
name varchar(100) not null,
phone varchar(20) not null,
email varchar(250) not null
)

create table if not exists company_contact (
  company_id integer references company(id),
  contact_id integer references contact(id),
  primary key (company_id, contact_id)
)

insert into "user" (username, password, name, lastname) values ('mblas', '12345', 'Miguel', 'Blas');
insert into "area" (name, description) values ('Chile', 'new area');
insert into "area" (name, description) values ('Peru', 'new area');
insert into "area" (name, description) values ('Argentina', 'new area');
insert into "area" (name, description) values ('Colombia', 'new area');
insert into project (title, description, permalink) values ('Project1', 'new project', 'asdf1-project1');
insert into project (title, description, permalink) values ('Project2', 'new project', 'asdf2-project2');
insert into project (title, description, permalink) values ('Project3', 'new project', 'asdf3-project3');
insert into project_area (project_id, area_id) values (1, 1);
insert into project_area (project_id, area_id) values (2, 1);
insert into project_area (project_id, area_id) values (2, 2);
insert into project_area (project_id, area_id) values (3, 3);
insert into project_area (project_id, area_id) values (3, 4);
insert into capacity (unit, value) values ('m', '123');
insert into capacity (unit, value) values ('km', '2000');
insert into project_capacity (project_id, capacity_id) values (1, 1);
insert into project_capacity (project_id, capacity_id) values (2, 2);
insert into project_capacity (project_id, capacity_id) values (2, 1);
insert into project_capacity (project_id, capacity_id) values (3, 1);
insert into contact (name, phone, email) values ('Juan', '123456789', 'juan@test.com');
insert into contact (name, phone, email) values ('Jose', '325456789', 'jose@test.com');

select * from project


