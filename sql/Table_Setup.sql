--- DELETE ---

DROP TYPE IF EXISTS role;

DROP TABLE IF EXISTS port;
DROP TABLE IF EXISTS terminal;
DROP TABLE IF EXISTS berth;
DROP TABLE IF EXISTS vessel;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS schedulechange;
DROP TABLE IF EXISTS vesselchange;

--- CREATE ---

CREATE TYPE role AS enum ('vessel planner', 'terminal manager', 'port authority', 'researcher');

CREATE TABLE port (
	id   serial CONSTRAINT port_pk PRIMARY KEY,
	name varchar(64) NOT NULL
);

CREATE TABLE terminal (
	id   serial CONSTRAINT terminal_pk PRIMARY KEY,
	name varchar(64) NOT NULL,
	port int         NOT NULL,
	CONSTRAINT terminal_port_id_fk FOREIGN KEY (port) REFERENCES port (id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE berth (
	id           serial CONSTRAINT berth_pk PRIMARY KEY,
	terminal     int     NOT NULL,
	open         time(0) NOT NULL,
	close        time(0) NOT NULL,
	unload_speed float    NOT NULL,
	length       int     NOT NULL,
	width        int     NOT NULL,
	depth        int     NOT NULL,
	CONSTRAINT berth_terminal_id_fk FOREIGN KEY (terminal) REFERENCES terminal (id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT berth_unload_speed_check CHECK (unload_speed > 0),
	CONSTRAINT berth_length_width_depth_check CHECK (length > 0 AND width > 0 AND depth > 0)
);

CREATE TABLE vessel (
	id            serial CONSTRAINT vessel_pk PRIMARY KEY,
	name          varchar(64)  NOT NULL,
	arrival       timestamp(0) NOT NULL,
	deadline      timestamp(0) NOT NULL,
	containers    int          NOT NULL,
	cost_per_hour float         NOT NULL,
	destination   int          NOT NULL,
	length        int          NOT NULL,
	width         int          NOT NULL,
	depth         int          NOT NULL,
	CONSTRAINT vessel_terminal_id_fk FOREIGN KEY (destination) REFERENCES terminal (id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT vessel_arrival_deadline_check CHECK (arrival < deadline),
	CONSTRAINT vessel_containers_check CHECK (containers > 0),
	CONSTRAINT vessel_length_width_depth_check CHECK (length > 0 AND width > 0 AND depth > 0)
);

CREATE TABLE schedule (
	vessel       int CONSTRAINT schedule_pk PRIMARY KEY,
	berth        int          NOT NULL,
	manual       boolean      NOT NULL,
	start        timestamp(0) NOT NULL,
	expected_end timestamp(0) NOT NULL,
	CONSTRAINT schedule_vessel_id_fk FOREIGN KEY (vessel) REFERENCES vessel (id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT schedule_berth_id_fk FOREIGN KEY (berth) REFERENCES berth (id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT schedule_vessel_berth_check CHECK (
			(SELECT vessel.destination FROM vessel WHERE vessel.id = schedule.vessel)
			=
			(SELECT berth.terminal FROM berth WHERE berth.id = schedule.berth)
		),
	CONSTRAINT schedule_start_end_check CHECK (start < expected_end)
);

CREATE TABLE schedulechange (
	vessel int,
	date timestamp(0),
	old jsonb NOT NULL,
	new jsonb NOT NULL,
	reason varchar(255),
	PRIMARY KEY (vessel, date),
	CONSTRAINT schedule_change_vessel_fk FOREIGN KEY (vessel) REFERENCES vessel (id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE vesselchange (
	vessel int,
	date timestamp(0),
	old jsonb NOT NULL,
	new jsonb NOT NULL,
	reason varchar(255),
	PRIMARY KEY (vessel, date),
	CONSTRAINT vessel_change_vessel_fk FOREIGN KEY (vessel) REFERENCES vessel (id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE employee (
	email    varchar(64) CONSTRAINT employee_pk PRIMARY KEY,
	name     varchar(64) NOT NULL,
	key_hash bytea       NOT NULL,
	key_salt bytea       NOT NULL,
	role     role        NOT NULL,
	port     int,
	terminal int,
	CONSTRAINT employee_port_id_fk FOREIGN KEY (port) REFERENCES port (id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT employee_terminal_id_fk FOREIGN KEY (terminal) REFERENCES terminal (id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT employee_password_check_size CHECK (octet_length(key_hash) = 64), -- SHA-512 hash, 512 / 8 = 64
	CONSTRAINT employee_salt_check_size CHECK (octet_length(key_salt) = 32)      -- generated salt, 32 bytes
);


