CREATE TABLE el_group_role ( 
group_id VARCHAR ( 36 ) NOT NULL,
role_id VARCHAR ( 36 ) NOT NULL,
PRIMARY KEY ( group_id, role_id ) );

ALTER TABLE
IF
	EXISTS el_group_role ADD CONSTRAINT FK_GROUP_ROLE_ROLE FOREIGN KEY ( role_id ) REFERENCES el_role;
ALTER TABLE
IF
	EXISTS el_group_role ADD CONSTRAINT FK_GROUP_ROLE_GROUP FOREIGN KEY ( group_id ) REFERENCES el_group;
	
CREATE TABLE el_group_user ( 
group_id 			VARCHAR ( 36 ) NOT NULL, 
user_id 			VARCHAR ( 36 ) NOT NULL, 
PRIMARY KEY ( group_id, user_id ) 
);

ALTER TABLE IF EXISTS el_group_user 
	ADD CONSTRAINT FK_GROUP_ROLE_USER FOREIGN KEY ( user_id ) REFERENCES el_user(id);
ALTER TABLE IF EXISTS el_group_user 
	ADD CONSTRAINT FK_GROUP_ROLE_GROUP FOREIGN KEY ( group_id ) REFERENCES el_group(id);