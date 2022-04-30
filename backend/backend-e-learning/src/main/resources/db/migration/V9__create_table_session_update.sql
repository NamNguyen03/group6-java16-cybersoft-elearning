CREATE TABLE el_session(
	ID VARCHAR ( 36 ) NOT NULL,
	created_at TIMESTAMP,
	created_by VARCHAR ( 36 ),
	last_modified_at TIMESTAMP,
	last_modified_by VARCHAR ( 36 ),
	VERSION int4 NOT NULL,
	session_name VARCHAR ( 100 ),
	img varchar(1000),
	session_description VARCHAR (1000),
	course_id VARCHAR (36) NOT NULL,
	PRIMARY KEY ( ID ) 
);

ALTER TABLE IF EXISTS el_session
	ADD CONSTRAINT FK_SESSION_COURSE FOREIGN KEY ( course_id ) REFERENCES el_course(id);