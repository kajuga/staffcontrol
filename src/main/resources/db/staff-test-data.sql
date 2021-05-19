INSERT INTO staffcontrol.feedback (description, creation_date) VALUES
('firstDescription', 'Wed 17 Dec 03:27:13 2018 PST'),
('secondDescription', now()),
('thirdDescription', 'Wed 12 Dec 07:37:14 1990 PST'),
('firthDescription', 'Wed 11 Jan 11:32:17 1991 PST');

INSERT INTO staffcontrol.team(title) VALUES
('Team_1'),
('Team_2'),
('Team_3');

INSERT INTO staffcontrol.project (name, client, duration, methodology, project_manager, team_id) VALUES
('The developed project #1', 'Sony inc.', '15 days', 'methodology_second', 'Sikorsky A.A.', 2),
('The developed project #2', 'Intel inc.', '125 days', 'methodology_first', 'Ivanov I.A.', 1),
('The developed project #3', 'Microsoft inc.', '45 days', 'methodology_third', 'Boyarski N.V.', 3);

INSERT INTO staffcontrol.employee (first_name, last_name, phone_number, email, skype, entry_date, experience, experience_level, language_level, birthday, project_id, feedback_id) VALUES
('Aleksandr', 'Ivanov', '+79129345678', 'ivanov@mail.ru','@ivanov', '1999-10-01', 'not_bad_skills', 'J1', 'B2', '2001-06-22', 2,1),
('Ivan', 'Aleksandrov', '+79224541265', 'aleksandrov@mail.ru','@aleksandrov', '2019-04-12', 'good_skills', 'M1', 'B2', '1980-03-19', 1,3),
('Kurt', 'Kozlov', '+76543543826', 'kozlov@mail.ru','@kozlov', '2010-11-03', 'average_skills', 'M2', 'C1', '1995-03-21', 2,2),
('Sergei', 'Gubenko', '+78743764133', 'gubenko@mail.ru','@gubenko', '2021-01-01', 'santa_skills', 'S2', 'C2', '1998-08-05', 3,4);
