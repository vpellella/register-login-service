
insert into hanuvi.role(id, name) values ('1', 'ROLE_ADMIN');
insert into hanuvi.role(id, name) values ('2', 'ROLE_USER');
insert into hanuvi.role(id, name) values ('3', 'ADMINISTRATOR');
insert into hanuvi.role(id, name) values ('4', 'SIMPLE_USER');

insert into hanuvi.app_user(id, username, password) values('1', 'admin', '{bcrypt}$2a$10$4I0y2q9FnBAfA07mgjl9xuSTMzN8PimGW7o0PYG2XsXwaw1PJPmea');

insert into HANUVI.USER_ROLE (user_id, role_id) values('1', '1');
insert into HANUVI.USER_ROLE (user_id, role_id) values('1', '3');