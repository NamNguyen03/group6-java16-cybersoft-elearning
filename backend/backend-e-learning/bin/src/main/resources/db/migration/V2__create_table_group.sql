CREATE TABLE el_group (
	ID VARCHAR ( 36 ) NOT NULL,
	created_at TIMESTAMP,
	created_by VARCHAR ( 36 ),
	last_modified_at TIMESTAMP,
	last_modified_by VARCHAR ( 36 ),
	VERSION int4 NOT NULL,
	name VARCHAR ( 100 ),
	description VARCHAR ( 255 ),
	PRIMARY KEY ( ID ) 
);