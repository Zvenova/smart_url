insert into usr (id, username, password, active)
values ((SELECT nextval('public.hibernate_sequence')), 'admin', 'admin', true);

insert into user_role (user_id, roles)
values ((select id from usr where username = 'admin'), 'ADMIN'),
       ((select id from usr where username = 'admin'), 'USER')
