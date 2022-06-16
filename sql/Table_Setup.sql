drop table if exists Employee;
drop table if exists Schedule;
drop table if exists Vessel;
drop table if exists Berth;
drop table if exists Terminal;
drop table if exists Port;

drop type if exists role;

create type role as enum ('vessel planner', 'terminal manager', 'port authority', 'researcher');

create table Port (
	id   serial,
	name varchar(64),
	primary key (id)
);

create table Terminal (
	id   serial,
	name varchar(64),
	port int,
	foreign key (port) references Port (id) ON DELETE CASCADE,
	primary key (id)
);

create table Berth (
	id           serial,
	terminal     int,
	open         time(0),
	close        time(0),
	unload_speed float,
	length       int,
	width        int,
	depth        int,
	foreign key (terminal) references Terminal (id) ON DELETE CASCADE,
	primary key (id)
);

create table Vessel (
	id            serial,
	name          varchar(64),
	arrival       timestamp,
	deadline      timestamp,
	containers    int,
	cost_per_hour float,
	destination   int,
	length        int,
	width         int,
	depth         int,
	foreign key (destination) references Terminal (id) ON DELETE CASCADE,
	primary key (id)
);

create table Schedule (
	vessel       int,
	berth        int,
	manual       boolean,
	start        timestamp,
	expected_end timestamp,
	primary key (vessel),
	foreign key (vessel) references Vessel (id) ON DELETE CASCADE,
	foreign key (berth) references Berth (id) ON DELETE CASCADE
);

CREATE TABLE Employee (
	name            varchar(64),
	email           varchar(64),
	password        char(64),
	salt_and_pepper char(64),
	role            role,
	port            int,
	terminal        int,
	primary key (email),
	foreign key (port) references Port (id) ON DELETE CASCADE,
	foreign key (terminal) references Terminal (id) ON DELETE CASCADE
);
