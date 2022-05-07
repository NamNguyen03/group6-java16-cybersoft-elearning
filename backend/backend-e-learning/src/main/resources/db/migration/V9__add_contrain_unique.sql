ALTER TABLE el_lesson ADD CONSTRAINT lesson_name UNIQUE (name);
ALTER TABLE el_group ADD CONSTRAINT group_name UNIQUE (name);
ALTER TABLE el_course ADD CONSTRAINT course_name UNIQUE (course_name);
ALTER TABLE el_role ADD CONSTRAINT role_name UNIQUE (name);