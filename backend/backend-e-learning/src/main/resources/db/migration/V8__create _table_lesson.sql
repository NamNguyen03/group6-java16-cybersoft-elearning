CREATE TABLE el_lesson(
	ID VARCHAR ( 36 ) NOT NULL,
	created_at TIMESTAMP,
	created_by VARCHAR ( 36 ),
	last_modified_at TIMESTAMP,
	last_modified_by VARCHAR ( 36 ),
	VERSION int4 NOT NULL,
	name VARCHAR ( 100 ),
	content varchar(10000),
	description VARCHAR (1000),
	course_id VARCHAR ( 36 ) NOT NULL,
	PRIMARY KEY ( ID ),
	FOREIGN KEY(course_id) 
  	REFERENCES el_course(id)
);
