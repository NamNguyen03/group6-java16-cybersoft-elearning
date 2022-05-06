CREATE TABLE el_user_token(
	ID VARCHAR ( 36 ) NOT NULL,
	created_at TIMESTAMP,
	created_by VARCHAR ( 36 ),
	last_modified_at TIMESTAMP,
	last_modified_by VARCHAR ( 36 ),
	VERSION int4 NOT NULL,
	value VARCHAR ( 512 ),
	description VARCHAR ( 255 ),
	PRIMARY KEY ( ID ),
    user_id VARCHAR ( 36 ) NOT NULL,
    CONSTRAINT FK_TOKEN_USER
      FOREIGN KEY(user_id) 
	  REFERENCES el_user(id)
);

