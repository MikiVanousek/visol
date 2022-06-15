drop table if exists PortPermission;
drop table if exists TerminalPermission;
drop table if exists Employee;
drop table if exists Schedule;
drop table if exists Vessel;
drop table if exists Berth;
drop table if exists Terminal;
drop table if exists Port;

drop type if exists role;

create type role as enum('planner', 'manager');

create table Port(
id serial,
name varchar(255),
primary key(id)
);

create table Terminal (
id serial,
open time(0),
close time(0),
port int,
foreign key(port) references Port(id) ON DELETE CASCADE,
primary key(id)
);

create table Berth(
id serial,
unloadspeed float,
length int,
width int,
depth int,
terminal int,
open time(0),
close time(0),
foreign key(terminal) references Terminal(id) ON DELETE CASCADE,
primary key(id)
);

create table Vessel (
id serial,
name varchar(255),
width int,
depth int,
length int,
dest_terminal int,
containers int,
eta timestamp,
deadline timestamp,
foreign key(dest_terminal) references Terminal(id) ON DELETE CASCADE,
primary key(id)
);

create table Schedule(
vessel int,
berth int,
manual boolean,
start timestamp,
finish timestamp,
primary key(vessel),
foreign key(vessel) references Vessel(id) ON DELETE CASCADE,
foreign key(berth) references Berth(id) ON DELETE CASCADE
);

create table Employee(
name varchar(255),
email varchar(255),
passwordHash varchar(255),
primary key(email)
);

create table TerminalPermission(
email varchar(255),
terminal int,
role role,
foreign key(terminal) references Terminal(id) ON DELETE CASCADE,
primary key(email),
foreign key(email) references Employee(email) ON DELETE CASCADE
);

create table PortPermission(
email varchar(255),
port int,
foreign key(port) references Port(id) ON DELETE CASCADE,
primary key(email),
foreign key(email) references Employee(email) ON DELETE CASCADE
);
