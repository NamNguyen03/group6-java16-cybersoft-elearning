CREATE TABLE el_comment (
	ID VARCHAR ( 36 ) NOT NULL,
	created_at TIMESTAMP,
	created_by VARCHAR ( 36 ),
	last_modified_at TIMESTAMP,
	last_modified_by VARCHAR ( 36 ),
	VERSION int4 NOT NULL,
	content VARCHAR ( 255 ),
	user_id VARCHAR ( 36 ) NOT NULL,
    course_id VARCHAR ( 36 ) NOT NULL,
    PRIMARY KEY ( ID ),
    CONSTRAINT FK_COMMENT_COURSE
        FOREIGN KEY(course_id) 
        REFERENCES el_course(id),
    CONSTRAINT FK_COMMENT_USER
	    FOREIGN KEY(user_id) 
  	    REFERENCES el_user(id)
);

CREATE TABLE el_rating (
	ID VARCHAR ( 36 ) NOT NULL,
	created_at TIMESTAMP,
	created_by VARCHAR ( 36 ),
	last_modified_at TIMESTAMP,
	last_modified_by VARCHAR ( 36 ),
	VERSION int4 NOT NULL,
	value int4 NOT NULL,
	user_id VARCHAR ( 36 ) NOT NULL,
    course_id VARCHAR ( 36 ) NOT NULL,
    PRIMARY KEY ( ID ),
    CONSTRAINT FK_RATING_COURSE
        FOREIGN KEY(course_id) 
        REFERENCES el_course(id),
    CONSTRAINT FK_RATING_USER
        FOREIGN KEY(user_id) 
        REFERENCES el_user(id)
);