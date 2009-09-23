-- SQL statements which are executed at application startup if hibernate.hbm2ddl.auto is 'create' or 'create-drop'

insert into ROLE ("TYPE", "DESCRIPTION") values('admin','administrator');
insert into ROLE ("TYPE", "DESCRIPTION") values('allocator','allocator');
insert into ROLE ("TYPE", "DESCRIPTION") values('participant','participant');

insert into "USER" ("USERNAME", "PASSWORD") values('admin','admin');

--insert into Role_User ('ROLE_id','USER_id') values (1,1);