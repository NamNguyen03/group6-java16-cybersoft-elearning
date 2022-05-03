CREATE TABLE el_course (
	ID VARCHAR ( 36 ) NOT NULL,
	created_at TIMESTAMP,
	created_by VARCHAR ( 36 ),
	last_modified_at TIMESTAMP,
	last_modified_by VARCHAR ( 36 ),
	VERSION int4 NOT NULL,
	course_name VARCHAR ( 100 ),
	course_time INT,
	description VARCHAR ( 255 ),
	PRIMARY KEY ( ID ) 
);